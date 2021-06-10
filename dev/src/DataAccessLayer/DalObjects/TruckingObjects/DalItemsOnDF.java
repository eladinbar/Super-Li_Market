package DataAccessLayer.DalObjects.TruckingObjects;

public class DalItemsOnDF implements DalObject$ {
    int DFID;
    int itemID;
    int amount;

    public  DalItemsOnDF (int DFID,int itemID,int amount)
    {
        this.DFID=DFID;
        this.itemID=itemID;
        this.amount=amount;
    }

    public int getItemID() {
        return itemID;
    }

    public int getAmount() {
        return amount;
    }

    public int getDFID() {
        return DFID;
    }
}
