package com.btv.app.features.transaction.services;

import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TransactionService {
    private final int RENEWAL_DAYS = 7;
    private final int LATE_FINE = 5000;
    private final int DUE_DATE_PERIOD = 30;
    private final TransactionRepository transactionRepository;
    public final TransactionBookService transactionBookService;
    public List<Transaction> getAllTransaction(){
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionByUser(Long userId){
        return transactionRepository.findByUserId_Id(userId);
    }

    public Transaction getTransactionById(Long id){
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        return optionalTransaction.orElse(null);
    }

    public Transaction addTransaction(User user){
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        return transactionRepository.save(transaction);
    }

//    public int getNullReturnDateTransactions(List<TransactionBook> transactions){
//        int cnt = 0;
//        for(TransactionBook t : transactions){
//            LocalDate returnDates = t.getReturnDate();
//            for(LocalDate returnDate : returnDates){
//                if(returnDate == null){
//                    cnt++;
//                    break;
//                }
//            }
//        }
//        return cnt;
//    }
//
//    public Boolean isBookBorrowed(Long userId, Long bookId){
//        List<Transaction> transaction = transactionRepository.findByUserId_Id(userId);
//        for(Transaction t : transaction){
//            for(Book b : t.getBooks()){
//                if(b.getId().equals(bookId)){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

//    public Transaction returnBook(Transaction transaction, int diff, int index){
//        int curFine = transaction.getFine();
//        List<LocalDate> tmpList = transaction.getReturnDates();
//        tmpList.set(index, LocalDate.now());
//        if(diff > 0){
//            curFine += (diff * LATE_FINE);
//            transaction.setFine(curFine);
//        }
//        transaction.setReturnDates(tmpList);
//        return transactionRepository.save(transaction);
//    }

//    public Transaction lostBookHandle(Transaction transaction, Book book){
//        int curFine = transaction.getFine();
//        curFine += (book.getPrice() * 2);
//        transaction.setFine(curFine);
//        return transactionRepository.save(transaction);
//    }
}
