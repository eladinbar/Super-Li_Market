package DAL;

public class DalItem implements DalObject{
    private int ID;
    private double weight;
    private String name;
    private int originSite;

    public DalItem(int ID,double weight,String name,int originSite)
    {
        this.ID=ID;
        this.weight=weight;
        this.name=name;
        this.originSite=originSite;
    }

    //In business layer : constructor that gets (DalItem)

}
