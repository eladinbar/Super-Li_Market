package PresentationLayer;

import java.util.ArrayList;
import java.util.List;

public class createObjects {
    private PresentationController pc;
    private List<Integer> suppliers;
    private List<Integer> products;
    public createObjects(PresentationController pc) {
        this.pc = pc;
        suppliers=new ArrayList<>();
        products=new ArrayList<>();
    }

    public void createObjectsForTests() {
        int id = 333333333;
        int phone = 544444444;
        for (int i = 0; i < 10; i++, phone++, id++) {
            pc.addSupplier("Supplier", "LastName", "email" + i + "@gmail.com", "" + id, "0" + phone, 1, true, true, "cash");
            suppliers.add(id);
        }for (int i = 10; i < 20; i++, phone++, id++) {
            pc.addSupplier("Supplier", "LastName", "email" + i + "@gmail.com", "" + id, "0" + phone, 1, false, true, "check");
            suppliers.add(id);
        }for (int i = 0; i < 10; i++) {
            pc.createProduct("name" + i, "osem");
            products.add(i);
        }for (int i = 10; i < 20; i++) {
            pc.createProduct("name" + i, "elit");
            products.add(i);
        }for (int i = 10; i < 30; i++) {
            pc.createProduct("name" + i, "tnuva");
            products.add(i);
        }for (int i = 30; i < 40; i++) {
            pc.createProduct("name" + i, "gad");
            products.add(i);
        }for (int i = 40; i < 50; i++) {
            pc.createProduct("name" + i, "knor");
            products.add(i);
        }
    }

}
