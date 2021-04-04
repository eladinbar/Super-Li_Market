package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Resources.Driver;

public class FacadeDriver {
    private  int ID;
    private String name;
    private boolean available;
    private Driver.License licenseType;



    public FacadeDriver(int ID, String name, boolean available,  Driver.License license){
        this.ID = ID;
        this.name = name;
        this.available =   available;
        this.licenseType = license;
    }

    public FacadeDriver(Driver value) {
        this.ID = value.getID();
        this.name = value.getName();;
        this.available = value.isAvailable();
        this.licenseType = value.getLicenseType();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Driver.License getLicenseType() {
        return licenseType;
    }

    public boolean isAvailable() {
        return available;
    }
    public void setUnavailable()
    {
        this.available=false;
    }
    public void makeAvailable()
    {
        this.available=true;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLicenseType(Driver.License licenseType) {
        this.licenseType = licenseType;
    }
}
