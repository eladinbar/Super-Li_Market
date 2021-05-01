package DAL;

public class DalDeliveryForm implements DalObject{
    private int ID;
    private int origin;
    private int destination;
    private boolean completed;
    private int leavingWeight;
    private int TRID;

    public DalDeliveryForm(int ID,int origin, int destination,boolean completed,int leavingWeight,int TRID)
    {
        this.ID=ID;
        this.origin=origin;
        this.destination=destination;
        this.completed=completed;
        this.leavingWeight=leavingWeight;
        this.TRID=TRID;
    }
}
