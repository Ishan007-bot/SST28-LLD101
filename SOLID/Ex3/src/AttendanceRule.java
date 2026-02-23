public class AttendanceRule implements Rules{
    public String validate(StudentProfile s) {
        if (s.attendancePct < 75) {
            return "attendance below 75";
        }
        return null;
    }
}