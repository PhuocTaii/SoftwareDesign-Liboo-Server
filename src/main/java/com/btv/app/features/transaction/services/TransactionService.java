package com.btv.app.features.transaction.services;

import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        try {
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            return null;
        }
    }
}
