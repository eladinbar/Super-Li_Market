package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Delivery.Item;

public class FacadeItem implements FacadeObject{
    private  int ID;
    private double weight;
    private String name;
    private int originId;

    public FacadeItem(int ID, double weight, String name, int originId)
    {
        this.ID=ID;
        this.name=name;
        this.weight=weight;
        this.originId = originId;

    }

    public FacadeItem(Item item) {
        this.ID = item.getID();
        this.name = item.getName();
        this.weight = item.getWeight();
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public double getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getOriginId() {
        return originId;
    }
}
