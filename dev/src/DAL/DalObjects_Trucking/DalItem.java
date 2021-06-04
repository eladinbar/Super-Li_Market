package DAL.DalObjects_Trucking;

import DAL.DalObject;

public class DalItem implements DalObject {
    private int ID;
    private double weight;
    private String name;
    private int originSite;

    public DalItem(int ID,double weight,String name,int originSite)
    {
        this.ID=ID;
        this.name=name;
        this.weight=weight;
        this.originSite=originSite;
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

    public int getOriginSite() {
        return originSite;
    }
    //In business layer : constructor that gets (DalItem)

}