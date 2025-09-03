import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> students;
    private final String filename = "students.dat";

    public StudentManager() {
        students = DataHandler.loadData(filename);
    }

    public void addStudent(Student s) { students.add(s); save(); }
    public void updateStudent(int index, Student s) { students.set(index, s); save(); }
    public void deleteStudent(int index) { students.remove(index); save(); }
    public ArrayList<Student> getStudents() { return students; }
    public void forceSave() { save(); }

    private void save() { DataHandler.saveData(students, filename); }
}
