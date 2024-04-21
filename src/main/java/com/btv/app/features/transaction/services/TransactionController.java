package com.btv.app.features.transaction.services;

import com.btv.app.features.transaction.models.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/getAllTransactions")
    public List<Transaction> getAllTransaction(){
        try {
            return transactionService.getAllTransaction();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
    @GetMapping("/getTransactionByID/{id}")
    public Transaction getTransactionByID(Long id){
        try{
            return transactionService.getTransactionByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
