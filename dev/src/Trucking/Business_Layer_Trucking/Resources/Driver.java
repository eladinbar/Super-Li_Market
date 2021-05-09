package Trucking.Business_Layer_Trucking.Resources;

import DAL.DalControllers_Trucking.DalDriverController;
import DAL.*;
import DAL.DalObjects_Trucking.DalDriver;

import java.sql.SQLException;

public class Driver {
    private  String ID;
    private String name;
    private License licenseType;

    public enum License{
        C(12000),
        C1(200000);
        private int size;
        private License(int size)
        {
            this.size=size;
        }
        public int getSize() {
            return size;
        }


    }

    public Driver(String ID, String name , License license) throws SQLException {
        this.ID = ID;
        this.name = name;
        this.licenseType = license;

        DalDriverController.getInstance().insert(new DalDriver(ID, name, licenseToString(licenseType)));

    }

    public Driver(DalDriver dalDriver){
        this.ID=dalDriver.getID();
        this.name= dalDriver.getName();
        if (dalDriver.getLicense().equals("C1"))
            this.licenseType=License.C1;
        else this.licenseType=License.C;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public License getLicenseType() {
        return licenseType;
    }



    public void setID(String ID) throws SQLException {
        this.ID = ID;
        DalDriverController.getInstance().update(new DalDriver(ID, name, licenseToString(licenseType)));
    }

    public void setName(String name) throws SQLException {
        this.name = name;
        DalDriverController.getInstance().update(new DalDriver(ID, name, licenseToString(licenseType)));

    }

    public void setLicenseType(License licenseType) throws SQLException {
        this.licenseType = licenseType;
        DalDriverController.getInstance().update(new DalDriver(ID, name, licenseToString(licenseType)));

    }

    private String licenseToString(License license){
        String l = "C";
        if (licenseType == Driver.License.C1) {
            l = "C1";
        }
        return l;

    }
}
