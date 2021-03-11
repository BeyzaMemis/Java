public class Doctorvisit extends ExaminationDecorator {
    Examination examination;
    public Doctorvisit(Examination examination){
        this.examination = examination;

    }
    @Override
    public String getDescription() {

        return examination.getDescription()+" Doctorvisit";
    }

    @Override
    public int cost() {
        return 15 + examination.cost();
    }
}
