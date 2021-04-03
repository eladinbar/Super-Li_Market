package Business_Layer_Trucking.Delivery;

public class Item {
    private  int ID;
    private int weight;
    private String name;

    public Item(int id, int weight, String name){
        this.ID=id;
        this.name=name;
        this.weight=weight;
    }

    public int getWeight() {
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

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public void setID(int ID) {
        this.ID = ID;
    }
}
