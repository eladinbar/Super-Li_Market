package DataAccessLayer.DalObjects.TruckingObjects;

public class DalTruckingNotification {
    private String content;
    private int ID;

    public DalTruckingNotification(int ID,String content){
        this.ID=ID;
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public int getID() {
        return ID;
    }
}
