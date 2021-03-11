

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {




        PatientDao patientDao = new PatientsDaoImpl();
        AdmissionDao admissionDao = new AdmissionDaoImpl();

        File_reader file  = new File_reader();

        String[] inputFile = file.readFile(args[0]);


        // here we create indexies of patients which is we will use it to reach specific patient
        String[] patients_for_index = file.readFile("patient.txt");

        List<String> list_of_patients_index = new ArrayList<String>();
        for (int i = 0 ; i<patients_for_index.length;i++){
            String[] line = patients_for_index[i].split("\\s+",2);

            list_of_patients_index.add(line[0]);

        }


        // here we create indexies of admissions which is we will use it to reach specific admission

        List<String> list_of_admission_indexies = new ArrayList<String>();
        List<String> list_of_admission_patientId = new ArrayList<String>();

        String[] admissions_for_index = file.readFile("admission.txt");

        for (String admissionsForIndex : admissions_for_index) {
            if (admissionsForIndex.matches(".*\\d.*")) {
                String[] line = admissionsForIndex.split("\\s+", 2);
                list_of_admission_indexies.add(line[0]);
                list_of_admission_patientId.add(line[1]);
            }
        }



        //here we will separate and read inputFile then we will do instructions
        List<String> output = new ArrayList<String>();
        List<String> patientsOut = new ArrayList<String>();
        List<String> admissionsOut = new ArrayList<String>();
        List<String> name_patientID_list = new ArrayList<String>();
        for(int i = 0; i<inputFile.length;i++){
            String[] line = inputFile[i].split("\\s+",2);
            if(line[0].equals("AddPatient") ){
                String[] lineAddPatient = inputFile[i].split("\\s+",6);
                Patient patient = new Patient(lineAddPatient[1]);
                patient.setName(lineAddPatient[2]);
                patient.setSurName(lineAddPatient[3]);
                patient.setPhoneNumber(lineAddPatient[4]);
                patient.setAddress(lineAddPatient[5]);
                patientDao.addPatient(patient);
                list_of_patients_index.add(lineAddPatient[1]);

                output.add("Patient "+lineAddPatient[1]+" "+lineAddPatient[2]+" added");

            }else if(line[0].equals("RemovePatient")){
                String[] lineRemovePatient = inputFile[i].split("\\s+",2);
                int index = list_of_patients_index.indexOf(lineRemovePatient[1]);
                Patient patient = patientDao.getPatient(index);
                String name = patient.getName();
                patientDao.removePatient(patient);
                list_of_patients_index.remove(lineRemovePatient[1]);
                // also remove from admissiontxt
                int index_admission = list_of_admission_patientId.indexOf(lineRemovePatient[1]);
                Admission admission = admissionDao.getAdmission(index_admission);
                admissionDao.removeAdmission(admission);

                output.add("Patient "+lineRemovePatient[1]+" "+name+" removed");
                list_of_admission_indexies.remove(index_admission);
                list_of_admission_patientId.remove(index_admission);

            }else if(line[0].equals("CreateAdmission")){
                ArrayList<ArrayList<String>> listOfExaminations = new ArrayList<ArrayList<String>>();
                String[] lineCreateAdmission = inputFile[i].split("\\s+",3);
                Admission admission = new Admission();
                admission.setAdmissionID(lineCreateAdmission[1]);
                admission.setPatientID(lineCreateAdmission[2]);
                admission.setListOfExaminationLists(listOfExaminations);
                admissionDao.CreateAdmission(admission);

                output.add("Admission "+lineCreateAdmission[1]+" created");

                list_of_admission_indexies.add(lineCreateAdmission[1]);
                list_of_admission_patientId.add(lineCreateAdmission[2]);

            }else if(line[0].equals("AddExamination")){
                String[] lineAddExamination = inputFile[i].split("\\s+");
                int indexOfAdmission = list_of_admission_indexies.indexOf(lineAddExamination[1]);
                List<String> operations = new ArrayList<String>();
                for(int k =0; k<(lineAddExamination.length - 3);k++){
                    operations.add(lineAddExamination[k+3]);
                }
                admissionDao.addExamination(lineAddExamination[2],operations,indexOfAdmission,lineAddExamination[1]);
                if(lineAddExamination[2].equals("Inpatient")){

                    output.add("Inpatient examination added to "+lineAddExamination[1]);
                }else{

                    output.add("Outpatient examination added to "+lineAddExamination[1]);

                }

            }else if(line[0].equals("TotalCost")){
                int Total = 0;
                ArrayList<ArrayList<String>> examinationListForCosts = new ArrayList<ArrayList<String>>();
                String[] lineTotalCost = inputFile[i].split("\\s+");
                int index = list_of_admission_indexies.indexOf(lineTotalCost[1]);
                Admission admission = admissionDao.getAdmission(index);
                examinationListForCosts = admission.getListOfExaminationLists();
                for (ArrayList<String> examinationListForCost : examinationListForCosts) {
                    if (examinationListForCost.get(0).equals("Inpatient")) {
                        Examination examination = new Inpatient();
                        for (int n = 0; n < (examinationListForCost.size() - 1); n++) {
                            switch (examinationListForCost.get(n + 1)) {
                                case "doctorvisit":
                                    examination = new Doctorvisit(examination);
                                    break;
                                case "imaging":
                                    examination = new imaging(examination);
                                    break;
                                case "tests":
                                    examination = new Tests(examination);
                                    break;
                                case "measurements":
                                    examination = new Measurements(examination);
                                    break;
                            }
                        }

                        output.add("    "+examination.getDescription()+" cost "+examination.cost()+"$");
                        Total+=examination.cost();

                    } else if (examinationListForCost.get(0).equals("Outpatient")) {
                        Examination examination = new Outpatient();
                        for (int n = 0; n < (examinationListForCost.size() - 1); n++) {
                            switch (examinationListForCost.get(n + 1)) {
                                case "doctorvisit":
                                    examination = new Doctorvisit(examination);
                                    break;
                                case "imaging":
                                    examination = new imaging(examination);
                                    break;
                                case "tests":
                                    examination = new Tests(examination);
                                    break;
                                case "measurements":
                                    examination = new Measurements(examination);
                                    break;
                            }
                        }


                        output.add("    "+examination.getDescription()+" cost "+examination.cost()+"$");
                        Total +=examination.cost();
                    }
                }

                output.add("    Total : "+Total+"$");

            }

        }
        List<Patient> allPatients = patientDao.getAllPatients();


        for (Patient patient : allPatients) {
            name_patientID_list.add(patient.getName()+" "+patient.getPatientID());
            patientsOut.add(patient.getPatientID()+"  "+patient.getName()+"   "+patient.getSurName()+" "+patient.getPhoneNumber()+" "+patient.getAddress());
        }
        Collections.sort(name_patientID_list);

        for(int i = 0;i<allPatients.size();i++){
            String patient_line= name_patientID_list.get(i);
            String[] line = patient_line.split(" ");
            int index = list_of_patients_index.indexOf(line[1]);

            Patient patient = patientDao.getPatient(index);
            output.add(patient.getPatientID()+"  "+patient.getName()+"   "+patient.getSurName()+" "+patient.getPhoneNumber()+" "+patient.getAddress());

        }

        List<Admission> allAdmissions = admissionDao.getAllAdmissions();
        int j = 0;
        for (Admission admission : allAdmissions){
            admissionsOut.add(admission.getAdmissionID()+"  "+admission.getPatientID());
            int sizeOfExaminations = admission.getListOfExaminationLists().size();
            for (int k = 0; k<sizeOfExaminations; k++){
                String examinationType = admission.getListOfExaminationLists().get(k).get(0);
                int sizeOfoperations = admission.getListOfExaminationLists().get(k).size()-1;

                if(sizeOfoperations == 1){
                    String operation1 = admission.getListOfExaminationLists().get(k).get(1);

                    admissionsOut.add(examinationType+" "+operation1);
                }else if (sizeOfoperations == 2){
                    String operation1 = admission.getListOfExaminationLists().get(k).get(1);
                    String operation2 = admission.getListOfExaminationLists().get(k).get(2);
                    admissionsOut.add(examinationType+" "+operation1+" "+operation2);

                }else if(sizeOfoperations == 3) {
                    String operation1 = admission.getListOfExaminationLists().get(k).get(1);
                    String operation2 = admission.getListOfExaminationLists().get(k).get(2);
                    String operation3 = admission.getListOfExaminationLists().get(k).get(3);
                    admissionsOut.add(examinationType+" "+operation1+" "+operation2+" "+operation3);
                }

            }
            j++;
        }






        try {
            File file1 = new File("output.txt");

            if(!file1.exists()) {
                file1.createNewFile();
            }

            PrintWriter pw = new PrintWriter(file1);
            for (int i = 0;i<output.size();i++){
                pw.println(output.get(i));
            }

            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // rewrite patient file

        try
        {
            String filename= "patient.txt";
            FileWriter fw = new FileWriter(filename,false); //the true will append the new data
            for (int i =0; i<patientsOut.size();i++){
                fw.write(patientsOut.get(i)+"\n");
            }
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }

        try
        {
            String filename= "admission.txt";
            FileWriter fw = new FileWriter(filename,false); //the true will append the new data
            for (int i =0; i<admissionsOut.size();i++){
                fw.write(admissionsOut.get(i)+"\n");
            }
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }









    }
}
