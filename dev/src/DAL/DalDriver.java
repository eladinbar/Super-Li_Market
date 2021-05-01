package DAL;

public class DalDriver implements DalObject {
    private String ID;
    private String name;
    private String license;

    public DalDriver(String ID,String name,String license)
    {
        this.ID=ID;
        this.name = name;
        this.license=license;
    }
}
