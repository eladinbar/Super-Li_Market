//import BusinessLayer.SuppliersPackage.SupplierPackage.Payment;
//import DataAccessLayer.DalControllers.SupplierControllers.SupplierCardDalController;
//import DataAccessLayer.DalObjects.SupplierObjects.SupplierCardDal;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SupplierCardDalControllerTest {
//    static SupplierCardDalController scdc;
//
//    @BeforeAll
//    static void getInstance() {
//        try {
//            scdc = SupplierCardDalController.getInstance();
//        } catch (SQLException throwables) {
//            fail("cant get Instance");
//        }
//    }
//
//    @Test
//    void insert() {
//        SupplierCardDal supplierCardDal = null;
//        try {
//            supplierCardDal = new SupplierCardDal(333333333, 1,
//                    true,true, Payment.bankTrasfer,"Address");
//        } catch (SQLException throwables) {
//            fail("can't create suppler person card" + throwables.getMessage());
//        }
//        assertNotNull(supplierCardDal);
//        try{
//            scdc.insert(supplierCardDal);
//        } catch (Exception e){
//            fail("can't add suppler person card" + e.getMessage());
//        }
//
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void select() {
//    }
//}