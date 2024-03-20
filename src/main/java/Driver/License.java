package Driver;
import java.time.LocalDate;
public class License {
    private LicenseLevel Type;
    private LocalDate ngay_cap;
    private LocalDate ngay_het_han;
    private String ID;

    public LicenseLevel getType() {
        return Type;
    }
    public LocalDate getNgay_cap() {
        return ngay_cap;
    }
    public LocalDate getNgay_het_han() {
        return ngay_het_han;
    }
    public String getID() {
        return ID;
    }

    public void setType(LicenseLevel type) {
        this.Type = type;
    }
    public void setNgay_cap(LocalDate ngay_cap) {
        this.ngay_cap = ngay_cap;
    }
    public void setNgay_het_han(LocalDate ngay_het_han) {
        this.ngay_het_han = ngay_het_han;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public License(LicenseLevel type, LocalDate ngay_cap, LocalDate ngay_het_han, String ID) {
        this.Type = type;
        this.ngay_cap = ngay_cap;
        this.ngay_het_han = ngay_het_han;
        this.ID = ID;
    }
}

