package org.example;

//public record RequestCase(String caseNo, String destinationCompanyCode) {}
public class RequestCase {
    private String caseNo;
    private String destinationCompanyCode;

    // Constructor
    public RequestCase(String caseNo, String destinationCompanyCode) {
        this.caseNo = caseNo;
        this.destinationCompanyCode = destinationCompanyCode;
    }

    // Getters
    public String getCaseNo() {
        return caseNo;
    }

    public String getDestinationCompanyCode() {
        return destinationCompanyCode;
    }

    // Setters
    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public void setDestinationCompanyCode(String destinationCompanyCode) {
        this.destinationCompanyCode = destinationCompanyCode;
    }

    // toString Method
    @Override
    public String toString() {
        return "RequestCase{" +
                "caseNo='" + caseNo + '\'' +
                ", destinationCompanyCode='" + destinationCompanyCode + '\'' +
                '}';
    }

    // equals and hashCode Methods (Optional, for better comparison and hashing behavior)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestCase that = (RequestCase) o;

        if (!caseNo.equals(that.caseNo)) return false;
        return destinationCompanyCode.equals(that.destinationCompanyCode);
    }

    @Override
    public int hashCode() {
        int result = caseNo.hashCode();
        result = 31 * result + destinationCompanyCode.hashCode();
        return result;
    }
}