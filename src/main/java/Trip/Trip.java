package Trip;
import java.time.LocalDate;
public class Trip {
    private Coordinate begin;
    private Coordinate end;
    private LocalDate begin_date;
    private LocalDate end_date;

    public Coordinate getBegin() {
        return begin;
    }
    public Coordinate getEnd() {
        return end;
    }
    public LocalDate getBegin_date() {
        return begin_date;
    }
    public LocalDate getEnd_date() {
        return end_date;
    }
    public void setBegin(Coordinate begin) {
        this.begin = begin;
    }
    public void setEnd(Coordinate end) {
        this.end = end;
    }
    public void setBegin_date(LocalDate begin_date) {
        this.begin_date = begin_date;
    }
    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Trip(Coordinate begin, Coordinate end, LocalDate begin_date, LocalDate end_date) {
        this.begin = begin;
        this.end = end;
        this.begin_date = begin_date;
        this.end_date = end_date;
    }
}
