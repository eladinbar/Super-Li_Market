package BusinessLayer.TruckingPackage.DeliveryPackage;

import DataAccessLayer.DalObjects.TruckingObjects.DalDemand;
import DataAccessLayer.DalControllers.TruckingControllers.DalDemandController;

import java.sql.SQLException;

public class Demand {
    private int itemID;
    private int site;//the destination - who raised the demand
    private int amount;

    public Demand(int itemID, int site_id, int amount) throws SQLException {
        this.itemID=itemID;
        this.site=site_id;
        this.amount=amount;

    }
    public Demand(DalDemand demand){
        this.itemID=demand.getItemID();
        this.amount=demand.getAmount();
        this.site=demand.getSiteID();
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

    public void setAmount(int amount) throws SQLException {
        this.amount = amount;
        DalDemandController.getInstance().update(new DalDemand(itemID,amount,site));

    }

    public void setItemID(int itemID) throws SQLException {
        this.itemID = itemID;
        DalDemandController.getInstance().update(new DalDemand(itemID,amount,site));

    }

    public void setSite(int site) throws SQLException {
        site = site;
        DalDemandController.getInstance().update(new DalDemand(itemID,amount,site));

    }
}
