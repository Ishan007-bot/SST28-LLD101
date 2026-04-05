class Database {
    public IValue fetch(IKey key) {
        System.out.println("DB HIT for key: " + key.hashCode());
        return new SimpleValue("Value_for_" + key.hashCode());
    }
}