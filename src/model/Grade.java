package model;

public class Grade {
    private String enrollmentId;
    private double marks;
    private String grade;

    public Grade(String enrollmentId, double marks, String grade) {
        this.enrollmentId = enrollmentId;
        this.marks = marks;
        this.grade = grade;
    }

    public String getEnrollmentId() { return enrollmentId; }
    public double getMarks() { return marks; }
    public String getGrade() { return grade; }
}