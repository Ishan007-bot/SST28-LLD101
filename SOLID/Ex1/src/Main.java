public class Main {
    public static void main(String[] args) {

        StudentRepo repo = new FakeDb();
        StudentParser parser = new StudentParser();
        StudentValidator validator = new StudentValidator();
        Printer printer = new Printer();

        System.out.println("=== Student Onboarding ===");

        OnboardingService svc = new OnboardingService(repo,parser,validator,printer);

        String raw = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        svc.registerFromRawInput(raw);

        System.out.println();
        System.out.println("-- DB DUMP --");
        System.out.print(TextTable.render3(repo));
    }
}