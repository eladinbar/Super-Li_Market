package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Resources.Truck;

public class FacadeTruck implements FacadeObject{
    private String model;
    private  int licenseNumber;
    private int weightNeto;
    private  int maxWeight;
    private boolean available;

    public FacadeTruck(String model,int licenseNumber,int weightNeto,int maxWeight)
    {
        available=true;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.weightNeto = weightNeto;
        this.maxWeight = maxWeight;

    }

    public FacadeTruck(Truck truck) {
        this.model = truck.getModel();
        this.licenseNumber = truck.getLicenseNumber();
        this.weightNeto = truck.getWeightNeto();
        this. maxWeight = truck.getMaxWeight();
        this.available = truck.isAvailable();
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
        this.available = false;
    }
    public void makeAvailable()
    {
       this.available = true;
    }

}
