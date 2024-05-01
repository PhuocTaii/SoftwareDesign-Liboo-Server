package com.btv.app.features.transaction.services;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.transaction.models.TransactionBook;
import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class TransactionService {
    private final Integer RENEWAL_DAYS = 7;
    private final Integer LATE_FINE = 5000;

    private final TransactionRepository transactionRepository;
    private final TransactionBookService transactionBookService;
    private final TransactionBookRepository transactionBookRepository;
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

    public Boolean isBookBorrowed(Long userId, Long bookId){
        List<Transaction> transaction = transactionRepository.findByUserId_Id(userId);
        for(Transaction t : transaction){
            List<TransactionBook> transactionBooks = t.getTransactionBooks();
            for(TransactionBook tb : transactionBooks){
                if(tb.getBook().getId().equals(bookId)){
                    return true;
                }
            }
        }
        return false;
    }

    public Transaction returnBook(Transaction transaction, TransactionBook transactionBook, Integer diff){
        int curFine = transaction.getFine();
        transactionBook.setReturnDate(LocalDate.now());
        if(diff > 0){
            curFine += (diff * LATE_FINE);
            transaction.setFine(curFine);
        }
        transactionBookRepository.save(transactionBook);
        return transactionRepository.save(transaction);
    }

    public Transaction lostBookHandle(Transaction transaction, Book book){
        int curFine = transaction.getFine();
        curFine += (book.getPrice() * 2);
        transaction.setFine(curFine);
        return transactionRepository.save(transaction);
    }
}
