import java.util.ArrayList;

public class CourseManager {
    private ArrayList<Course> courses;
    private final String filename = "courses.dat";

    public CourseManager() {
        courses = DataHandler.loadData(filename);
    }

    public void addCourse(Course c) { courses.add(c); save(); }
    public void updateCourse(int index, Course c) { courses.set(index, c); save(); }
    public void deleteCourse(int index) { courses.remove(index); save(); }
    public ArrayList<Course> getCourses() { return courses; }
    public void forceSave() { save(); }

    private void save() { DataHandler.saveData(courses, filename); }
}
