package com.btv.app.features.transaction.services;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.transaction.models.TransactionBook;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionBookService {
    private static final int DUE_DATE_PERIOD = 30; // 30 days
    public final TransactionBookRepository transactionBookRepository;

    public TransactionBook addTransactionBook(Transaction transaction, Book book) {
        TransactionBook transactionBook = new TransactionBook();
        transactionBook.setTransaction(transaction);
        transactionBook.setBook(book);

        return transactionBookRepository.save(transactionBook);
    }

    public void addTransactionBooks(Transaction transaction, List<Book> books) {
        for(Book book: books) {
            addTransactionBook(transaction, book);
        }
    }
}
