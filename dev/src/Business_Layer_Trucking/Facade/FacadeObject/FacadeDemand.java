package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Delivery.Demand;

public class FacadeDemand {
    private int itemID;
    private int Site;
    private int amount;

    public FacadeDemand(int itemID, int site_id, int amount){
        // TODO need to be completed
        throw new UnsupportedOperationException();
    }

    public FacadeDemand(Demand demand){
        throw new UnsupportedOperationException();
    }


    public int getAmount() {
        return amount;
    }

    public int getItemID() {
        return itemID;
    }

    public int getSite() {
        return Site;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setSite(int site) {
        Site = site;
    }
}
