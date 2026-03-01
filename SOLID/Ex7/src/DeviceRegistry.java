import java.util.*;

public class DeviceRegistry {
    private final java.util.List<Object> devices = new ArrayList<>();

    public void add(Object d) {
        devices.add(d);
    }

    public <T> T getFirstOfType(String simpleName) {
        for (Object d : devices) {
            if (d.getClass().getSimpleName().equals(simpleName))
                return (T) d;
        }
        throw new IllegalStateException("Missing: " + simpleName);
    }
}