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
}
