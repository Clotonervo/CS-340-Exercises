public class BasicFlightInfo implements Observer {

    private FlightFeed subject;
    private Flight f;

    public BasicFlightInfo(FlightFeed currentSubject){
        subject = currentSubject;
        subject.attach(this);
    }

    public void update(){
        f = subject.getFlight();
        if (f == null){
            return;
        }
        System.out.println(f.toString());
        return;
    }

}
