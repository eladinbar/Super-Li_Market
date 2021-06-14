package BusinessLayer.TruckingPackage.DeliveryPackage;

import DataAccessLayer.DalObjects.TruckingObjects.DalDemand;
import DataAccessLayer.DalControllers.TruckingControllers.DalDemandController;

import java.sql.SQLException;

import static java.lang.System.exit;

public class Demand {
    private int itemID;
    private String supplier;//the destination - who raised the demand
    private int amount;

    public Demand(int itemID, String site_id, int amount) throws SQLException {
        this.itemID=itemID;
        this.supplier =site_id;
        this.amount=amount;


        DalDemandController.getInstance().insert(new DalDemand(this.itemID,this.amount,this.supplier));

    }
    public Demand(DalDemand demand) throws SQLException {

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

    public String getSupplier() {
        return supplier;
    }

    public void setAmount(int amount) throws SQLException {
        this.amount = amount;
        if (amount==0)
        {
            DalDemandController.getInstance().delete(new DalDemand(itemID,0,supplier));
        }
        DalDemandController.getInstance().update(new DalDemand(itemID,amount, supplier));

    }

    public void setItemID(int itemID) throws SQLException {
        this.itemID = itemID;
        DalDemandController.getInstance().update(new DalDemand(itemID,amount, supplier));

    }

    public void setSupplier(String supplier) throws SQLException {
        this.supplier = supplier;
        DalDemandController.getInstance().update(new DalDemand(itemID,amount, supplier));

    }
}
