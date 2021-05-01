package DAL;

public class DalTruck implements DalObject{
    private String model;
    private String licenseNumber;
    private int weightNeto;
    private int maxWeight;

    public DalTruck(String model,String licenseNumber,int weightNeto,int maxWeight)
    {
        this.licenseNumber=licenseNumber;
        this.maxWeight=maxWeight;
        this.model=model;
        this.weightNeto=weightNeto;
    }
}
