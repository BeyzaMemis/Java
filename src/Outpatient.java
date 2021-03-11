public class Outpatient extends Examination {

    public Outpatient(){
        description = "Outpatient";
    }
    @Override
    public int cost() {
        return 15;
    }
}
