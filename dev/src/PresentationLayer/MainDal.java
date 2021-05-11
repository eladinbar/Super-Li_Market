package PresentationLayer;

import DataAccessLayer.DalControllers.SupplierControllers.*;
import SerciveLayer.InventoryService;
import SerciveLayer.InventoryServiceImpl;
import SerciveLayer.SupplierService;

import java.sql.SQLException;
import java.time.LocalDate;

public class MainDal {
    public static void main(String[] args) throws SQLException {
//        PresentationController c = new PresentationController();
//        c.run();
        SupplierService sc = new SupplierService();
        InventoryService ic = new InventoryServiceImpl();
        String name = "shaked";
        String lastName = "dollberg";
        String email = "s@gmail.com";
        String id = "208677682";
        String phone = "0547972797";
      //  sc.addSupplier(name, lastName, email, id, phone, 1, true, true, "cash", "hes 3 raanana");
//        sc.getSupplier(id);
//        sc.addItemToAgreement("208677682", 1, 1, 20, ic);
        sc.removeItemFromAgreement(id, 1);

//        AgreementItemsDalController agreementItemsController = AgreementItemsDalController.getInstance();
//        OrderDalController orderDalController = OrderDalController.getInstance();
//        PersonCardDalController personCardDalController = PersonCardDalController.getInstance();
//        ProductsInOrderDalController productsInOrderDalController = ProductsInOrderDalController.getInstance();
//        QuantityListItemsDalController quantityListItemsDalController = QuantityListItemsDalController.getInstance();
//        SupplierCardDalController supplierCardDalController = SupplierCardDalController.getInstance();
//        SupplierContactMembersDalController supplierCardDalController1 = SupplierContactMembersDalController.getInstance();

        //agreement
//        AgreementItems ag = new AgreementItems(1, 2, 53);
//        agreementItemsController.insert(ag);
//        ag.setPrice(50);
//        agreementItemsController.update(ag);
//        agreementItemsController.delete(ag);

        //order
//        Order order = new Order(1, LocalDate.now(), true);
//        orderDalController.insert(order);
//        order.setDelivered(0);
//        orderDalController.update(order);
//        orderDalController.delete(order);
//
        //Person Card
//        PersonCard pc = new PersonCard("shaked", "dollberg", "s@gmail.com", "208677682", "0547972797");
//        personCardDalController.insert(pc);
//        pc.setPhone("0522434934");
//        personCardDalController.update(pc);
//        personCardDalController.delete(pc);

    }
}