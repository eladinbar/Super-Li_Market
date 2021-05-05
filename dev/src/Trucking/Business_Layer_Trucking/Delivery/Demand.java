package Trucking.Business_Layer_Trucking.Delivery;

public class Demand {
    private int itemID;
    private int site;//the destination - who raised the demand
    private int amount;

    public Demand(int itemID, int site_id, int amount){
        this.itemID=itemID;
        this.site=site_id;
        this.amount=amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getItemID() {
        return itemID;
    }

    public int getSite() {
        return site;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setSite(int site) {
        site = site;
    }
}
