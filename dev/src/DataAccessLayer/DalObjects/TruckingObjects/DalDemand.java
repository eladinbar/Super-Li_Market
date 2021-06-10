package DataAccessLayer.DalObjects.TruckingObjects;

public class DalDemand implements DalObject$ {
    private int itemID;
    private int amount;
    private int siteID;

    public DalDemand(int itemID,int amount,int siteID)
    {
        this.amount=amount;
        this.itemID=itemID;
        this.siteID=siteID;
    }

    public int getAmount() {
        return amount;
    }

    public int getItemID() {
        return itemID;
    }

    public int getSiteID() {
        return siteID;
    }
}
