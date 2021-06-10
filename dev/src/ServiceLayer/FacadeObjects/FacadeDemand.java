package ServiceLayer.FacadeObjects;
import BusinessLayer.TruckingPackage.DeliveryPackage.*;

public class FacadeDemand {
    private int itemID;
    private int site;
    private int amount;

    public FacadeDemand(int itemID, int site_id, int amount){
        this.itemID = itemID;
        this.site = site_id;
        this.amount = amount;
    }

    public FacadeDemand(Demand demand){
        this.itemID = demand.getItemID();
        this.site = demand.getSupplier();
        this.amount = demand.getAmount();

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
        this.site = site;
    }
}
