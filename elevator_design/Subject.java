interface Subject {
    void addObserver(Observer o);

    void notifyObservers(String event);
}