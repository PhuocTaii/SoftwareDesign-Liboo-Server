package com.btv.app.features.transaction.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.authentication.services.AuthenticationService;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.book.services.BookService;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.transaction.models.TransactionBook;
import com.btv.app.features.transaction.models.TransactionRequest;
import com.btv.app.features.user.models.Role;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionBookService transactionBookService;
    private final BookService bookService;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @AllArgsConstructor
    public static class TransactionResponse {
        public Transaction transaction;
        public String message;
    }

    @AllArgsConstructor
    public static class TransactionListResponse {
        public List<Transaction> transactions;
        public int pageNumber;
        public int totalPages;
        public long totalItems;
    }

    @GetMapping("/librarian/all-transactions")
    public ResponseEntity<List<Transaction>> getAllTransaction(){
        List<Transaction> res = transactionService.getAllTransaction();
        return ResponseEntity.ok(res);
    }

    //Search by userId
    @GetMapping("/librarian/transactions/{id}")
    public ResponseEntity<TransactionListResponse> getTransactionByUserId(@PathVariable("id") Long userId, @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber){
        User user = userService.getUserByID(userId);
        if(user == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }

        if(user.getRole() != Role.USER){
            throw new MyException(HttpStatus.BAD_REQUEST, "User is not a member");
        }

        Page<Transaction> res = transactionService.getTransactionByUser(userId, pageNumber);
        return ResponseEntity.ok(new TransactionListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @GetMapping("/user/transactions")
    public ResponseEntity<TransactionListResponse> getTransactionsByCurrentUser(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "filter-by", required = false) String filterOption,
            @RequestParam(value = "from", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam(value = "to", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateTo
    )
    {
        User user = authenticationService.getCurrentUser();
        Page<Transaction> res;
        if(Objects.equals(filterOption, "") || dateFrom == null || dateTo == null)
            res = transactionService.getTransactionByUser(user.getId(), pageNumber);
        else if(Objects.equals(filterOption, "borrow-date"))
            res = transactionService.getTransactionByUserAndBorrowedDate(user.getId(), dateFrom, dateTo, pageNumber);
        else if(Objects.equals(filterOption, "return-date"))
            res = transactionService.getTransactionByUserAndReturnDate(user.getId(), dateFrom, dateTo, pageNumber);
        else if(Objects.equals(filterOption, "due-date"))
            res = transactionService.getTransactionByUserAndDueDate(user.getId(), dateFrom, dateTo, pageNumber);
        else
            res = transactionService.getTransactionByUser(user.getId(), pageNumber);
        return ResponseEntity.ok(new TransactionListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @PostMapping("librarian/add-transaction")
    public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionRequest request){
        //Check if user have role "USER"
//        User user = userService.getUserByID(userId);
        User user = userService.getUserByEmail(request.getEmail());
        if(user == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }
        List<Book> books = new ArrayList<>();
        for (String bookIsbn : request.getIsbns()) {
            Book book = bookService.getBookByISBN(bookIsbn);
            books.add(book);
        }

        Role role = user.getRole();
        if(role != Role.USER){
            throw new MyException(HttpStatus.BAD_REQUEST, "User is not a member");
        }

        if(user.getAvailableBorrow() < request.getIsbns().size()){
            throw new MyException(HttpStatus.BAD_REQUEST, "User is not allowed to borrow more books");
        }

        Membership mem = user.getMembership();
        //Check if user is borrowing the same book and if the book is out of stock
        for(Book b : books){
            Boolean isBorrowed = transactionBookService.isBookBorrowed(user.getId(),b.getId());
            if(isBorrowed){
                throw new MyException(HttpStatus.BAD_REQUEST, b.getName() + " is already borrowed");
            }
            if(b.getQuantity() - b.getBorrowed() <= 1){
                throw new MyException(HttpStatus.BAD_REQUEST, b.getName() + " is out of stock");
            }
        }

        Transaction res = transactionService.addTransaction(user);
        transactionBookService.addTransactionBooks(res, books);

        //update book borrowed count
        for(Book b: books){
            bookService.increaseBookBorrowed(b);
        }
        userService.decreaseAvailableBorrow(user, books.size());
        return ResponseEntity.ok(res);
    }

    @PutMapping("librarian/return-book/{transactionId}/{bookId}")
    public ResponseEntity<TransactionResponse> returnBook(@PathVariable("transactionId") Long transactionId, @PathVariable("bookId") Long bookId){
        String message = "Return success";
        TransactionBook transactionBook = transactionBookService.getTransactionBookByTransactionAndBook(transactionId, bookId);
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if(transactionBook == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Transaction book not found");
        }

        if(transactionBook.getReturnDate() != null){
            throw new MyException(HttpStatus.BAD_REQUEST, "Book is already returned");
        }


        //Find difference between due date and return date
        int diff = Math.toIntExact(ChronoUnit.DAYS.between(transactionBook.getDueDate(), LocalDate.now()));
        if(diff > 0){
            message = "User is late!";
        }
        Transaction trans = transactionService.returnBook(transaction, transactionBook, diff);

        //update book borrowed count
        bookService.decreaseBookBorrowed(transactionBook.getBook());
        userService.increaseAvailableBorrow(transaction.getUser());

        TransactionResponse res = new TransactionResponse(trans, message);
        return ResponseEntity.ok(res);
    }
    @PutMapping("user/lost-book/{transactionId}/{bookId}")
    public ResponseEntity<TransactionResponse> lostBook(@PathVariable("transactionId") Long transactionId, @PathVariable("bookId") Long bookId){
        TransactionBook transactionBook = transactionBookService.getTransactionBookByTransactionAndBook(transactionId, bookId);
        Transaction transaction = transactionService.getTransactionById(transactionId);
        Book book = transactionBook.getBook();

        String message = "You have to pay for the lost book";

        if(transaction == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Transaction not found");
        }

        if(transactionBook.getReturnDate() != null){
            throw new MyException(HttpStatus.BAD_REQUEST, "Book is already returned");
        }

        //update book borrowed count
        Transaction trans = transactionService.lostBookHandle(transaction, book);

        TransactionResponse res = new TransactionResponse(trans, message);

        bookService.decreaseBookAmount(book);
        bookService.decreaseBookBorrowed(book);
        return ResponseEntity.ok(res);
    }

    @GetMapping("admin/fine-by-year")
    public ResponseEntity<List<Integer>> getFineByYear(@Param("year") Integer year){
        List<Integer> res = transactionService.getFineByYear(year);
        return ResponseEntity.ok(res);
    }

    @GetMapping("librarian/borrowing-books")
    public ResponseEntity<List<TransactionBook>> getBorrowingBooks(){
        List<TransactionBook> res = transactionBookService.getBorrowingBooks();
        return ResponseEntity.ok(res);
    }

    @GetMapping("librarian/total-active-borrowers")
    public ResponseEntity<Long> getTotalActiveBorrowers(){
        Long res = transactionService.getTotalActiveBorrowers();
        return ResponseEntity.ok(res);
    }

    @GetMapping("librarian/book-borrowed-count")
    public ResponseEntity<List<Integer>> getBookBorrowedCount(@Param("year") Integer year){
        List<Integer> res = transactionService.getBookBorrowedCount(year);
        return ResponseEntity.ok(res);
    }
}