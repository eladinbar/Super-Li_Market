package DAL;

public class DalSite implements DalObject{
    private int siteID;
    private String name;
    private String city;
    private int deliveryArea;
    private String contactName;
    private String phoneNumber;


    public DalSite(int siteID,String name,String city,int deliveryArea,String contactName,String phoneNumber)
    {
        this.siteID=siteID;
        this.name=name;
        this.city=city;
        this.deliveryArea=deliveryArea;
        this.contactName=contactName;
        this.phoneNumber=phoneNumber;

    }
}
