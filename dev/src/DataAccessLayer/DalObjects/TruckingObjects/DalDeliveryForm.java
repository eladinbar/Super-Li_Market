package DataAccessLayer.DalObjects.TruckingObjects;

public class DalDeliveryForm  {
    private int ID;
    private int destination;
    private boolean completed;
    private int leavingWeight;
    private int TRID;
    //TODO origin need to be deleted
    public DalDeliveryForm(int ID, int destination,boolean completed,int leavingWeight,int TRID)
    {
        this.ID=ID;

        this.destination=destination;
        this.completed=completed;
        this.leavingWeight=leavingWeight;
        this.TRID=TRID;
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

    public boolean isCompleted() {
        return completed;
    }
}
