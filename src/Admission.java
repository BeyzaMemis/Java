import java.util.ArrayList;
import java.util.List;

public class Admission {
    private String admissionID;
    private String patientID;
    private String examinationType;
    private String operation1;
    private String operation2;
    private String operation3;
    private ArrayList<ArrayList<String>> listOfExaminationLists;


    public String getAdmissionID() {
        return admissionID;
    }

    public void setAdmissionID(String admissionID) {
        this.admissionID = admissionID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(String examinationType) {
        this.examinationType = examinationType;
    }

    public String getOperation1() {
        return operation1;
    }

    public void setOperation1(String operation1) {
        this.operation1 = operation1;
    }

    public String getOperation2() {
        return operation2;
    }

    public void setOperation2(String operation2) {
        this.operation2 = operation2;
    }

    public String getOperation3() {
        return operation3;
    }

    public void setOperation3(String operation3) {
        this.operation3 = operation3;
    }

    public ArrayList<ArrayList<String>> getListOfExaminationLists() {
        return listOfExaminationLists;
    }

    public void setListOfExaminationLists(ArrayList<ArrayList<String>> listOfExaminationLists) {
        this.listOfExaminationLists = listOfExaminationLists;
    }
}
