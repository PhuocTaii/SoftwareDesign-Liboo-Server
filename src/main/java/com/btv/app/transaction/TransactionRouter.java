package com.btv.app.transaction;


import com.btv.app.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transaction")

public class TransactionRouter {
    private final TransactionController transactionController;
    @Autowired
    public TransactionRouter(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    @GetMapping("/getAllTransactions")
    public List<Transaction> getAllTransaction(){
        return transactionController.getAllTransaction();
    }

    @GetMapping("/getTransactionByID/{id}")
    public Transaction getTransactionByID(@PathVariable("id") Long id){
        return transactionController.getTransactionByID(id);
    }

}
