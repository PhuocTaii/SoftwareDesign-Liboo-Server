package com.btv.app.features.transaction.services;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.transaction.models.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class TransactionService {
    private final int RENEWAL_DAYS = 7;
    private final int LATE_FINE = 5000;
    private final int DUE_DATE_PERIOD = 30;
    private final TransactionRepository transactionRepository;
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

    public Transaction addTransaction(Transaction transaction){
        List<LocalDate> returnDates = transaction.getReturnDates();
        List<Integer> renewalCounts = transaction.getRenewalCounts();
        List<LocalDate> dueDates = transaction.getDueDates();

        List<Book> books = transaction.getBooks();
        for(Integer i = 0; i < books.size(); i++){
            returnDates.add(LocalDate.EPOCH);
            renewalCounts.add(0);
            dueDates.add(LocalDate.now().plusDays(DUE_DATE_PERIOD));
        }

        transaction.setReturnDates(returnDates);
        transaction.setRenewalCounts(renewalCounts);
        transaction.setDueDates(dueDates);

        return transactionRepository.save(transaction);
    }

    public int getNullReturnDateTransactions(List<Transaction> transactions){
        int cnt = 0;
        for(Transaction t : transactions){
            List<LocalDate> returnDates = t.getReturnDates();
            for(LocalDate returnDate : returnDates){
                if(returnDate == null){
                    cnt++;
                    break;
                }
            }
        }
        return cnt;
    }

    public Boolean isBookBorrowed(Long userId, Long bookId){
        List<Transaction> transaction = transactionRepository.findByUserId_Id(userId);
        for(Transaction t : transaction){
            for(Book b : t.getBooks()){
                if(b.getId().equals(bookId)){
                    return true;
                }
            }
        }
        return false;
    }

    public Transaction returnBook(Transaction transaction, int diff, int index){
        int curFine = transaction.getFine();
        List<LocalDate> tmpList = transaction.getReturnDates();
        tmpList.set(index, LocalDate.now());
        if(diff > 0){
            curFine += (diff * LATE_FINE);
            transaction.setFine(curFine);
        }
        transaction.setReturnDates(tmpList);
        return transactionRepository.save(transaction);
    }

//    public Transaction increaseRenewalCount(Transaction transaction, int index){
//        transaction.setRenewalCount(transaction.getRenewalCount() + 1);
//        return transactionRepository.save(transaction);
//    }

    public Transaction lostBookHandle(Transaction transaction, Book book){
        int curFine = transaction.getFine();
        curFine += (book.getPrice() * 2);
        transaction.setFine(curFine);
        return transactionRepository.save(transaction);
    }

//    public Transaction extendDueDate(Transaction transaction){
//        transaction.setDueDate(transaction.getDueDate().plusDays(RENEWAL_DAYS));
//        return transactionRepository.save(transaction);
//    }
}
