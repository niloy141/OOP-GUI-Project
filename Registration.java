import java.io.Serializable;

public class Registration implements Serializable {
    private int id;
    private Student student;
    private Course course;
    private String semester;
    private String grade;

    public Registration(int id, Student student, Course course, String semester, String grade) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.grade = grade;
    }

    public int getId() { return id; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public String getSemester() { return semester; }
    public String getGrade() { return grade; }

    public void setSemester(String semester) { this.semester = semester; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "Reg#" + id + " | " + student.getName() + " -> " + course.getTitle() + " (" + semester + ") Grade: " + grade;
    }
}
