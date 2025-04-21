package org.example.entity;

import java.util.*;

public record DocumentTransactionEntity(
        String caseNo,
        String docType,
        String docClass,
        String destinationCompanyCode,
        String destinationOfficeCode,
        int totalDocument,
        String docStatus,
        String hireeNo,
        Date createdDate,
        String createdBy
) {
    public DocumentTransactionEntity(String caseNo,
                                     String docClass,
                                     String destinationCompanyCode,
                                     int totalDocument,
                                     String docStatus,
                                     String hireeNo,
                                     String createdBy) {
        this(
                caseNo,
                null,
                docClass,
                destinationCompanyCode,
                null,
                totalDocument,
                docStatus,
                hireeNo,
                new Date(),
                createdBy
        );
    }
}
