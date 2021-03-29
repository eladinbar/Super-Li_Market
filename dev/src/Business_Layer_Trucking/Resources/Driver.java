package Business_Layer_Trucking.Resources;

public class Driver {
    private  int ID;
    private String name;
    private boolean available;
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

    public Driver(int ID, String name, boolean available,  License license){
        this.ID = ID;
        this.name = name;
        this.available =   available;
        this.licenseType = license;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public License getLicenseType() {
        return licenseType;
    }

    public boolean isAvailable() {
        return available;
    }
    public void setUnavailable()
    {
        available=false;
    }
    public void makeAvailable()
    {
        available=true;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLicenseType(License licenseType) {
        this.licenseType = licenseType;
    }
}
