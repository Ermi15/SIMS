package model;

public class Enrollment {
    private String id;
    private String studentId;
    private String courseCode;
    private String semesterCode;

    public Enrollment(String id, String studentId, String courseCode, String semesterCode) {
        this.id = id;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semesterCode = semesterCode;
    }

    public String getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public String getSemesterCode() { return semesterCode; }

}