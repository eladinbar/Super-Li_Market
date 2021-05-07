package Trucking.Business_Layer_Trucking.Delivery;

import DAL.DalDeliveryFormController;
import DAL.DalDemand;
import DAL.DalDemandController;
import DAL.DalItem;

import java.sql.SQLException;

public class Demand {
    private int itemID;
    private int site;//the destination - who raised the demand
    private int amount;

    public Demand(int itemID, int site_id, int amount) throws SQLException {
        this.itemID=itemID;
        this.site=site_id;
        this.amount=amount;
        DalDemandController.getInstance().insert(new DalDemand(itemID,amount,site_id));

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
