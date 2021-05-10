package PresentationLayer.SuppliersP;

import DataAccessLayer.DalControllers.SupplierControllers.*;
import DataAccessLayer.DalObjects.SupplierObjects.OrderDal;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCardDal;


import java.sql.SQLException;
import java.time.LocalDate;

public class main {
    public static void main(String[] args) throws SQLException {
//        MenuPrinter m = new MenuPrinter();
//        m.startWork();
        AgreementItemsDalController agreementItemsController = AgreementItemsDalController.getInstance();
        OrderDalController orderDalController = OrderDalController.getInstance();
        PersonCardDalController personCardDalController = PersonCardDalController.getInstance();
        ProductsInOrderDalController productsInOrderDalController = ProductsInOrderDalController.getInstance();
        QuantityListItemsDalController quantityListItemsDalController = QuantityListItemsDalController.getInstance();
        SupplierCardDalController supplierCardDalController = SupplierCardDalController.getInstance();
        SupplierContactMembersDalController supplierCardDalController1 = SupplierContactMembersDalController.getInstance();

        //agreement
//        AgreementItems ag = new AgreementItems(1, 2, 53);
//        agreementItemsController.insert(ag);
//        ag.setPrice(50);
//        agreementItemsController.update(ag);
//        agreementItemsController.delete(ag);

        //order
        OrderDal order = new OrderDal(1,"2", LocalDate.now(), true);
//        orderDalController.insert(order);
//        order.setDelivered(0);
//        orderDalController.update(order);
        orderDalController.delete(order);
//
        //Person Card
//        PersonCardDal pc = new PersonCardDal("shaked", "dollberg", "s@gmail.com", "208677682", "0547972797");
//        personCardDalController.insert(pc);
//        pc.setPhone("0522434934");
//        personCardDalController.update(pc);
//        personCardDalController.delete(pc);

    }
}