package repository;

import model.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private List<Course> courses = new ArrayList<>();
    private static final String FILE_PATH = "data/courses.txt";

    public CourseRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    courses.add(new Course(parts[0].trim(), parts[1].trim(),
                            Integer.parseInt(parts[2].trim())));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("courses.txt not found. Creating empty list.");
            saveToFile();
        } catch (Exception e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Course c : courses) {
                writer.println(c.getCode() + "," + c.getName() + "," + c.getCredits());
            }
        } catch (Exception e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }

    public void addCourse(Course course) {
        courses.add(course);
        saveToFile();
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public void deleteCourse(String code) {
        courses.removeIf(c -> c.getCode().equals(code));
        saveToFile();
    }
}
