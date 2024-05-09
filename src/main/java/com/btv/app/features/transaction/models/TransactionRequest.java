package com.btv.app.features.transaction.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TransactionRequest {
    private String email;
    private List<String> isbns;
}
