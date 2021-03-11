import java.util.List;

public interface AdmissionDao {
    String[] read_File(String fileRoad);
    List<Admission> getAllAdmissions();
    void CreateAdmission(Admission admission);
    void addExamination(String examinationtype,List<String> operation,int index,String admissionID);
    void removeAdmission(Admission admission);
    Admission getAdmission(int index);

}
