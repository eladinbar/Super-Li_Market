package DAL;

public class DalDeliveryForm implements DalObject{
    private int ID;
    private int origin;
    private int destination;
    private Boolean completed;
    private int leavingWeight;
    private int TRID;

    public DalDeliveryForm(int ID,int origin, int destination,Boolean completed,int leavingWeight,int TRID)
    {
        this.ID=ID;
        this.origin=origin;
        this.destination=destination;
        this.completed=completed;
        this.leavingWeight=leavingWeight;
        this.TRID=TRID;
    }

    public int getOrigin() {
        return origin;
    }

    public int getID() {
        return ID;
    }

    public int getDestination() {
        return destination;
    }

    public int getLeavingWeight() {
        return leavingWeight;
    }

    public int getTRID() {
        return TRID;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
