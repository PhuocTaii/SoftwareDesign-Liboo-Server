package com.btv.app.features.transaction.services;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.book.services.BookService;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.transaction.models.TransactionBook;
import com.btv.app.features.user.models.Role;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserService;
import lombok.AllArgsConstructor;
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

    @GetMapping("/librarian/all-transactions")
    public ResponseEntity<List<Transaction>> getAllTransaction(){
        try {
            List<Transaction> res = transactionService.getAllTransaction();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).build();
        }
    }

    //Search by userId
    @GetMapping("/transactions/{id}")
    public ResponseEntity<List<Transaction>> getTransactionByUserId(@PathVariable("id") Long userId){
        try{
            User user = userService.getUserByID(userId);
            if(user == null){
                System.out.println("User not found");
                return ResponseEntity.status(404).build();
            }

            if(user.getRole() != Role.USER){
                System.out.println("User is not a member");
                return ResponseEntity.status(400).build();
            }

            List<Transaction> res = transactionService.getTransactionByUser(userId);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("librarian/add-transaction")
    public ResponseEntity<Transaction> addTransaction(@ModelAttribute("userId") Long userId, @ModelAttribute("bookIds") List<Long> bookIds){
        try{
//            //Check if user have role "USER"
            User user = userService.getUserByID(userId);
            List<Book> books = new ArrayList<>();
            for (Long bookId : bookIds) {
                Book book = bookService.getBookByID(bookId);
                books.add(book);
            }

            Role role = user.getRole();
            if(role != Role.USER){
                System.out.println("User is not a member");
                return ResponseEntity.status(400).build();
            }

            if(user.getAvailableBorrow() < bookIds.size()){
                System.out.println("User is not allowed to borrow more books");
                return ResponseEntity.status(400).build();
            }

            Membership mem = user.getMembership();
            //Check if user is borrowing the same book
            for(Book b : books){
                Boolean isBorrowed = transactionService.isBookBorrowed(user.getId(),b.getId());
                if(isBorrowed){
                    System.out.println(b.getName() + " is already borrowed");
                    return ResponseEntity.status(400).build();
                }
            }

            Transaction res = transactionService.addTransaction(user);
            transactionBookService.addTransactionBooks(res, books);
            //update book borrowed count
            for(Book b: books){
                bookService.increaseBookBorrowed(b);
            }
            userService.decreaseAvailableBorrow(user, books.size());
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("librarian/return-book/{transactionId}/{bookId}")
    public ResponseEntity<Transaction> returnBook(@PathVariable("transactionId") Long transactionId, @PathVariable("bookId") Long bookId){
        try{
            TransactionBook transactionBook = transactionBookService.getTransactionBookByTransactionAndBook(transactionId, bookId);
            Transaction transaction = transactionService.getTransactionById(transactionId);
            if(transactionBook == null){
                System.out.println("Transaction book is not found");
                return ResponseEntity.status(404).build();
            }

            if(transactionBook.getReturnDate() != null){
                System.out.println("Book is already returned");
                return ResponseEntity.status(400).build();
            }


            //Find difference between due date and return date
            int diff = Math.toIntExact(ChronoUnit.DAYS.between(transactionBook.getDueDate(), LocalDate.now()));
            if(diff > 0){
                System.out.println("User is late!");
            }
            Transaction res = transactionService.returnBook(transaction, transactionBook, diff);

            //update book borrowed count
            bookService.decreaseBookBorrowed(transactionBook.getBook());
            userService.increaseAvailableBorrow(transaction.getUser());
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("user/lost-book/{transactionId}/{bookId}")
    public ResponseEntity<Transaction> lostBook(@PathVariable("transactionId") Long transactionId, @PathVariable("bookId") Long bookId){
        try{
            TransactionBook transactionBook = transactionBookService.getTransactionBookByTransactionAndBook(transactionId, bookId);
            Transaction transaction = transactionService.getTransactionById(transactionId);
            Book book = transactionBook.getBook();

            if(transaction == null){
                System.out.println("Transaction not found");
                return ResponseEntity.status(404).build();
            }

            if(transactionBook == null){
                System.out.println("Transaction book not found");
                return ResponseEntity.status(404).build();
            }

            if(transactionBook.getReturnDate() != null){
                System.out.println("Book is already returned");
                return ResponseEntity.status(400).build();
            }

            //update book borrowed count
            Transaction res = transactionService.lostBookHandle(transaction, book);
            bookService.decreaseBookAmount(book);
            bookService.decreaseBookBorrowed(book);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}