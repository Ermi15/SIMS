package service.interfaces;

import model.Course;
import java.util.List;

public interface CourseService {
    void addCourse(String code, String name, int credits);
    List<Course> getAllCourses();
    void deleteCourse(String code);
}
