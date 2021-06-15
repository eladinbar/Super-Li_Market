package DataAccessLayer.DalObjects.TruckingObjects;

public class DalDriver  {
    private String ID;
    private String name;
    private String license;

    public DalDriver(String ID,String name,String license) {
        this.ID=ID;
        this.name = name;
        this.license=license;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public String getLicense() {
        return license;
    }
}
