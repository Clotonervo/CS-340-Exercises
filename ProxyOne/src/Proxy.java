import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Proxy implements Interface {

    private Implementation realSubject;
    public List<DayOfWeek> allowedDays = new ArrayList<>();
    public List<Calendar> allowedTimeRange = new ArrayList<>();
    private Calendar proxyCalendar;

    public Proxy(){
        realSubject = new Implementation();
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        allowedDays.add(DayOfWeek.MONDAY);
        allowedDays.add(DayOfWeek.TUESDAY);
        allowedDays.add(DayOfWeek.WEDNESDAY);
        allowedDays.add(DayOfWeek.THURSDAY);
        allowedDays.add(DayOfWeek.FRIDAY);

        startTime.set(Calendar.HOUR, 8);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.AM_PM, Calendar.AM);

        endTime.set(Calendar.HOUR, 5);
        endTime.set(Calendar.MINUTE, 0);
        endTime.set(Calendar.AM_PM, Calendar.PM);

        allowedTimeRange.add(startTime);
        allowedTimeRange.add(endTime);
        proxyCalendar = Calendar.getInstance(TimeZone.getDefault());
    }

    public Integer getNumber() throws Exception {
        LocalDate date = LocalDate.now();

        if (allowedDays.contains(date.getDayOfWeek())
                && proxyCalendar.after(allowedTimeRange.get(0)) && proxyCalendar.before(allowedTimeRange.get(1))){
            return realSubject.getNumber();
        }
        else {
            throw new Exception("You can't call that function at this time!");
        }
    }

    public void addTwo() throws Exception{
        LocalDate date = LocalDate.now();

        if (allowedDays.contains(date.getDayOfWeek())
                && proxyCalendar.after(allowedTimeRange.get(0)) && proxyCalendar.before(allowedTimeRange.get(1))){
            realSubject.addTwo();
        }
        else {
            throw new Exception("You can't call that function at this time!");
        }
    }

}
