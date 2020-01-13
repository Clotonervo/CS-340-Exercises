import java.util.*;

public class Subject {
    private List<Observer> observerList = new ArrayList<Observer>();

    public void attach(Observer observer) {
        observerList.add(observer);
    }

    public void notifyObservers() {
        for (Observer obs:observerList) {
            obs.update();
        }
    }
}