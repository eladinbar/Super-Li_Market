package Business_Layer_Trucking.Delivery;

public class Site {
    private int siteID;
    private String city;
    private int deliveryArea;
    private String phoneNumber;
    private String contactName;
    private String name;

    public Site(int siteID,String city,int deliveryArea,String phoneNumber,String contactName,String name)
    {
        this.siteID=siteID;
        this.city=city;
        this.contactName=contactName;
        this.phoneNumber=phoneNumber;
        this.deliveryArea=deliveryArea;
        this.name=name;
    }

    public int getDeliveryArea() {
        return deliveryArea;
    }

    public int getSiteID() {
        return siteID;
    }

    public String getCity() {
        return city;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setDeliveryArea(int deliveryArea) {
        this.deliveryArea = deliveryArea;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
