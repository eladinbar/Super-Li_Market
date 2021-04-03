package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Delivery.Demand;

public class FacadeDemand {
    private int itemID;
    private int site;
    private int amount;

    public FacadeDemand(int itemID, int site_id, int amount){
        this.amount=amount;
        this.itemID=itemID;
        this.site=site_id;
    }

    public FacadeDemand(Demand demand){
        this.itemID=demand.getItemID();
        this.amount=demand.getAmount();
        this.site=demand.getSite();
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
