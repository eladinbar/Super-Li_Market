package DAL;

public class DalSite implements DalObject{
    private int ID;
    private double weight;
    private String name;
    private int originSite;

    public DalSite(int ID,double weight,String name,int originSite)
    {
        this.ID=ID;
        this.weight=weight;
        this.name=name;
        this.originSite=originSite;
    }
}
