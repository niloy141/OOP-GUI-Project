import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String title;
    private int creditHours;

    public Course(int id, String title, int creditHours) {
        this.id = id;
        this.title = title;
        this.creditHours = creditHours;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getCreditHours() { return creditHours; }

    public void setTitle(String title) { this.title = title; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }

    @Override
    public String toString() {
        return id + " - " + title + " (" + creditHours + " cr)";
    }
}
