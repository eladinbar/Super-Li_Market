package Trucking.Business_Layer_Trucking.Delivery;

import DAL.DalItem;
import DAL.DalSite;
import DAL.DalSiteController;

import java.sql.SQLException;

public class Site {
    private int siteID;
    private String city;
    private int deliveryArea;
    private String phoneNumber;
    private String contactName;
    private String name;

    public Site(int siteID,String city,int deliveryArea,String phoneNumber,String contactName,String name) throws SQLException {
        this.siteID=siteID;
        this.city=city;
        this.contactName=contactName;
        this.phoneNumber=phoneNumber;
        this.deliveryArea=deliveryArea;
        this.name=name;
        DalSiteController.getInstance().insert(new DalSite(siteID,name,city,deliveryArea,contactName,phoneNumber));
    }

    public Site(DalSite dalSite){
        this.siteID=dalSite.getSiteID();
        this.city= dalSite.getCity();
        this.contactName= dalSite.getContactName();
        this.phoneNumber= dalSite.getPhoneNumber();
        this.deliveryArea=dalSite.getDeliveryArea();
        this.name=dalSite.getName();
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

    public String getName() {
        return name;

    }
    public void setCity(String city) throws SQLException {
        this.city = city;
        DalSiteController.getInstance().update(new DalSite(siteID,name,city,deliveryArea,contactName,phoneNumber));

    }

    public void setContactName(String contactName) throws SQLException {
        this.contactName = contactName;
        DalSiteController.getInstance().update(new DalSite(siteID,name,city,deliveryArea,contactName,phoneNumber));

    }

    public void setDeliveryArea(int deliveryArea) throws SQLException {
        this.deliveryArea = deliveryArea;
        DalSiteController.getInstance().update(new DalSite(siteID,name,city,deliveryArea,contactName,phoneNumber));

    }

    public void setPhoneNumber(String phoneNumber) throws SQLException {
        this.phoneNumber = phoneNumber;
        DalSiteController.getInstance().update(new DalSite(siteID,name,city,deliveryArea,contactName,phoneNumber));

    }

    public void setSiteID(int siteID) throws SQLException {
        this.siteID = siteID;
        DalSiteController.getInstance().update(new DalSite(siteID,name,city,deliveryArea,contactName,phoneNumber));

    }


    public void setName(String name) throws SQLException {
        this.name = name;
        DalSiteController.getInstance().update(new DalSite(siteID,name,city,deliveryArea,contactName,phoneNumber));

    }
}
