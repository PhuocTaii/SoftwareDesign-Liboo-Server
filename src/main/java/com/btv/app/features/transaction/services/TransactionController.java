package com.btv.app.features.transaction.services;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.book.services.BookService;
import com.btv.app.features.intermediate.TransactionBook;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.transaction.models.Transaction;
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
    private final BookService bookService;
    private final UserService userService;

    @GetMapping("librarian/all-transactions")
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
    @GetMapping("transactions/{id}")
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
    public ResponseEntity<Transaction> addTransaction(@ModelAttribute Transaction transaction){
        try{
            //Check if user have role "USER"
            User user = transaction.getUser();
            List<TransactionBook> books = transaction.getTransactionBooks();
            Role role = user.getRole();
            if(role != Role.USER){
                System.out.println("User is not a member");
                return ResponseEntity.status(400).build();
            }

            if(user.getAvailableBorrow() < transaction.getTransactionBooks().size()){
                System.out.println("User is not allowed to borrow more books");
                return ResponseEntity.status(403).build();
            }

            Membership mem = user.getMembership();
            int maxBorrow = mem.getMaxBook();
            List<Transaction> transactions = transactionService.getTransactionByUser(transaction.getUser().getId());

            //Check if user is borrowing the same book
//            for(Book b : books){
//                Boolean isBorrowed = transactionService.isBookBorrowed(user.getId(),b.getId());
//                if(isBorrowed){
//                    System.out.println("Book is already borrowed");
//                    return ResponseEntity.status(400).build();
//                }
//            }


            //Check if user has reached maximum borrow limit
//            int nullReturn = transactionService.getNullReturnDateTransactions(transactions);
//            if(Objects.equals(nullReturn, maxBorrow)){
//                System.out.println("User has reached maximum borrow limit");
//                return ResponseEntity.status(400).build();
//            }

            transactionService.addTransaction(transaction);


//            update book borrowed count
            for(TransactionBook b: books){
                bookService.increaseBookBorrowed(b.getBook());
            }
            userService.decreaseAvailableBorrow(user, books.size());

            return ResponseEntity.status(200).body(transaction);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(500).build();
        }
    }

//    @PutMapping("librarian/return-book/{transactionId}/{bookId}")
//    public ResponseEntity<Transaction> returnBook(@PathVariable("transactionId") Long transactionId, @PathVariable("bookId") Long bookId){
//        try{
//            Transaction transaction = transactionService.getTransactionById(transactionId);
//            Book returnBook = bookService.getBookByID(bookId);
//            if(transaction == null){
//                System.out.println("Transaction not found");
//                return ResponseEntity.status(404).build();
//            }
//
//            if(returnBook == null){
//                System.out.println("Book not found");
//                return ResponseEntity.status(404).build();
//            }
//
//            int index = transaction.getBooks().indexOf(returnBook);
////            if(!transaction.getReturnDates().get(index).toString().equals(LocalDate.EPOCH.toString())){
////                System.out.println("Book is returned already");
////                return ResponseEntity.status(400).build();
////            }
//            if(!transaction.getTransactionBooks().get(index).getReturnDate().toString().equals(LocalDate.EPOCH.toString())){
//                System.out.println("Book is returned already");
//                return ResponseEntity.status(400).build();
//            }
//
//            //Find difference between due date and return date
//            int diff = Math.toIntExact(ChronoUnit.DAYS.between(transaction.getTransactionBooks().get(index).getDueDate(), LocalDate.now()));
//            if(diff > 0){
//                System.out.println("User is late!");
//            }
//            Transaction res = transactionService.returnBook(transaction, diff, index);
//            //update book borrowed count
//            bookService.decreaseBookBorrowed(returnBook);
//            userService.increaseAvailableBorrow(transaction.getUser());
//            return ResponseEntity.status(200).body(res);
//        } catch (Exception e) {
//            System.out.println(e);
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @PutMapping("user/lost-book/{transactionId}/{bookId}")
//    public ResponseEntity<Transaction> lostBook(@PathVariable("transactionId") Long transactionId, @PathVariable("bookId") Long bookId){
//    public ResponseEntity<Transaction> lostBook(@PathVariable("transactionId") Long transactionId, @PathVariable("bookId") Long bookId){
//        try{
//            Transaction transaction = transactionService.getTransactionById(transactionId);
//            Book book = bookService.getBookByID(bookId);
//
//            if(transaction == null){
//                System.out.println("Transaction not found");
//                return ResponseEntity.status(404).build();
//            }
//
//            if(book == null){
//                System.out.println("Book not found");
//                return ResponseEntity.status(404).build();
//            }
//
//            List<Book> bookList = transaction.getBooks();
//            if(!bookList.contains(book)){
//                System.out.println("Book not found in transaction");
//                return ResponseEntity.status(404).build();
//            }
//            //update book borrowed count
//            Transaction res = transactionService.lostBookHandle(transaction, book);
//            bookService.decreaseBookAmount(book);
//            bookService.decreaseBookBorrowed(book);
//            return ResponseEntity.status(200).body(res);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).build();
//        }
//    }
}




