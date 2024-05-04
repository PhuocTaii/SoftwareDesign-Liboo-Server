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
    private static final int RENEWAL_DAYS = 7; // 7 days
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

    public TransactionBook increaseRenewalCount(TransactionBook transactionBook){
        transactionBook.setRenewalCount(transactionBook.getRenewalCount() + 1);
        return transactionBookRepository.save(transactionBook);
    }

    public TransactionBook extendDueDate(TransactionBook transactionBook){
        transactionBook.setDueDate(transactionBook.getDueDate().plusDays(RENEWAL_DAYS));
        return transactionBookRepository.save(transactionBook);
    }
    public TransactionBook getTransactionBookByTransactionAndBook(Long transactionId, Long bookId) {
        return transactionBookRepository.findByTransaction_IdAndBook_Id(transactionId, bookId);
    }

//    public Boolean isBookBorrowed(Long userId, Long bookId){
//        List<Transaction> transaction = transactionRepository.findByUserId_Id(userId);
//        for(Transaction t : transaction){
//            List<TransactionBook> transactionBooks = t.getTransactionBooks();
//            for(TransactionBook tb : transactionBooks){
//                if(tb.getBook().getId().equals(bookId)){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
    public Boolean isBookBorrowed(Long userId, Long bookId){
        return transactionBookRepository.existsByTransaction_User_IdAndBook_IdAndReturnDateNotNull(userId, bookId);
    }

    public List<TransactionBook> getBorrowingBooks(){
        return transactionBookRepository.findByReturnDateNull();
    }
}
