public class Main {
    public static void main(String[] args) {
        System.out.println("=== Evaluation Pipeline ===");
        Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");

        Rubric rubric = new Rubric();
        PlagiarismCheckerInt pc = new PlagiarismChecker();
        CodeGraderInt grader = new CodeGrader();
        ReportWriterInt writer = new ReportWriter();

        EvaluationPipeline pipeline = new EvaluationPipeline(rubric, pc, grader, writer);
        pipeline.evaluate(sub);
    }
}