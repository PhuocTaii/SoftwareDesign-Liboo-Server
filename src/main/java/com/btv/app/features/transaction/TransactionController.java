package com.btv.app.features.transaction;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public List<Transaction> getAllTransaction(){
        try {
            return transactionService.getAllTransaction();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Transaction getTransactionByID(Long id){
        try{
            return transactionService.getTransactionByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
