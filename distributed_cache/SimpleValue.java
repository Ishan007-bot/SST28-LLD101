class SimpleValue implements IValue {
    String data;

    public SimpleValue(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data;
    }
}