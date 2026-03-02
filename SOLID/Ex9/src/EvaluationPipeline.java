public class EvaluationPipeline {
    // DIP violation: high-level module constructs concretes directly
    private final PlagiarismCheckerInt pc;
    private final CodeGraderInt grader;
    private final ReportWriterInt writer;
    private final Rubric rubric;

    public EvaluationPipeline(Rubric rubric, PlagiarismCheckerInt pc, CodeGraderInt grader, ReportWriterInt writer) {
        this.pc = pc;
        this.grader = grader;
        this.writer = writer;
        this.rubric = rubric;
    }

    public void evaluate(Submission sub) {

        int plag = pc.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = grader.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = writer.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}