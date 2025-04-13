package org.example;

import java.util.Date;

public class DocumentTransactionEntity {
    private String caseNo;
    private String docType;
    private String docClass;
    private String destinationCompanyCode;
    private String destinationOfficeCode;
    private int totalDocument;
    private String docStatus;
    private String hireeNo;
    private Date createdDate;
    private String createdBy;

    // Getters and Setters
    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocClass() {
        return docClass;
    }

    public void setDocClass(String docClass) {
        this.docClass = docClass;
    }

    public String getDestinationCompanyCode() {
        return destinationCompanyCode;
    }

    public void setDestinationCompanyCode(String destinationCompanyCode) {
        this.destinationCompanyCode = destinationCompanyCode;
    }

    public String getDestinationOfficeCode() {
        return destinationOfficeCode;
    }

    public void setDestinationOfficeCode(String destinationOfficeCode) {
        this.destinationOfficeCode = destinationOfficeCode;
    }

    public int getTotalDocument() {
        return totalDocument;
    }

    public void setTotalDocument(int totalDocument) {
        this.totalDocument = totalDocument;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getHireeNo() {
        return hireeNo;
    }

    public void setHireeNo(String hireeNo) {
        this.hireeNo = hireeNo;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
