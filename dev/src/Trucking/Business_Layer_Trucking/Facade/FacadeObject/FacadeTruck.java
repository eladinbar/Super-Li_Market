package Trucking.Business_Layer_Trucking.Facade.FacadeObject;

import Trucking.Business_Layer_Trucking.Resources.Truck;

public class FacadeTruck implements FacadeObject{
    private String model;
    private  String licenseNumber;
    private int weightNeto;
    private  int maxWeight;

    public FacadeTruck(String model,String licenseNumber,int weightNeto,int maxWeight)
    {
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
    }



    public String  getLicenseNumber() {
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


}
