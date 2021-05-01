package Trucking.Business_Layer_Trucking.Resources;

public class Truck {
    private String model;
    private  String licenseNumber;
    private int weightNeto;
    private  int maxWeight;
    private boolean available;

    public Truck(String model,String licenseNumber,int weightNeto,int maxWeight)
    {
        this.model=model;
        this.licenseNumber=licenseNumber;
        this.weightNeto=weightNeto;
        this.maxWeight=maxWeight;
        available=true;

    }

    public boolean isAvailable() {
        return available;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getWeightNeto() {
        return weightNeto;
    }

    public String getModel() {
        return model;
    }
    public void setUnavailable()
    {
        available=false;
    }
    public void makeAvailable()
    {
        available=true;
    }

}
