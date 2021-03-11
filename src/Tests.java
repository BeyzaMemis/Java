public class Tests extends ExaminationDecorator {
    Examination examination;
    Tests(Examination examination){
        this.examination = examination;
    }
    @Override
    public String getDescription() {
        return examination.getDescription()+" tests";
    }

    @Override
    public int cost() {
        return 7+examination.cost();
    }
}
