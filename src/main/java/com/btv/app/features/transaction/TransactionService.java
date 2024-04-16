package com.btv.app.features.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public List<Transaction> getAllTransaction(){
        try {
            return transactionRepository.findAll();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Transaction getTransactionByID(Long id){
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        return optionalTransaction.orElse(null);
    }
}
