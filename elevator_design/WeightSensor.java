import java.util.*;

class WeightSensor implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private double weight;

    public void setWeight(double weight) {
        this.weight = weight;
        if (weight > 700) {
            notifyObservers("OVERWEIGHT");
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer o : observers) {
            o.update(event);
        }
    }
}