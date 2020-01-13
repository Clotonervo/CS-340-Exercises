
public class FlightMonitor {
	
	public static void main(String[] args) {
	
		FlightFeed feed = new FlightFeed();
		BasicFlightInfo flightInfo = new BasicFlightInfo(feed);
		DeltaChecker deltaChecker = new DeltaChecker(feed);
		feed.start();
	}
	
}