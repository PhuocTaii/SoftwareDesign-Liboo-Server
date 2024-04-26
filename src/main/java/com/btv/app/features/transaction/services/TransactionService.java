package com.btv.app.features.transaction.services;

import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    public List<Transaction> getAllTransaction(){
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionByUser(Long userId){
        return transactionRepository.findByUserId_Id(userId);
    }

    public Transaction addTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public Integer getNullReturnDateTransactions(List<Transaction> transactions){
        Integer cnt = 0;
        for(Transaction t : transactions){
            if(t.getReturnDate() == null){
                cnt++;
            }
        }
        return cnt;
    }

    public Boolean isBookBorrowed(Long userId, Long bookId){
        Transaction transaction = transactionRepository.findByUserId_IdAndBookId_Id(userId, bookId);
        return transaction != null;
    }

    public Transaction getTransactionByUserAndBook(Long userId, Long bookId){
        return transactionRepository.findByUserId_IdAndBookId_Id(userId, bookId);
    }

    public Transaction returnBook(Transaction transaction){
        transaction.setReturnDate(LocalDate.now());
        return transactionRepository.save(transaction);
    }
}
