package PresentationLayer.SuppliersP;

import DataAccessLayer.DalControllers.SupplierControllers.*;
import DataAccessLayer.DalObjects.SupplierObjects.AgreementItems;
import DataAccessLayer.DalObjects.SupplierObjects.Order;

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
        AgreementItems agItems = new AgreementItems(1, 2, 53);
        agreementItemsController.insert(agItems);
        Order order = new Order(1, LocalDate.now(), true);
        OrderDalController orderController = OrderDalController.getInstance();
        orderController.insert(order);
    }
}
