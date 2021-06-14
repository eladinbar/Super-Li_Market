package DataAccessLayer.DalObjects.TruckingObjects;

public class DalDemand  {
    private int itemID;
    private int amount;
    private String siteID;

    public DalDemand(int itemID,int amount,String siteID)
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

    public String getSiteID() {
        return siteID;
    }
}
