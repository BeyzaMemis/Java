import java.util.List;

public interface PatientDao {

    String[] readFile(String fileRoad);
    List<Patient> getAllPatients();
    Patient getPatient(int index);
    void addPatient(Patient patient);
    void removePatient(Patient patient);


}
