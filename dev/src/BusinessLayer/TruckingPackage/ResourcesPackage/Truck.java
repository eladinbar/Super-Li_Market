package BusinessLayer.TruckingPackage.ResourcesPackage;

import DataAccessLayer.DalObjects.TruckingObjects.DalTruck;
import DataAccessLayer.DalControllers.TruckingControllers.DalTruckController;

import java.sql.SQLException;

public class Truck {
    private String model;
    private  String licenseNumber;
    private int weightNeto;
    private  int maxWeight;

    public Truck(String model,String licenseNumber,int weightNeto,int maxWeight) throws SQLException {
        this.model=model;
        this.licenseNumber=licenseNumber;
        this.weightNeto=weightNeto;
        this.maxWeight=maxWeight;
        DalTruckController.getInstance().insert(new DalTruck(model,licenseNumber,weightNeto,maxWeight));
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

    public void setLicenseNumber(String licenseNumber) throws SQLException {
        this.licenseNumber = licenseNumber;
        DalTruckController.getInstance().update(new DalTruck(model,licenseNumber,weightNeto,maxWeight));

    }

    public void setMaxWeight(int maxWeight) throws SQLException {
        this.maxWeight = maxWeight;
        DalTruckController.getInstance().update(new DalTruck(model,licenseNumber,weightNeto,maxWeight));

    }

    public void setModel(String model) throws SQLException {
        this.model = model;
        DalTruckController.getInstance().update(new DalTruck(model,licenseNumber,weightNeto,maxWeight));

    }

    public void setWeightNeto(int weightNeto) throws SQLException {
        this.weightNeto = weightNeto;
        DalTruckController.getInstance().insert(new DalTruck(model,licenseNumber,weightNeto,maxWeight));

    }
}
