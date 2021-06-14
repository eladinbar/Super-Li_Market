package ServiceLayer.FacadeObjects;
import BusinessLayer.TruckingPackage.DeliveryPackage.*;

public class FacadeDemand {
    private int itemID;
    private String supplier;
    private int amount;

    public FacadeDemand(int itemID, String site_id, int amount){
        this.itemID = itemID;
        this.supplier = site_id;
        this.amount = amount;
    }

    public FacadeDemand(Demand demand){
        this.itemID = demand.getItemID();
        this.supplier = demand.getSupplier();
        this.amount = demand.getAmount();

    }


    public int getAmount() {
        return amount;
    }

    public int getItemID() {
        return itemID;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

}
