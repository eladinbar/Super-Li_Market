package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Resources.ResourcesController;

public class ResourcesService
{
    ResourcesController rc;


    public ResourcesService(){

    }


    public void chooseTruck(int truck) {
        rc.chooseTruck(truck);
    }

    public void chooseDriver(int driver) {
        rc.chooseDriver(driver);
    }

    public void replaceTruck(int truck) {
        rc.replaceTruck(truck);
        //TODO check if need to change driver in case we replaced truck (exception?return false?)
    }

    public void makeUnavailable_Driver(int driver) {
        rc.makeUnavailable_Driver(driver);
    }

    public void makeAvailable_Driver(int driver) {
        rc.makeAvailable_Driver(driver);
    }

    public void makeUnavailable_Truck(int truck) {
        rc.makeUnavailable_Truck(truck);
    }

    public void makeAvailable_Truck(int truck) {
        rc.makeAvailable_Truck(truck);
    }
}
