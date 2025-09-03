import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CoursePanel extends JPanel {
    private final CourseManager manager;
    private JTable table;
    private DefaultTableModel model;

    public CoursePanel(CourseManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        model = new DefaultTableModel(new String[]{"ID", "Title", "Credit Hours"}, 0);
        table = new JTable(model);
        reloadTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        JTextField idField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField creditField = new JTextField();
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        form.add(new JLabel("Course ID:")); form.add(idField);
        form.add(new JLabel("Title:")); form.add(titleField);
        form.add(new JLabel("Credit Hours:")); form.add(creditField);
        form.add(addBtn); form.add(updateBtn);
        form.add(deleteBtn); form.add(new JLabel(""));

        add(form, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String title = titleField.getText().trim();
                int credits = Integer.parseInt(creditField.getText().trim());
                if (title.isEmpty()) { JOptionPane.showMessageDialog(this, "Title required"); return; }
                manager.addCourse(new Course(id, title, credits));
                reloadTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID and Credits must be integers.");
            }
        });

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String title = titleField.getText().trim();
                    int credits = Integer.parseInt(creditField.getText().trim());
                    manager.updateCourse(row, new Course(id, title, credits));
                    reloadTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID and Credits must be integers.");
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                manager.deleteCourse(row);
                reloadTable();
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                idField.setText(model.getValueAt(row, 0).toString());
                titleField.setText(model.getValueAt(row, 1).toString());
                creditField.setText(model.getValueAt(row, 2).toString());
            }
        });
    }

    public void reloadTable() {
        model.setRowCount(0);
        for (Course c : manager.getCourses()) {
            model.addRow(new Object[]{c.getId(), c.getTitle(), c.getCreditHours()});
        }
    }
}
