import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    private StudentManager studentManager;
    private CourseManager courseManager;
    private RegistrationManager registrationManager;

    private StudentPanel studentPanel;
    private CoursePanel coursePanel;
    private RegistrationPanel registrationPanel;

    public MainFrame() {
        setTitle("Student Course Registration System");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Managers shared across panels
        studentManager = new StudentManager();
        courseManager = new CourseManager();
        registrationManager = new RegistrationManager();

        // Sidebar (no tabs)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(10, 1, 8, 8));
        sidebar.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JButton studentsBtn = new JButton("Students");
        JButton coursesBtn = new JButton("Courses");
        JButton regsBtn = new JButton("Registrations");
        JButton saveAllBtn = new JButton("Save All");

        sidebar.add(studentsBtn);
        sidebar.add(coursesBtn);
        sidebar.add(regsBtn);
        sidebar.add(new JLabel(""));
        sidebar.add(saveAllBtn);

        add(sidebar, BorderLayout.WEST);

        // Content area with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        studentPanel = new StudentPanel(studentManager);
        coursePanel = new CoursePanel(courseManager);
        registrationPanel = new RegistrationPanel(registrationManager, studentManager, courseManager);

        contentPanel.add(studentPanel, "students");
        contentPanel.add(coursePanel, "courses");
        contentPanel.add(registrationPanel, "registrations");

        add(contentPanel, BorderLayout.CENTER);

        // Button actions
        studentsBtn.addActionListener(e -> {
            studentPanel.reloadTable();
            cardLayout.show(contentPanel, "students");
        });

        coursesBtn.addActionListener(e -> {
            coursePanel.reloadTable();
            cardLayout.show(contentPanel, "courses");
        });

        regsBtn.addActionListener(e -> {
            registrationPanel.refreshCombos();
            registrationPanel.reloadTable();
            cardLayout.show(contentPanel, "registrations");
        });

        saveAllBtn.addActionListener(e -> {
            studentManager.forceSave();
            courseManager.forceSave();
            registrationManager.forceSave();
            JOptionPane.showMessageDialog(this, "All data saved.");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
