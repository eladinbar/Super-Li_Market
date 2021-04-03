package Business_Layer_Trucking.Resources;

public class Truck {
    private String model;
    private  int licenseNumber;
    private int weightNeto;
    private  int maxWeight;
    private boolean available;

    public Truck(String model,int licenseNumber,int weightNeto,int maxWeight)
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

    public int getLicenseNumber() {
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
