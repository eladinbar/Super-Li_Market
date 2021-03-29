package Business_Layer_Trucking.Facade.FacadeObject;

public class FacadeTruck implements FacadeObject{
    private String model;
    private  int licenseNumber;
    private int weightNeto;
    private  int maxWeight;
    private boolean available;

    public FacadeTruck(String model,int licenseNumber,int weightNeto,int maxWeight)
    {
        //TODO complete
        available=true;
        throw new UnsupportedOperationException();
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
        //TODO implement
        throw new UnsupportedOperationException();
    }
    public void makeAvailable()
    {
        //TODO implement
        throw new UnsupportedOperationException();
    }

}
