import java.util.ArrayList;

public class RegistrationManager {
    private ArrayList<Registration> registrations;
    private final String filename = "registrations.dat";

    public RegistrationManager() {
        registrations = DataHandler.loadData(filename);
    }

    public void addRegistration(Registration r) { registrations.add(r); save(); }
    public void updateRegistration(int index, Registration r) { registrations.set(index, r); save(); }
    public void deleteRegistration(int index) { registrations.remove(index); save(); }
    public ArrayList<Registration> getRegistrations() { return registrations; }
    public void forceSave() { save(); }

    private void save() { DataHandler.saveData(registrations, filename); }
}
