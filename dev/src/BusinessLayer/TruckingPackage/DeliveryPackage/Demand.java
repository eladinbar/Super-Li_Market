package BusinessLayer.TruckingPackage.DeliveryPackage;

import DataAccessLayer.DalObjects.TruckingObjects.DalDemand;
import DataAccessLayer.DalControllers.TruckingControllers.DalDemandController;

import java.sql.SQLException;

public class Demand {
    private int itemID;
    private int supplier;//the destination - who raised the demand
    private int amount;

    public Demand(int itemID, int site_id, int amount) throws SQLException {
        this.itemID=itemID;
        this.supplier =site_id;
        this.amount=amount;

    }
    public Demand(DalDemand demand){
        this.itemID=demand.getItemID();
        this.amount=demand.getAmount();
        this.supplier =demand.getSiteID();
    }

    public int getAmount() {
        return amount;
    }

    public int getItemID() {
        return itemID;
    }

    public int getSupplier() {
        return supplier;
    }

    public void setAmount(int amount) throws SQLException {
        this.amount = amount;
        DalDemandController.getInstance().update(new DalDemand(itemID,amount, supplier));

    }

    public void setItemID(int itemID) throws SQLException {
        this.itemID = itemID;
        DalDemandController.getInstance().update(new DalDemand(itemID,amount, supplier));

    }

    public void setSupplier(int supplier) throws SQLException {
        supplier = supplier;
        DalDemandController.getInstance().update(new DalDemand(itemID,amount, supplier));

    }
}
