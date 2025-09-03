import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentPanel extends JPanel {
    private final StudentManager manager;
    private JTable table;
    private DefaultTableModel model;

    public StudentPanel(StudentManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        model = new DefaultTableModel(new String[]{"ID", "Name", "Department"}, 0);
        table = new JTable(model);
        reloadTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField deptField = new JTextField();
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        form.add(new JLabel("Student ID:")); form.add(idField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Department:")); form.add(deptField);
        form.add(addBtn); form.add(updateBtn);
        form.add(deleteBtn); form.add(new JLabel(""));

        add(form, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String dept = deptField.getText().trim();
                if (name.isEmpty() || dept.isEmpty()) { JOptionPane.showMessageDialog(this, "Fill all fields"); return; }
                manager.addStudent(new Student(id, name, dept));
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
                    String name = nameField.getText().trim();
                    String dept = deptField.getText().trim();
                    manager.updateStudent(row, new Student(id, name, dept));
                    reloadTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID must be an integer.");
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                manager.deleteStudent(row);
                reloadTable();
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                idField.setText(model.getValueAt(row, 0).toString());
                nameField.setText(model.getValueAt(row, 1).toString());
                deptField.setText(model.getValueAt(row, 2).toString());
            }
        });
    }

    public void reloadTable() {
        model.setRowCount(0);
        for (Student s : manager.getStudents()) {
            model.addRow(new Object[]{s.getId(), s.getName(), s.getDepartment()});
        }
    }
}
