package org.example.entity;

public record RequestCase(
     String caseNo,
     String destinationCompanyCode
){
    public Boolean isCase005() {
        return this.caseNo.equals("CASE005");
    }
}