import java.util.*;

public class OnboardingService {
    private final StudentRepo repo;
    private final StudentParser parser;
    private final StudentValidator validator;
    private final Printer printer;

    public OnboardingService(StudentRepo repo,
                              StudentParser parser,
                              StudentValidator validator,
                              Printer printer) {
        this.repo = repo;
        this.parser = parser;
        this.validator = validator;
        this.printer = printer;
    }

    // Intentionally violates SRP: parses + validates + creates ID + saves + prints.
    //Fixed SRP Violation
    public void registerFromRawInput(String raw) {

        printer.printInput(raw);

        Map<String,String> kv= parser.parse(raw);

        List<String> errors = validator.validate(kv);

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        
        String id = IdUtil.nextStudentId(repo.count());

        StudentRecord rec = new StudentRecord(id, name, email, phone, program);

        repo.save(rec);

        printer.printSuccess(rec, repo.count());
    }
}