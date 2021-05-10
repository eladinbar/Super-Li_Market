package Trucking.Business_Layer_Trucking.Delivery;

import DAL.DalObjects_Trucking.DalItem;
import DAL.DalControllers_Trucking.DalItemController;

import java.sql.SQLException;

public class Item {
    private  int ID;
    private double weight;
    private String name;
    private int originSiteId ;

    public Item(int id, double weight, String name, int originSiteID) throws SQLException {
        this.ID=id;
        this.name=name;
        this.weight=weight;
        this.originSiteId = originSiteID;
        DalItemController.getInstance().insert(new DalItem(ID,weight,name,originSiteID));

    }
    public Item(DalItem dalItem){
        this.ID=dalItem.getID();
        this.weight= dalItem.getWeight();
        this.name= dalItem.getName();
        this.originSiteId= dalItem.getOriginSite();
    }

    public int getOriginSiteId() {
        return originSiteId;
    }

    public double getWeight() {
        return weight;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) throws SQLException {
        this.name = name;
        DalItemController.getInstance().update(new DalItem(ID,weight,name,originSiteId));

    }

    public String getName() {
        return name;

    }

    public void setWeight(double weight) throws SQLException {
        this.weight = weight;
        DalItemController.getInstance().update(new DalItem(ID,weight,name,originSiteId));

    }


    public void setID(int ID) throws SQLException {
        this.ID = ID;
        DalItemController.getInstance().update(new DalItem(ID,weight,name,originSiteId));

    }
}
