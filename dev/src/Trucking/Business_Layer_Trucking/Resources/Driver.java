package Trucking.Business_Layer_Trucking.Resources;

public class Driver {
    private  String ID;
    private String name;
    private License licenseType;

    public enum License{
        C(12000),
        C1(200000);
        private int size;
        private License(int size)
        {
            this.size=size;
        }
        public int getSize() {
            return size;
        }


    }

    public Driver(String ID, String name , License license){
        this.ID = ID;
        this.name = name;
        this.licenseType = license;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public License getLicenseType() {
        return licenseType;
    }



    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLicenseType(License licenseType) {
        this.licenseType = licenseType;
    }
}
