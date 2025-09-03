import java.io.*;
import java.util.ArrayList;

public class DataHandler {
    public static <T> void saveData(ArrayList<T> list, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> loadData(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<T>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
