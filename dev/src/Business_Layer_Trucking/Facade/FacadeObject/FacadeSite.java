package Business_Layer_Trucking.Facade.FacadeObject;

public class FacadeSite {
    private int siteID;
    private String city;
    private int deliveryArea;
    private String phoneNumber;
    private String contactName;

    public FacadeSite(int siteID,String city,int deliveryArea,String phoneNumber,String contactName)
    {
        // TODO need to be completed
        throw new UnsupportedOperationException();
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
