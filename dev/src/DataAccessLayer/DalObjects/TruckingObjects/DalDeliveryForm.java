package DataAccessLayer.DalObjects.TruckingObjects;

public class DalDeliveryForm  {
    private int ID;
    private String destination;
    private boolean completed;
    private int leavingWeight;
    private int TRID;
    public DalDeliveryForm(int ID, String destination,boolean completed,int leavingWeight,int TRID)
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

    public String getDestination() {
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
