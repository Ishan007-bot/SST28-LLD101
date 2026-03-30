class Alarm implements Observer {
    @Override
    public void update(String event) {
        if (event.equals("ALARM")) {
            System.out.println("Alarm triggered! Playing emergency sound.");
        }
    }
}