package BusinessLayer.TruckingPackage.Facade.FacadeObject;

import BusinessLayer.TruckingPackage.ResourcesPackage.Driver;

public class FacadeDriver {
    private  String ID;
    private String name;
    private Driver.License licenseType;



    public FacadeDriver(String ID, String name, boolean available,  Driver.License license){
        this.ID = ID;
        this.name = name;
        this.licenseType = license;
    }

    public FacadeDriver(Driver value) {
        this.ID = value.getID();
        this.name = value.getName();;
        this.licenseType = value.getLicenseType();
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Driver.License getLicenseType() {
        return licenseType;
    }



    public void setID(String  ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLicenseType(Driver.License licenseType) {
        this.licenseType = licenseType;
    }
}
