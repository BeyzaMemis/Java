public class imaging extends ExaminationDecorator {
    Examination examination;

    imaging(Examination examination){
        this.examination = examination;

    }

    @Override
    public String getDescription() {
        return examination.getDescription()+" imaging";
    }

    @Override
    public int cost() {
        return 10 + examination.cost();
    }
}
