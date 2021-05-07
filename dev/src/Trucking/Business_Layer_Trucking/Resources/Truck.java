package Trucking.Business_Layer_Trucking.Resources;

import DAL.DalItem;
import DAL.DalTruck;

public class Truck {
    private String model;
    private  String licenseNumber;
    private int weightNeto;
    private  int maxWeight;

    public Truck(String model,String licenseNumber,int weightNeto,int maxWeight)
    {
        this.model=model;
        this.licenseNumber=licenseNumber;
        this.weightNeto=weightNeto;
        this.maxWeight=maxWeight;

    }

    public Truck(DalTruck dalTruck){
        this.licenseNumber= dalTruck.getLicenseNumber();
        this.model= dalTruck.getModel();
        this.weightNeto= dalTruck.getWeightNeto();
        this.maxWeight= dalTruck.getMaxWeight();
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

}
