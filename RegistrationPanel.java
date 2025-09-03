import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RegistrationPanel extends JPanel {
    private final RegistrationManager regManager;
    private final StudentManager studentManager;
    private final CourseManager courseManager;

    private JTable table;
    private DefaultTableModel model;

    private JComboBox<Student> studentBox;
    private JComboBox<Course> courseBox;

    public RegistrationPanel(RegistrationManager regManager, StudentManager studentManager, CourseManager courseManager) {
        this.regManager = regManager;
        this.studentManager = studentManager;
        this.courseManager = courseManager;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        model = new DefaultTableModel(new String[]{"ID", "Student", "Course", "Semester", "Grade"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        JTextField idField = new JTextField();
        studentBox = new JComboBox<>();
        courseBox = new JComboBox<>();
        JTextField semesterField = new JTextField("Fall 2025");
        JTextField gradeField = new JTextField("N/A");

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh Lists");

        form.add(new JLabel("Registration ID:")); form.add(idField);
        form.add(new JLabel("Student:")); form.add(studentBox);
        form.add(new JLabel("Course:")); form.add(courseBox);
        form.add(new JLabel("Semester:")); form.add(semesterField);
        form.add(new JLabel("Grade:")); form.add(gradeField);
        form.add(addBtn); form.add(updateBtn);
        form.add(deleteBtn); form.add(refreshBtn);

        add(form, BorderLayout.SOUTH);

        refreshCombos();
        reloadTable();

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Student s = (Student) studentBox.getSelectedItem();
                Course c = (Course) courseBox.getSelectedItem();
                String sem = semesterField.getText().trim();
                String grade = gradeField.getText().trim();
                if (s == null || c == null) { JOptionPane.showMessageDialog(this, "Add Students and Courses first."); return; }
                regManager.addRegistration(new Registration(id, s, c, sem, grade));
                reloadTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID must be an integer.");
            }
        });

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    Student s = (Student) studentBox.getSelectedItem();
                    Course c = (Course) courseBox.getSelectedItem();
                    String sem = semesterField.getText().trim();
                    String grade = gradeField.getText().trim();
                    regManager.updateRegistration(row, new Registration(id, s, c, sem, grade));
                    reloadTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID must be an integer.");
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                regManager.deleteRegistration(row);
                reloadTable();
            }
        });

        refreshBtn.addActionListener(e -> refreshCombos());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                idField.setText(model.getValueAt(row, 0).toString());
                semesterField.setText(model.getValueAt(row, 3).toString());
                gradeField.setText(model.getValueAt(row, 4).toString());
            }
        });
    }

    public void refreshCombos() {
        DefaultComboBoxModel<Student> sModel = new DefaultComboBoxModel<>();
        for (Student s : studentManager.getStudents()) sModel.addElement(s);
        studentBox.setModel(sModel);

        DefaultComboBoxModel<Course> cModel = new DefaultComboBoxModel<>();
        for (Course c : courseManager.getCourses()) cModel.addElement(c);
        courseBox.setModel(cModel);
    }

    public void reloadTable() {
        model.setRowCount(0);
        for (Registration r : regManager.getRegistrations()) {
            model.addRow(new Object[]{ r.getId(), r.getStudent().getName(), r.getCourse().getTitle(), r.getSemester(), r.getGrade() });
        }
    }
}
