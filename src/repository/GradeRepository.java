package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GradeRepository {
    private List<Grade> grades = new ArrayList<>();
    private static final String FILE_PATH = "data/grades.txt";

    public GradeRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        System.out.println("Loading grades from: " + file.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    grades.add(new Grade(
                            parts[0].trim(),
                            Double.parseDouble(parts[1].trim()),
                            parts[2].trim()
                    ));
                }
            }
            System.out.println("Successfully loaded " + grades.size() + " grades");

        } catch (FileNotFoundException e) {
            System.out.println("grades.txt not found. Creating empty list.");
            saveToFile();
        } catch (Exception e) {
            System.out.println("Error loading grades: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Grade g : grades) {
                writer.println(g.getEnrollmentId() + "," + g.getMarks() + "," + g.getGrade());
            }
        } catch (Exception e) {
            System.out.println("Error saving grades: " + e.getMessage());
        }
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
        saveToFile();
    }

    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades);
    }

    public void deleteGrade(String enrollmentId) {
        grades.removeIf(g -> g.getEnrollmentId().equals(enrollmentId));
        saveToFile();
    }

}
