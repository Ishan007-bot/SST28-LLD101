import java.util.*;

public interface StudentRepo {

    void save(StudentRecord r);
    int count();
    List<StudentRecord> all();
    
} 