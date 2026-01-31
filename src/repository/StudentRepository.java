package repository;

import model.Student;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private List<Student> students = new ArrayList<>();
    private static final String FILE_PATH = "data/students.txt";

    public StudentRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    LocalDate dob = LocalDate.parse(parts[2].trim());
                    String email = parts[3].trim();
                    students.add(new Student(id, name, dob, email));
                }
            }
        } catch (FileNotFoundException e) {
            saveToFile();
            System.out.println("students.txt not found. Creating empty list.");

        } catch (Exception e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Student s : students) {
                writer.println(s.getId() + "," + s.getName() + "," +
                        s.getDateOfBirth() + "," + s.getEmail());
            }
        } catch (Exception e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public void addStudent(Student student) {
        students.add(student);
        saveToFile();
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public void deleteStudent(String id) {
        students.removeIf(s -> s.getId().equals(id));
        saveToFile();
    }

    public Student findById(String studentId) {
        for (Student s : students) {
            if (s.getId().equals(studentId)) {
                return s;
            }
        }
        return null;
    }

}
