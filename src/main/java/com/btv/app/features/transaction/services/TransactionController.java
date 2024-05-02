package com.btv.app.features.transaction.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.book.services.BookService;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.transaction.models.TransactionBook;
import com.btv.app.features.user.models.Role;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionBookService transactionBookService;
    private final BookService bookService;
    private final UserService userService;

    @AllArgsConstructor
    public static class TransactionResponse {
        public Transaction transaction;
        public String message;
    }

    @GetMapping("/librarian/all-transactions")
    public ResponseEntity<List<Transaction>> getAllTransaction(){
        List<Transaction> res = transactionService.getAllTransaction();
        return ResponseEntity.ok(res);
    }

    //Search by userId
    @GetMapping("/transactions/{id}")
    public ResponseEntity<List<Transaction>> getTransactionByUserId(@PathVariable("id") Long userId){
        User user = userService.getUserByID(userId);
        if(user == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }

        if(user.getRole() != Role.USER){
            throw new MyException(HttpStatus.BAD_REQUEST, "User is not a member");
        }

        List<Transaction> res = transactionService.getTransactionByUser(userId);
        return ResponseEntity.ok(res);

    }

    @PostMapping("librarian/add-transaction")
    public ResponseEntity<Transaction> addTransaction(@ModelAttribute("userId") Long userId, @ModelAttribute("bookIds") List<Long> bookIds){
        //Check if user have role "USER"
        User user = userService.getUserByID(userId);
        if(user == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }
        List<Book> books = new ArrayList<>();
        for (Long bookId : bookIds) {
            Book book = bookService.getBookByID(bookId);
            books.add(book);
        }

        Role role = user.getRole();
        if(role != Role.USER){
            throw new MyException(HttpStatus.BAD_REQUEST, "User is not a member");
        }

        if(user.getAvailableBorrow() < bookIds.size()){
            throw new MyException(HttpStatus.BAD_REQUEST, "User is not allowed to borrow more books");
        }

        Membership mem = user.getMembership();
        //Check if user is borrowing the same book
        for(Book b : books){
            Boolean isBorrowed = transactionService.isBookBorrowed(user.getId(),b.getId());
            if(isBorrowed){
                throw new MyException(HttpStatus.BAD_REQUEST, b.getName() + " is already borrowed");
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
}