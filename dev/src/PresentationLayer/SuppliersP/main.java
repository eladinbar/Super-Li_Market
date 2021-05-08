package PresentationLayer.SuppliersP;

import DataAccessLayer.DalControllers.SupplierControllers.*;
import DataAccessLayer.DalObjects.SupplierObjects.AgreementItems;
import DataAccessLayer.DalObjects.SupplierObjects.Order;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierContactMembers;

import java.sql.SQLException;
import java.time.LocalDate;

public class main {
    public static void main(String[] args) throws SQLException {
//        MenuPrinter m = new MenuPrinter();
//        m.startWork();
        AgreementItemsDalController agreementItemsController = AgreementItemsDalController.getInstance();
        OrderDalController.getInstance();
        PersonCardDalController.getInstance();
        ProductsInOrderDalController.getInstance();
        QuantityListItemsDalController.getInstance();
        SupplierCardDalController.getInstance();
        SupplierContactMembersDalController.getInstance();
//        AgreementItems ag = new AgreementItems(1, 2, 53);
//        agreementItemsController.delete(ag);
        SupplierContactMembers sp = new SupplierContactMembers(1, 2);
        SupplierContactMembersDalController spc = SupplierContactMembersDalController.getInstance();
        spc.delete(sp);

    }
}
