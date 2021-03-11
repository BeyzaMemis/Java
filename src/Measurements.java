public class Measurements extends ExaminationDecorator {

    Examination examination;
    Measurements(Examination examination){
        this.examination = examination;
    }
    @Override
    public String getDescription() {
        return examination.getDescription()+" measurements";
    }

    @Override
    public int cost() {
        return 5+examination.cost();
    }
}
