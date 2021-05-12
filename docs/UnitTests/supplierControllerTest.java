import BusinessLayer.SuppliersPackage.SupplierPackage.QuantityList;
import BusinessLayer.SuppliersPackage.SupplierPackage.Supplier;
import BusinessLayer.SuppliersPackage.SupplierPackage.SupplierController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class supplierControllerTest {
    private SupplierController sc;
    private Supplier sup;
    private Supplier sup2;

    @BeforeEach
    void setUp() throws Exception {
        sc = new SupplierController();
        sup = new Supplier("shaked", "dollberg", "shaked@gmail.com", "316415551", "0542570330", 1, false, false, "check");
        sup2=new Supplier("gal", "brown", "galbrown@gmail.com", "316415553", "0542570330", 1, false, false, "check");
    }

    @AfterEach
    void tearDown() {
        sc = new SupplierController();
    }

    @Test
    void addSupplier() {
        //set

        //act
        try {
            sup = sc.addSupplier("gal", "brown", "galbrown@gmail.com", "316415553", "0542570330", 1, false, false, "check");
            //assert
            assertTrue(sc.getSuppliers().containsKey("316415553"), "failed to add new supplier, while it should be added");
            assertTrue(sc.getPersons().containsKey("316415553"), "failed to add new supplier, while it should be added");
            assertEquals(sup, sc.getSuppliers().get("316415553"), "added incorrect supplier");
        } catch (Exception e) {
            fail("failed to add supplier with correct details");
        }
    }

    @Test
    void removeSupplier() throws Exception {
        //set
        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        //act
        sc.removeSupplier(id);
        //assert
        assertFalse(sc.getSuppliers().containsKey(id),"deleted failed");
        assertFalse(sc.getPersons().containsKey(id),"deleted failed");
    }

    @Test
    void updateFirstName() throws Exception {
        //set
        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        //act
        sc.updateFirstName("316415553","shaked");
        //assert
        assertEquals("shaked",sup2.getSc().getFirstName());
    }

    @Test
    void addContactMember() throws Exception {
        //set
        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        //act
        sc.addContactMember("316415553","shaked","dollberg","shaked@gmail.com","316415552","0542228282");
        //assert
        assertTrue(sup2.getSc().getContactMembers().contains("316415552"),"the new contact memeber havnt added to supplier list");
        assertTrue(sc.getPersons().containsKey("316415552"),"the new contact memeber havnt added to supplier list");
    }

    @Test
    void deleteContactMember() throws Exception {
        //set
        String id=sup2.getSc().getId();
        String memberID=sup.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        sup2.getSc().getContactMembers().add(sup.getSc().getId());
        //act
        sc.deleteContactMember(id,memberID);
        //assert
        assertFalse(sup2.getSc().getContactMembers().contains(memberID),"contact member havnt deleted");
    }

    @Test
    void addQuantityList() throws Exception {
        //set
        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        //act
        QuantityList ql=sc.addQuantityList(id);
        //assert
        assertNotNull(sup2.getAg().getQl(),"failed to add quantityList");
        assertEquals(ql,sup2.getAg().getQl(),"added wrong quantity list to supplier");
    }

    @Test
    void editQuantityListAmount() throws Exception {
        //set
        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        sup2.getAg().addQuantityList();
        sup2.getAg().getProducts().put(1,1);
        sup2.getAg().getQl().getAmount().put(1,100);
        //act
        sc.editQuantityListAmount(id,1,99);
        //assert
        assertEquals(sup2.getAg().getQl().getAmount().get(1),99,"amount stayed 100 while it should be changed to 99");
        try {
            sc.editQuantityListAmount(id,1,0);
            fail("edited amount with invalid amount");
        }catch (Exception e){
            assertEquals(sup2.getAg().getQl().getAmount().get(1),99,"amount stayed 100 while it should be changed to 99");
        }
    }

    @Test
    void deleteQuantityList() throws Exception {
        //set
        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        sup2.getAg().addQuantityList();
        //act
        sc.deleteQuantityList(id);
        //assert
        assertNull(sup2.getAg().getQl());
    }

    @Test
    void addQuantityListItem() throws Exception {
        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        sup2.getAg().getProducts().put(1,1);
        sup2.getAg().getPrices().put(1,11);
        sup2.getAg().addQuantityList();
        //act
        sc.addQuantityListItem(id,1,100,20);
        //assert
        assertTrue(sup2.getAg().getQl().getAmount().containsKey(1),"item havnt added successfully to quantityList");
        assertTrue(sup2.getAg().getQl().getDiscount().containsKey(1),"item havnt added successfully from quantity list");
    }

    @Test
    void deleteQuantityListItem() throws Exception {
        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        sup2.getAg().addQuantityList();
        sup2.getAg().getProducts().put(1,1);
        sup2.getAg().getQl().getAmount().put(1,1);
        sup2.getAg().getQl().getDiscount().put(1,1);
        //act
        sc.deleteQuantityListItem(id,1);
        //assert
        assertFalse(sup2.getAg().getQl().getAmount().containsKey(1),"item havnt deleted successfully from quantity list");
        assertFalse(sup2.getAg().getQl().getDiscount().containsKey(1),"item havnt deleted successfully from quantity list");
    }

    @Test
    void addItemToAgreement() throws Exception {
        //set
        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        //act
        sc.addItemToAgreement(id,1,1,100);
        //assert
        assertTrue(sup2.getAg().getProducts().containsKey(1),"failed to add item to the agreement");
    }

    @Test
    void removeItemFromAgreement() throws Exception {
        //set
        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        sup2.getAg().getProducts().put(1,1);
        //act
        sc.removeItemFromAgreement(id,1);
        //assert
        assertFalse(sup2.getAg().getProducts().containsKey(1),"failed to remove item from the agreement");
    }

    @Test
    void getPrice() throws Exception {
        //set

        String id=sup2.getSc().getId();
        sc.getSuppliers().put(id,sup2);
        sup2.addItemToAgreement(1,1,100);
        sup2.addQuantityList();
        sup2.addQuantityListItem(1,100,10);
        //act
        Double price=sc.getPrice(id,90,1);
        //assert
        assertEquals(9000,price);
    }
}