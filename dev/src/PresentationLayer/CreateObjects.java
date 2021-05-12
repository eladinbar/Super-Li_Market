package PresentationLayer;

import SerciveLayer.IService;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateObjects {
    private IService service;
    private List<Integer> suppliers;
    private List<Integer> products;

    public CreateObjects(IService service) {
        suppliers = new ArrayList<>();
        products = new ArrayList<>();
        this.service = service;
    }

    public void createObjectsForTests() {
        int id = 333333333;
        int phone = 544444444;
        for (int i = 0; i < 10; i++, phone++, id++) {
            service.addSupplier("Supplier", "LastName", "email" + i + "@gmail.com", "" + id, "0" + phone, 1, true, true, "cash", "adress");
            suppliers.add(id);
            service.addQuantityList("" + id);
            service.addItemToAgreement("" + id, i, i, i);
            service.addQuantityListItem("" + id, i, i, i + 3);
        }
        for (int i = 10; i < 20; i++, phone++, id++) {
            service.addSupplier("Supplier", "LastName", "email" + i + "@gmail.com", "" + id, "0" + phone, 1, false, true, "check", "adress");
            suppliers.add(id);
        }

    }

    public void setupSystem() {
        createObjectsForTests();
        //Adding categories
        service.addCategory("Dairy products", "");
        service.addCategory("Milks", "Dairy products");
        service.addCategory("Tnuva", "Milks");
        service.addCategory("Tara", "Milks");
        service.addCategory("Yotvata", "Milks");
        service.addCategory("Cheese", "Dairy products");
        service.addCategory("Hard cheeses", "Cheese");
        service.addCategory("Soft cheeses", "Cheese");
        service.addCategory("Snacks", "");
        service.addCategory("Chocolate", "Snacks");
        service.addCategory("Potato chips", "Snacks");
        service.addCategory("Drinks", "");
        service.addCategory("Soft drinks", "Drinks");
        service.addCategory("CocaCola products", "Soft drinks");
        service.addCategory("Pepsi products", "Soft drinks");
        service.addCategory("Hard drinks", "Drinks");
        service.addCategory("Beers", "Hard drinks");
        service.addCategory("Liqueurs", "Hard drinks");
        service.addCategory("Meat section", "");
        service.addCategory("chicken", "Meat section");
        service.addCategory("Fish", "Meat section");


        //Adding items
        //Tnuva milk 3% regular
        service.addItem(845, "Regular milk 3%", "Tnuva", 3.45, 5.15, 20,
                "SH-A1-L-S5", "ST-A5-S8", 40, 20, 111, new ArrayList<>());
        //Tnuva milk regulat 1%
        service.addItem(846, "Regular milk 1%", "Tnuva", 3.45, 5.15, 20,
                "SH-A1-L-S6", "ST-A5-L-S9", 40, 20, 111, new ArrayList<>());
        //Tnuva non-Lactose milk
        service.addItem(847, "Non-Lactose Milk", "Tnuva", 3.95, 6.99, 10,
                "SH-A8-R-S9", "ST-A5-L-S10", 40, 20, 111, new ArrayList<>());
        //tara regular
        service.addItem(98754, "Regular milk 3%", "Tara", 3.95, 6.99, 10,
                "SH-A19-R-S54", "ST-A12-R-S15", 40, 20, 112, new ArrayList<>());
        //tara coffee milk
        service.addItem(98755, "Coffee Milk", "Tara", 4.00, 6.99, 10,
                "SH-A19-R-55", "ST-A20-R-S1", 15, 2, 112, new ArrayList<>());
        //tara vanila milk
        service.addItem(98759, "Vanilla  Milk", "Tara", 3.95, 6.99, 10,
                "SH-A3-L-S16", "ST-A7-R-S17", 40, 20, 112, new ArrayList<>());
        //Yotvata choclate milk
        service.addItem(287, "Choclate milk", "Yotvata", 3.95, 6.99, 10,
                "SH-A3-L-S16", "ST-A7-R-S17", 40, 20, 113, new ArrayList<>());
        //Yotvata Caramel Milk
        service.addItem(265, "Caramel milk", "Tnuva", 3.95, 6.99, 10,
                "SH-A3-L-S16", "ST-A7-R-S17", 10, 0, 113, new ArrayList<>());
        //Gouda Cheese
        service.addItem(78525, "Gouda Cheese 200g", "Hard Cheeses", 3.95, 12.99, 350,
                "SH-A4-R-S1", "ST-A6-R-S2", 400, 45, 124, new ArrayList<>());
        //Cream cheese
        service.addItem(88435, "cream cheese", "Soft cheeses", 3.95, 6.99, 10,
                "SH-A28-R-S3", "ST-A14-L-S8", 40, 20, 124, new ArrayList<>());
        //frozen pizza
        service.addItem(45667, "Frozen Pizza Big", "Dairy products", 15.95, 25.99, 25,
                "SH-A84-R-S89", "ST-A53-L-S110", 20, 20, 145, new ArrayList<>());
        //Para Chocolate
        service.addItem(999, "Para Chocolate", "Chocolate", 3.95, 6.99, 10,
                "SH-A8-R-S9", "ST-A5-L-S10", 40, 20, 111, new ArrayList<>());
        //Lets chips
        service.addItem(867, "Let's Potato Chips", "Potato Chips", 3.95, 6.99, 10,
                "SH-A34-R-S9", "ST-A32-R-S10", 40, 20, 111, new ArrayList<>());
        //Tapu chips
        service.addItem(868, "Tapu chips", "Potato Chips", 3.95, 6.99, 10,
                "SH-A8-R-S9", "ST-A5-L-S10", 40, 20, 111, new ArrayList<>());

        //Snyders
        service.addItem(81474, "Snyders Chadar", "Snacks", 10, 16, 10,
                "SH-A1-R-S1", "ST-A1-L-S1", 40, 20, 357, new ArrayList<>());
        //Cola Zero
        service.addItem(11111, "Cola Zero", "CocaCola products", 2, 7, 100,
                "SH-A2-R-S4", "ST-A7-L-S45", 120, 20, 357, new ArrayList<>());
        //Fanta
        service.addItem(11115, "Fanta", "CocaCola products", 10, 16, 70,
                "SH-A2-R-S5", "ST-A7-L-S46", 40, 20, 357, new ArrayList<>());
        //Pepsi max
        service.addItem(22285, "Pepsi Max", "Pepsi products", 10, 16, 12,
                "SH-A3-R-S5", "ST-A7-L-S46", 15, 3, 817, new ArrayList<>());
        //Goldstar 500ml
        service.addItem(24285, "Goldstar 500ml", "Beers", 5.6, 12, 54,
                "SH-A6-R-S14", "ST-A6-L-S15", 17, 300, 917, new ArrayList<>());
        //Guinness beer
        service.addItem(25285, "Guinness beer 400ml", "Beers", 7.6, 14.5, 500,
                "SH-A6-R-S15", "ST-A6-L-S15", 400, 300, 1556, new ArrayList<>());

        //Jack daniels 750ml
        service.addItem(25885, "Jack daniels 750ml", "Liqueurs", 7.6, 14.5, 500,
                "SH-A6-R-S15", "ST-A6-L-S15", 400, 300, 1558, new ArrayList<>());

        //Chicken breasts 1kg
        service.addItem(7789, "Chicken breasts 1kg", "Chicken", 21.3, 45.5, 5,
                "SH-A9-L-S18", "ST-A39-L-S1", 10, 5, 9032, new ArrayList<>());
        //Chicken Wings 1kg
        service.addItem(7790, "Chicken Wings 1kg", "Chicken", 26.3, 54.5, 10,
                "SH-A9-L-S19", "ST-A39-L-S2", 15, 1, 9032, new ArrayList<>());
        //whole Salmon
        service.addItem(7794, "Whole Salmon", "Fish", 75.4, 248.3, 1,
                "SH-A9-L-S20", "ST-A39-L-S3", 4, 3, 8753, new ArrayList<>());

        //Adding sales
        //Guinness sale

        service.addItemSale("Guinness Sale", 25285, 0.2, LocalDate.of(2021, 4, 11), LocalDate.of(2021, 4, 15));

        //vannila milk sale
        Calendar start2 = Calendar.getInstance();
        start2.set(2021, Month.APRIL.getValue() - 1, 22);
        Calendar end2 = Calendar.getInstance();
        end2.set(2021, Month.APRIL.getValue() - 1, 27);
        service.addCategorySale("Yotvata Sale", "Yotvata", 0.25, LocalDate.of(2021, 4, 22), LocalDate.of(2021, 4, 27));

        //Adding suppliers discount
        LocalDate defectTime = LocalDate.of(2021, 3, 4);
        //Adding defect entries
        service.recordDefect(88435, defectTime, 4, "SH-A28-R-S3");
        LocalDate defectTime2 = LocalDate.of(2021, 3, 4);
        //Adding defect entries
        service.recordDefect(22285, defectTime2, 4, "ST-A7-L-S46");
        LocalDate defectTime3 = LocalDate.of(2021, 3, 4);
        //Adding defect entries
        service.recordDefect(98755, defectTime3, 2, "SH-A19-R-55");

        service.addItemToAgreement("333333333", 11115, 12, 100);
        service.addItemToAgreement("333333334", 11115, 12, 103);
        service.addItemToAgreement("333333335", 265, 16, 108);
        service.addItemToAgreement("333333336", 265, 16, 108);
        service.addItemToAgreement("333333337", 265, 16, 108);
        service.addItemToAgreement("333333338", 265, 16, 108);
        service.addItemToAgreement("333333333", 846, 13, 1043);
        service.addItemToAgreement("333333334", 846, 13, 1043);
        service.addItemToAgreement("333333335", 846, 13, 1043);
        service.addItemToAgreement("333333336", 846, 13, 1043);
        service.addItemToAgreement("333333337", 846, 13, 1043);
        service.addItemToAgreement("333333333", 847, 14, 88);
        service.addItemToAgreement("333333334", 847, 14, 88);
        service.addItemToAgreement("333333335", 847, 14, 88);
        service.addItemToAgreement("333333336", 847, 14, 88);
        service.addItemToAgreement("333333337", 847, 14, 88);
        service.addItemToAgreement("333333333", 845, 15, 120);
        service.addItemToAgreement("333333334", 845, 15, 120);
        service.addItemToAgreement("333333335", 845, 15, 120);
        service.addItemToAgreement("333333336", 845, 15, 120);
        service.addItemToAgreement("333333337", 845, 15, 120);
    }
}
