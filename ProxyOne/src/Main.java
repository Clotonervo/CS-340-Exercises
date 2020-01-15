import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class Main {

    public static void main(String[] args){
        Proxy proxy = new Proxy(); //Constructor runs from Mon-Fri from 8AM - 5PM

        try {
            System.out.println(proxy.getNumber());  // Will all run
            proxy.addTwo();
            System.out.println(proxy.getNumber());
            proxy.addTwo();
            System.out.println(proxy.getNumber());

            LocalDate date = LocalDate.now();
            List<DayOfWeek> newDays = proxy.allowedDays;

            newDays.remove(date.getDayOfWeek());
            proxy.addTwo(); // Should throw an exception here because today is not in the policy

        }catch(Exception x){
            System.out.println(x);
        }

        try {
            LocalDate date = LocalDate.now();
            List<Calendar> newTimes = proxy.allowedTimeRange;
            List<DayOfWeek> newDays = proxy.allowedDays;
            newDays.add(date.getDayOfWeek());
            proxy.addTwo();
            System.out.println(proxy.getNumber()); //Will work

            Calendar newStartTime = Calendar.getInstance();
            Calendar newEndTime = Calendar.getInstance();

            newStartTime.set(Calendar.HOUR, 1);
            newStartTime.set(Calendar.MINUTE, 0);
            newStartTime.set(Calendar.AM_PM, Calendar.AM);

            newEndTime.set(Calendar.HOUR, 3);
            newEndTime.set(Calendar.MINUTE, 0);
            newEndTime.set(Calendar.AM_PM, Calendar.AM);

            newTimes.set(0, newStartTime);  // Sets valid time between 1AM - 3AM
            newTimes.set(1, newEndTime);
            proxy.addTwo(); // Should throw an exception here because time

        }catch(Exception x){
            System.out.println(x);
        }

    }
}
