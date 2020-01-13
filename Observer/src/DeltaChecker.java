public class DeltaChecker implements Observer{

    private FlightFeed subject;
    private Flight olderFlight;

    public DeltaChecker(FlightFeed currentSubject){
        subject = currentSubject;
        subject.attach(this);
    }

    public void update(){
        Flight newFlight = subject.getFlight();
        if (newFlight == null){
            return;
        }
        if (olderFlight == null){
            olderFlight = newFlight;
            return;
        }
        float longitudeChange = newFlight.longitude - olderFlight.longitude;
        float latitudeChange = newFlight.latitude - olderFlight.latitude;
        float velocityChange = newFlight.velocity - olderFlight.velocity;
        float altitudeChange = newFlight.geo_altitude - olderFlight.geo_altitude;
        System.out.println(String.format("Deltas for %s: lon: %f, lat: %f, vel: %f, alt: %f",
                newFlight.icao24, longitudeChange, latitudeChange,
                velocityChange, altitudeChange));
        olderFlight = newFlight;
        return;
    }
}
