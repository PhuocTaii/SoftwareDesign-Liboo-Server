package com.btv.app.features.renewal.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.authentication.services.AuthenticationService;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.transaction.models.TransactionBook;
import com.btv.app.features.transaction.services.TransactionBookService;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class RenewalController {
    private final RenewalService renewalService;
    private final TransactionBookService transactionBookService;
    private final AuthenticationService authenticationService;

    @AllArgsConstructor
    public static class RenewalListResponse {
        public List<Renewal> renewals;
        public int pageNumber;
        public int totalPages;
        public long totalItems;
    }

    @GetMapping("/librarian/renewals")
    public ResponseEntity<RenewalListResponse> getRenewals(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber){
        Page<Renewal> res = renewalService.getAllRenewals(pageNumber);
        return ResponseEntity.ok(new RenewalListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }
    @GetMapping("/user/renewals")
    public ResponseEntity<RenewalListResponse> getRenewalsByUser(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "from", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam(value = "to", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateTo
    ){
        User user = authenticationService.getCurrentUser();
        Page<Renewal> res;

        if(dateFrom == null || dateTo == null)
            res = renewalService.getRenewalByUser(user, pageNumber);
        else
            res = renewalService.getRenewalByUserAndRequestDate(user, dateFrom, dateTo, pageNumber);
        return ResponseEntity.ok(new RenewalListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @PostMapping("/user/request-renewal")
    public ResponseEntity<Renewal> requestRenewal(@RequestBody Map<String, Long> req) {
        Long transactionBookId = req.get("transactionBook");
        TransactionBook transactionBook = transactionBookService.getTransactionBookById(transactionBookId);
        Renewal renewal = new Renewal();
        renewal.setTransactionBook(transactionBook);

        // check if this transaction belong to current user
        if(!Objects.equals(authenticationService.getCurrentUser().getId(), renewal.getTransactionBook().getTransaction().getUser().getId())) {
            throw new MyException(HttpStatus.FORBIDDEN, "Not have right to renew this borrowing!");
        }

        // check membership
        Membership membership = renewal.getTransactionBook().getTransaction().getUser().getMembership();
        if(renewal.getTransactionBook().getRenewalCount() >= membership.getMaxRenewal()) {
            throw new MyException(HttpStatus.BAD_REQUEST, "You have reached the limit times of renewal");
        }

        // check if returned
        if(renewal.getTransactionBook().getReturnDate() != null) {
            throw new MyException(HttpStatus.BAD_REQUEST, "You have already returned this book");
        }

        // check time
        if(!LocalDate.now().isBefore(renewal.getTransactionBook().getDueDate())) {
            throw new MyException(HttpStatus.BAD_REQUEST, "You cannot renew now");
        }

        renewalService.requestRenewal(renewal);
        transactionBookService.increaseRenewalCount(renewal.getTransactionBook());
        transactionBookService.extendDueDate(renewal.getTransactionBook());

        return ResponseEntity.ok(renewal);
    }
}
