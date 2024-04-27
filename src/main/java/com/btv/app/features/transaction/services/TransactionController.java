package com.btv.app.features.transaction.services;

import com.btv.app.features.authentication.model.AuthenticationResponse;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.book.services.BookService;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.user.models.Role;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.http.auth.AUTH;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final BookService bookService;
    private final Authentication auth;

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
            List<Transaction> res = transactionService.getTransactionByUser(userId);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("librarian/add-transaction")
    public ResponseEntity<Transaction> addTransaction(@ModelAttribute Transaction transaction){
        try{
            //Check if user have role "USER"
            User user = transaction.getUser();
            Book book = transaction.getBook();

            Role role = user.getRole();
            if(role != Role.USER){
                System.out.println("User is not a member");
                return ResponseEntity.status(400).build();
            }

            Membership mem = user.getMembership();
            Integer maxBorrow = mem.getMaxBook();
            List<Transaction> transactions = transactionService.getTransactionByUser(transaction.getUser().getId());

            //Check if user is borrowing the same book
            Boolean isBorrowed = transactionService.isBookBorrowed(user.getId(),transaction.getBook().getId());
            if(isBorrowed){
                System.out.println("Book is already borrowed");
                return ResponseEntity.status(400).build();
            }

            //Check if user has reached maximum borrow limit
            Integer nullReturn = transactionService.getNullReturnDateTransactions(transactions);
            if(Objects.equals(nullReturn, maxBorrow)){
                System.out.println("User has reached maximum borrow limit");
                return ResponseEntity.status(400).build();
            }
            Transaction res = transactionService.addTransaction(transaction);
            //update book borrowed count
            bookService.increaseBookBorrowed(book);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("librarian/return-book/{transactionId}")
    public ResponseEntity<Transaction> returnBook(@PathVariable("transactionId") Long transactionId){
        try{
            Transaction transaction = transactionService.getTransactionById(transactionId);

            if(transaction == null){
                System.out.println("Transaction not found");
                return ResponseEntity.status(404).build();
            }

            if(transaction.getReturnDate() != null){
                System.out.println("Book is returned already");
                return ResponseEntity.status(400).build();
            }
            Book book = transaction.getBook();


            //Find difference between due date and return date
            Integer diff = Math.toIntExact(ChronoUnit.DAYS.between(transaction.getDueDate(), LocalDate.now()));
            if(diff > 0){
                System.out.println("You're late!");
            }
            Transaction res = transactionService.returnBook(transaction, diff);
            //update book borrowed count
            bookService.decreaseBookBorrowed(book);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("user/lost-book/{transactionId}")
    public ResponseEntity<Transaction> lostBook(@PathVariable("transactionId") Long transactionId){
        try{
            Transaction transaction = transactionService.getTransactionById(transactionId);
            if(transaction == null){
                System.out.println("Transaction not found");
                return ResponseEntity.status(404).build();
            }
            //update book borrowed count
            Book book = transaction.getBook();
            Transaction res = transactionService.updateFine(transaction, book);
            bookService.decreaseBookAmount(book);
            bookService.decreaseBookBorrowed(book);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}




