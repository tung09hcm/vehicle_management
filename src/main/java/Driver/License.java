package Driver;
import java.time.LocalDate;
public class License {
    private LicenseLevel type;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String id;

    public LicenseLevel getType() {
        return type;
    }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public String getId() {
        return id;
    }

    public void setType(LicenseLevel type) {
        this.type = type;
    }
    public void setIssueDate(LocalDate IssueDate) { this.issueDate = IssueDate; }
    public void setExpiryDate(LocalDate ExpiryDate) {
        this.expiryDate = ExpiryDate;
    }
    public void setId(String id) {
        this.id = id;
    }

    public License(LicenseLevel type, LocalDate IssueDate, LocalDate ExpiryDate, String ID) {
        this.type = type;
        this.issueDate = IssueDate;
        this.expiryDate = ExpiryDate;
        this.id = ID;
    }
}

