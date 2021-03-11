import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PatientsDaoImpl implements PatientDao {

    @Override
    public String[] readFile(String fileRoad) {
        try {
            int i = 0;
            int length = Files.readAllLines(Paths.get(fileRoad)).size();
            String[] results = new String[length];
            for (String line : Files.readAllLines(Paths.get(fileRoad))) {
                results[i++] = line;
            }
            return results;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    List<Patient> Patients;

    PatientsDaoImpl(){
        String[] patients_array = readFile("patient.txt");

        Patients = new ArrayList<Patient>();

        // here we will create a database for patients by using list
        for(int i = 0; i<patients_array.length; i++){
            String[] line = patients_array[i].split("\\s+",5);


            Patient patient = new Patient(line[0]);
            patient.setName(line[1]);
            patient.setSurName(line[2]);
            patient.setPhoneNumber(line[3]);
            patient.setAddress(line[4]);

            Patients.add(patient);


        }



    }

    @Override
    public List<Patient> getAllPatients() {

        return Patients;
    }

    @Override
    public Patient getPatient(int index) {

        return Patients.get(index);
    }

    @Override
    public void addPatient(Patient patient) {
        Patients.add(patient);


    }

    @Override
    public void removePatient(Patient patient) {

        Patients.remove(patient);


    }


}
