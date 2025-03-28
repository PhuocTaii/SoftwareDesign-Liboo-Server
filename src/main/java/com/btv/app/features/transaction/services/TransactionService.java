package com.btv.app.features.transaction.services;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.transaction.models.TransactionBook;
import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
public class TransactionService {
    private final Integer RENEWAL_DAYS = 7;
    private final Integer LATE_FINE = 5000;
    private final Integer PAGE_SIZE = 10;

    private final TransactionRepository transactionRepository;
    private final TransactionBookRepository transactionBookRepository;
    public Page<Transaction> getAllTransaction(int pageNumber){
        return transactionRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Page<Transaction> getTransactionByUser(Long userId, int pageNumber){
        return transactionRepository.findByUserId_Id(userId, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Page<Transaction> getTransactionByUserAndBorrowedDate(Long userId, LocalDate dateFrom, LocalDate dateTo, int pageNumber){
        return transactionRepository.findByUser_IdAndBorrowedDateBetween(userId, dateFrom, dateTo, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Page<Transaction> getTransactionByBorrowedDate(LocalDate dateFrom, LocalDate dateTo, int pageNumber){
        return transactionRepository.findByBorrowedDateBetween(dateFrom, dateTo, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Page<Transaction> getTransactionByUserAndReturnDate(Long userId, LocalDate dateFrom, LocalDate dateTo, int pageNumber){
        return transactionRepository.findByUser_IdAndTransactionBooks_ReturnDateBetween(userId, dateFrom, dateTo, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Page<Transaction> getTransactionByReturnDate(LocalDate dateFrom, LocalDate dateTo, int pageNumber){
        return transactionRepository.findByTransactionBooks_ReturnDateBetween(dateFrom, dateTo, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Page<Transaction> getTransactionByUserAndDueDate(Long userId, LocalDate dateFrom, LocalDate dateTo, int pageNumber){
        return transactionRepository.findByUser_IdAndTransactionBooks_DueDateBetween(userId, dateFrom, dateTo, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Page<Transaction> getTransactionByDueDate(LocalDate dateFrom, LocalDate dateTo, int pageNumber){
        return transactionRepository.findByTransactionBooks_DueDateBetween(dateFrom, dateTo, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
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

    public Transaction returnBook(Transaction transaction, TransactionBook transactionBook, Integer diff, boolean isLost){
        int fine = calcFineForATransactionBook(transactionBook, diff, isLost);
        transaction.setFine(transaction.getFine() + fine);
        transactionBook.setReturnDate(LocalDate.now());
        transactionBookRepository.save(transactionBook);
        return transactionRepository.save(transaction);
    }

    public int calcFineForATransactionBook(TransactionBook transactionBook, int dateDiff, boolean isLost){
        if(isLost){
            return transactionBook.getBook().getPrice() * 2;
        }
        if(dateDiff > 0){
            return dateDiff * LATE_FINE;
        }
        return 0;
    }

    public Transaction lostBookHandle(Transaction transaction, Book book){
        int curFine = transaction.getFine();
        curFine += (book.getPrice() * 2);
        transaction.setFine(curFine);
        return transactionRepository.save(transaction);
    }

    public List<Integer>getFineByYear(Integer year){
        List<Integer> res = new ArrayList<>(12);
        for(int i = 0; i < 12; i++){
            res.add(0);
        }
        List<Transaction> transactions = transactionRepository.findAll();
        for(Transaction t : transactions){
            if(t.getBorrowedDate().getYear() == year){
                int month = t.getBorrowedDate().getMonthValue();
                int fine = t.getFine();
                res.set(month - 1, res.get(month - 1) + fine);
            }
        }
        return res;
    }

    public Long getTotalActiveBorrowers(){
        return transactionRepository.countDistinctByTransactionBooks_ReturnDateNull();
    }

    public List<Integer> getBookBorrowedCount(Integer year){
        List<Integer> res = new ArrayList<>(12);
        for(int i = 0; i < 12; i++){
            res.add(0);
        }
        List<Transaction> transactions = transactionRepository.findAll();
        for(Transaction t : transactions){
            if(t.getBorrowedDate().getYear() == year){
                int month = t.getBorrowedDate().getMonthValue();
                res.set(month - 1, res.get(month - 1) + t.getTransactionBooks().size());
            }
        }
        return res;
    }
}
