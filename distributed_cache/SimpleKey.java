class SimpleKey implements IKey {
    int id;

    public SimpleKey(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SimpleKey && ((SimpleKey) obj).id == this.id;
    }
}