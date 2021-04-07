package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Delivery.Item;

public class FacadeItem implements FacadeObject{
    private  int ID;
    private double weight;
    private String name;

    public FacadeItem(int ID, double weight, String name)
    {
        this.ID=ID;
        this.name=name;
        this.weight=weight;
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
}
