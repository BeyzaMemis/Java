public class Inpatient extends Examination {

    public Inpatient(){
        description = "Inpatient";
    }
    @Override
    public int cost() {
        return 10;
    }
}
