import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdmissionDaoImpl implements AdmissionDao{

    List<Admission> Admissions;
    AdmissionDaoImpl(){
        Admissions = new ArrayList<Admission>();
        String[] admissions_array = read_File("admission.txt");
        int admission_total_numbers = 0;
        int admission_line_number = 1;
        List<Integer> admissions_line_numbers = new ArrayList<Integer>();
        List<Integer> index_of_admissions_ = new ArrayList<Integer>();

        for(int i = 0; i<admissions_array.length;i++){
            if(admissions_array[i].matches(".*\\d.*")){
                admission_total_numbers += 1;
                admissions_line_numbers.add(admission_line_number);
                admission_line_number = 1;
                index_of_admissions_.add(i);


            }else{
                admission_line_number+=1;
            }

        }
        //here we try to change admissions lines numbers list as we want;
        admissions_line_numbers.remove(admissions_line_numbers.get(0));
        int index_length =index_of_admissions_.size();

        admissions_line_numbers.add(admissions_array.length-index_of_admissions_.get(index_length-1));


        // here we will create our Admissions Data base  list

        int i = 0;
        int index_in_loop_admissions = 0;

        while (i<admissions_array.length){
            Admission admission = new Admission();
            ArrayList<ArrayList<String>> listOfexaminations = new ArrayList<ArrayList<String>>();


            for(int j = 0; j<admissions_line_numbers.get(index_in_loop_admissions); j++){
                if(admissions_array[i].matches(".*\\d.*")){
                    String[] line = admissions_array[i].split("\\s+");
                    admission.setAdmissionID(line[0]);
                    admission.setPatientID(line[1]);

                }
                else if (!admissions_array[i].matches(".*\\d.*")){
                    ArrayList<String> singleExamination = new ArrayList<String>();

                    String[] line = admissions_array[i].split("\\s+");
                    admission.setExaminationType(line[0]);
                    singleExamination.add(admission.getExaminationType());
                    if(line.length == 2){
                        admission.setOperation1(line[1]);
                        admission.setOperation2("nope");
                        admission.setOperation3("nope");
                        singleExamination.add(admission.getOperation1());


                    }else if(line.length == 3){
                        admission.setOperation1(line[1]);
                        admission.setOperation2(line[2]);
                        singleExamination.add(admission.getOperation1());
                        singleExamination.add(admission.getOperation2());

                        admission.setOperation3("nope");

                    }else if(line.length == 4){
                        admission.setOperation1(line[1]);
                        admission.setOperation2(line[2]);
                        admission.setOperation3(line[3]);
                        singleExamination.add(admission.getOperation1());
                        singleExamination.add(admission.getOperation2());
                        singleExamination.add(admission.getOperation3());

                    }
                    listOfexaminations.add(singleExamination);
                }
                i++;


            }
            admission.setListOfExaminationLists(listOfexaminations);
            Admissions.add(admission);
            index_in_loop_admissions+=1;



        }

    }

    @Override
    public String[] read_File(String fileRoad) {
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

    @Override
    public List<Admission> getAllAdmissions() {
        return Admissions;
    }

    @Override
    public void CreateAdmission(Admission admission) {
        Admissions.add(admission);


    }

    @Override
    public void addExamination(String examinationtype, List<String> operation,int index,String admissionID) {

        Admission admission = Admissions.get(index);
        ArrayList<String> singleExamination = new ArrayList<String>();
        admission.setExaminationType(examinationtype);
        singleExamination.add(admission.getExaminationType());

        for (String s : operation) {
            admission.setOperation1(s);
            singleExamination.add(admission.getOperation1());

        }

        admission.getListOfExaminationLists().add(singleExamination);


    }

    @Override
    public void removeAdmission(Admission admission) {
        Admissions.remove(admission);

    }

    @Override
    public Admission getAdmission(int index) {

        return Admissions.get(index);
    }
}
