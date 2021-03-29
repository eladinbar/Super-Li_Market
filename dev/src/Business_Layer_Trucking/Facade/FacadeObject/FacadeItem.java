package Business_Layer_Trucking.Facade.FacadeObject;

public class FacadeItem implements FacadeObject{
    private  int ID;
    private int weight;
    private String name;

    public FacadeItem(int ID, int weight, String name)
    {
        this.ID=ID;
        this.name=name;
        this.weight=weight;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public int getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
