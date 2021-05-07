package Trucking.Business_Layer_Trucking.Delivery;

import DAL.DalItem;

public class Item {
    private  int ID;
    private double weight;
    private String name;
    private int originSiteId ;

    public Item(int id, double weight, String name, int originSiteID){
        this.ID=id;
        this.name=name;
        this.weight=weight;
        this.originSiteId = originSiteID;

    }
    public Item(DalItem dalItem){
        throw new UnsupportedOperationException();
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    public void setID(int ID) {
        this.ID = ID;
    }
}
