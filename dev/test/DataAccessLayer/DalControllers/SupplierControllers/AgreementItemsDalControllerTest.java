package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalObjects.SupplierObjects.agreementItemsDal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgreementItemsDalControllerTest {
    AgreementItemsDalController aidController;

    @BeforeAll
    void prepareController(){
        try{
            aidController = AgreementItemsDalController.getInstance();
            aidController.createTable();
        } catch (Exception e){
            fail("cant get controller Instance or create table");
        }
    }

    @Test
    void insertItemToAgreement(){
        //prep


    }
}