/*
package Tests;

import Trucking.Business_Layer_Trucking.Facade.FacadeObject.ResourcesService;
import Trucking.Business_Layer_Trucking.Resources.Driver;
import Trucking.Business_Layer_Trucking.Resources.ResourcesController;
import Trucking.Business_Layer_Trucking.Resources.Truck;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesServiceTest {


    ResourcesController rc;
    ResourcesService instance = null;
    @BeforeEach
    public void setup()
    {
        rc=ResourcesController.getInstance();
        instance=ResourcesService.getInstance();
        HashMap<String, Truck> trucks=new HashMap<>();
        Truck truck=new Truck("volvo","1234567",1000,10000);
        Truck truck2=new Truck("volvi","2345678",2500,5000);
        trucks.put(truck.getLicenseNumber(),truck);
        trucks.put(truck2.getLicenseNumber(),truck2);
        rc.setTrucks(trucks);
        rc.setDrivers(new HashMap<>());
    }


    //--------------------------- Simple Adders-----------------------------------------
    @Test
    void testAddTruck_Ok() {
        rc.addTruck("volvi","3456789",2500,5000);
        assertEquals(3,rc.getTrucks().size());
    }


    @Test
    void testAddDriver(){
        rc.addDriver("1234567","raz", Driver.License.C1);
        assertNotNull(rc.getDrivers().get("1234567"));
    }
    @Test
    void testMultiAddDriver(){
        rc.addDriver("1234567","raz", Driver.License.C1);
        rc.addDriver("1123456","raz", Driver.License.C1);
        rc.addDriver("1134567","raz", Driver.License.C1);
        assertEquals(3,rc.getDrivers().size());
    }

    //--------------------------- Simple Getters-----------------------------------------

    @Test
    void testGetDrivers_NoDrivers() {
        assertEquals(0,rc.getDrivers().size());
    }
    @Test
    void testGetDrivers()
    {
        rc.addDriver("1234567","raz", Driver.License.C1);
        rc.addDriver("1123456","raz", Driver.License.C1);
        rc.addDriver("1134567","raz", Driver.License.C1);
        assertEquals(3,rc.getDrivers().size());
    }

    @Test
    void testGetTrucks() {
        assertEquals(2,rc.getTrucks().size());
    }

    //--------------------------- Availability methods -----------------------------------------


    @Test
    void testMakeUnavailable_Driver() {
        rc.addDriver("1234567","raz", Driver.License.C1);
        rc.addDriver("1123456","raz", Driver.License.C1);
        rc.addDriver("1134567","raz", Driver.License.C1);
        rc.makeUnavailable_Driver("1234567");
        assertFalse(rc.getDrivers().get("1234567").isAvailable());

    }

    @Test
    void testMakeAvailable_Driver() {
        rc.addDriver("1234567","raz", Driver.License.C1);
        rc.addDriver("1123456","raz", Driver.License.C1);
        rc.addDriver("1134567","raz", Driver.License.C1);
        rc.makeUnavailable_Driver("1234567");
        rc.makeAvailable_Driver("1234567");
        assertTrue(rc.getDrivers().get("1234567").isAvailable());
    }

    @Test
    void testMakeUnavailable_Truck() {
        rc.makeUnavailable_Truck("1234567");
        assertFalse(rc.getTrucks().get("1234567").isAvailable());
    }

    @Test
    void testMakeAvailable_Truck(){
        rc.makeUnavailable_Truck("1234567");
        rc.makeAvailable_Truck("1234567");
        assertTrue(rc.getTrucks().get("1234567").isAvailable());
    }

    @Test
    void testGetAvailableTrucks() {
        rc.makeUnavailable_Truck("1234567");
        assertEquals(1,rc.getAvailableTrucks().size());
    }
    @Test
    void testGetAvailableDrivers() {
        rc.addDriver("1234567","raz", Driver.License.C1);
        rc.addDriver("1123456","raz", Driver.License.C1);
        rc.addDriver("1134567","raz", Driver.License.C1);
        rc.makeUnavailable_Driver("1234567");
        assertEquals(2,rc.getAvailableTrucks().size());
    }

    @Test
    void testChooseTruck(){
        rc.chooseTruck("1234567");
        assertEquals("1234567",rc.getCurrTruckNumber());
    }

    @Test
    void testChooseDriver() {
        rc.addDriver("1234567","raz", Driver.License.C1);
        rc.addDriver("1123456","raz", Driver.License.C1);
        rc.addDriver("1134567","raz", Driver.License.C1);
        rc.chooseDriver("1234567");
        assertEquals("1234567",rc.getCurrDriverID());
    }
    @Test
    void testSaveReport() {
        rc.addDriver("1234567","raz", Driver.License.C1);
        rc.chooseTruck("1234567");
        rc.chooseDriver("1234567");
        rc.saveReport();
        assertFalse(rc.getTrucks().get("1234567").isAvailable());


    }
}
*/
