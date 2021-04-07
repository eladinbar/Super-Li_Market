package Business_Layer_Trucking.Delivery;

public class Item {
    private  int ID;
    private double weight;
    private String name;

    public Item(int id, double weight, String name){
        this.ID=id;
        this.name=name;
        this.weight=weight;
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
