package BusinessLayer.TruckingPackage.Facade.FacadeObject;

import BusinessLayer.TruckingPackage.DeliveryPackage.Site;

public class FacadeSite implements  FacadeObject {
    private int siteID;
    private String city;
    private int deliveryArea;
    private String phoneNumber;
    private String contactName;
    private String name;

    public FacadeSite(int siteID,String city,int deliveryArea,String phoneNumber,String contactName , String name)
    {
        this.siteID = siteID;
        this.city = city;
        this.name = name;
        this.deliveryArea = deliveryArea;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
    }

    public FacadeSite(Site value) {
        this.siteID = value.getSiteID();
        this.city = value.getCity();
        this.deliveryArea = value.getDeliveryArea();
        this.phoneNumber = value.getPhoneNumber();
        this.contactName = value.getContactName();
        name = value.getName();

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public String getCity() {
        return city;
    }

    public int getSiteID() {
        return siteID;
    }

    public int getDeliveryArea() {
        return deliveryArea;
    }

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public void setDeliveryArea(int deliveryArea) {
        this.deliveryArea = deliveryArea;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
