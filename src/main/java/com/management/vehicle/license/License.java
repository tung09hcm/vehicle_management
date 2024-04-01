package com.management.vehicle.license;

public class License {
    private LicenseLevel type;
    private String issueDate;
    private String expiryDate;
    private String id;

    public LicenseLevel getType() {
        return type;
    }

    public void setType(LicenseLevel type) {
        this.type = type;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public License() {
        this.type = LicenseLevel.NONE;
        this.issueDate = "";
        this.expiryDate = "";
        this.id = "";
    }

    public License(LicenseLevel type, String issueDate, String expiryDate, String id) {
        this.type = type;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.id = id;
    }
}

