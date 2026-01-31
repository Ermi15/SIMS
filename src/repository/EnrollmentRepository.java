package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {
    private List<Enrollment> enrollments = new ArrayList<>();
    private static final String FILE_PATH = "data/enrollments.txt";

    public EnrollmentRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        System.out.println("Loading enrollments from: " + file.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    enrollments.add(new Enrollment(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            parts[3].trim()
                    ));
                }
            }
            System.out.println("Successfully loaded " + enrollments.size() + " enrollments");

        } catch (FileNotFoundException e) {
            System.out.println("enrollments.txt not found. Creating empty list.");
            saveToFile();
        } catch (Exception e) {
            System.out.println("Error loading enrollments: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Enrollment e : enrollments) {
                writer.println(e.getId() + "," + e.getStudentId() + "," +
                        e.getCourseCode() + "," + e.getSemesterCode());
            }
        } catch (Exception e) {
            System.out.println("Error saving enrollments: " + e.getMessage());
        }
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        saveToFile();
    }

    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }

    public void deleteEnrollment(String id) {
        enrollments.removeIf(e -> e.getId().equals(id));
        saveToFile();
    }

}
