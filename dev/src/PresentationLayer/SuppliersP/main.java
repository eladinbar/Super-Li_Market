package PresentationLayer.SuppliersP;

import DataAccessLayer.DalControllers.SupplierControllers.*;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
//        MenuPrinter m = new MenuPrinter();
//        m.startWork();
        AgreementItemsDalController.getInstance();
        OrderDalController.getInstance();
        PersonCardDalController.getInstance();
        ProductsInOrderDalController.getInstance();
        QuantityListItemsDalController.getInstance();
        SupplierCardDalController.getInstance();
        SupplierContactMembersDalController.getInstance();
    }
}
