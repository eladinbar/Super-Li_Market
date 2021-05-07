package PresentationLayer;

import SerciveLayer.IService;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class createObjects {
    private IService service;
    private List<Integer> suppliers;
    private List<Integer> products;
    public createObjects(IService service) {
        suppliers=new ArrayList<>();
        products=new ArrayList<>();
        this.service = service;
    }

    public void createObjectsForTests() {
        int id = 333333333;
        int phone = 544444444;
//        for (int i = 0; i < 10; i++) {
//            pc.createProduct("name" + i, "osem");
//            products.add(i);
//        }
//        for (int i = 10; i < 20; i++) {
//            pc.createProduct("name" + i, "elit");
//            products.add(i);
//        }for (int i = 10; i < 30; i++) {
//            pc.createProduct("name" + i, "tnuva");
//            products.add(i);
//        }for (int i = 30; i < 40; i++) {
//            pc.createProduct("name" + i, "gad");
//            products.add(i);
//        }for (int i = 40; i < 50; i++) {
//            pc.createProduct("name" + i, "knor");
//            products.add(i);
//        }
        for (int i = 0; i < 10; i++, phone++, id++) {
            service.addSupplier("Supplier", "LastName", "email" + i + "@gmail.com", "" + id, "0" + phone, 1, true, true, "cash");
            suppliers.add(id);
            service.addQuantityList(""+id);
            service.addItemToAgreement(""+id, i, i, i);
            service.addQuantityListItem(""+id, i, i, i+3);
        }
        for (int i = 10; i < 20; i++, phone++, id++) {
            service.addSupplier("Supplier", "LastName", "email" + i + "@gmail.com", "" + id, "0" + phone, 1, false, true, "check");
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
        Calendar start = Calendar.getInstance();
        start.set(2021, Month.APRIL.getValue() - 1, 11);
        Calendar end = Calendar.getInstance();
        end.set(2021, Month.APRIL.getValue() - 1, 15);
        service.addItemSale("Guinness Sale", 25285, 0.2, start, end);

        //vannila milk sale
        Calendar start2 = Calendar.getInstance();
        start2.set(2021, Month.APRIL.getValue() - 1, 22);
        Calendar end2 = Calendar.getInstance();
        end2.set(2021, Month.APRIL.getValue() - 1, 27);
        service.addCategorySale("Yotvata Sale", "Yotvata", 0.25, start2, end2);

        //Adding suppliers discount
        Calendar discDate = Calendar.getInstance();
        discDate.set(2021, Month.MARCH.getValue() - 1, 3);
        service.addItemDiscount(111, 0.12, discDate, 3, 845);

        Calendar discDate2 = Calendar.getInstance();
        discDate2.set(2021, Month.MARCH.getValue() - 1, 4);
        service.addItemDiscount(111, 0.12, discDate2, 10, 846);

        Calendar discDate3 = Calendar.getInstance();
        discDate3.set(2021, Month.MARCH.getValue() - 1, 5);
        service.addItemDiscount(113, 0.10, discDate3, 20, 11111);

        Calendar defectTime = Calendar.getInstance();
        defectTime.set(2021, Month.MARCH.getValue() - 1, 4);
        //Adding defect entries
        service.recordDefect(88435, defectTime, 4, "SH-A28-R-S3");

        Calendar defectTime2 = Calendar.getInstance();
        defectTime2.set(2021, Month.MARCH.getValue() - 1, 4);
        //Adding defect entries
        service.recordDefect(22285, defectTime2, 4, "ST-A7-L-S46");
        Calendar defectTime3 = Calendar.getInstance();
        defectTime3.set(2021, Month.MARCH.getValue() - 1, 4);
        //Adding defect entries
        service.recordDefect(98755, defectTime3, 2, "SH-A19-R-55");

    }

}
