package Business_Layer_Trucking.Delivery;

public class Demand {
    private int itemID;
    private int Site;
    private int amount;

    public Demand(int itemID, int site_id, int amount){
        // TODO need to be completed
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
