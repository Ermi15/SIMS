package model;

import java.time.LocalDate;

public class Student extends BaseEntity {
    private String name;
    private LocalDate dateOfBirth;
    private String email;

    public Student(String id, String name, LocalDate dateOfBirth, String email) {
        super(id);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public String getName() { return name; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getEmail() { return email; }

    public int getAge() {
        return java.time.Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getDobString() {
        return dateOfBirth.toString();
    }
}