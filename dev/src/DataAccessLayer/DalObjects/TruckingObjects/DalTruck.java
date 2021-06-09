package DataAccessLayer.DalObjects.TruckingObjects;

import DataAccessLayer.DAL$.DalObject$;

public class DalTruck implements DalObject$ {
    private String licenseNumber;
    private String model;
    private int weightNeto;
    private int maxWeight;

    public DalTruck(String model,String licenseNumber,int weightNeto,int maxWeight)
    {
        this.licenseNumber=licenseNumber;
        this.maxWeight=maxWeight;
        this.model=model;
        this.weightNeto=weightNeto;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getWeightNeto() {
        return weightNeto;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getModel() {
        return model;
    }
}
