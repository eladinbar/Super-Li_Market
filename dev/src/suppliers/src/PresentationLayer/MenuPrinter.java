package PresentationLayer;

import ServiceLayer.Response.ResponseT;
import ServiceLayer.objects.order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Scanner;

public class MenuPrinter {
    private PresentationController pc;
    private Scanner scan;

    public MenuPrinter() {
        scan = new Scanner(System.in);
        this.pc = new PresentationController();
        createObjectsForTests();
    }

    //the main function
    public void startWork() throws ReflectiveOperationException {
        System.out.println("welcome to Super Lee");
        int choose = 1;
        while (choose > 0) {
            choose = PrintMenu();
            switch (choose) {
                case 1 -> addSupplierFunc();
                case 2 -> getSupplier();
                case 3 -> updateSupplierDetailFunc();
                case 4 -> addQuantityList();
                case 5 -> editQuantityList();
                case 6 -> createNewOrder();
                case 7 -> setPermanentOrder();
                case 8 -> approveOrder();
                case 9 -> getOrder();
                case 10 -> addProductToOrder();
                case 11 -> createProduct();
                case 12 -> getProduct();
            }
        }
    }

    //a helper function to print the menu
    public int PrintMenu() {
        System.out.println("1. add supplier");
        System.out.println("2. get supplier");
        System.out.println("3. update supplier details");//todo add edit menu
        System.out.println("4. add quantity List");
        System.out.println("5. edit quantity List");//todo add edit menu
        System.out.println("6. create new order");//todo add menu
        System.out.println("7. set permanent order");//todo add menu
        System.out.println("8. approve order");
        System.out.println("9. get order");
        System.out.println("10. add product to order");
        System.out.println("11. add new product");
        System.out.println("12. get product");
        System.out.println("13. add item to agreement");
        System.out.println("Please choose an option:");
        return scan.nextInt();
    }

    //a helper function that add supplier to the system
    private void addSupplierFunc() throws ReflectiveOperationException {
        System.out.print("please enter following details: ");
        System.out.print("\nfirst name: ");
        String firstName = getStringFromUser();
        System.out.print("last name: ");
        String lName = getStringFromUser();
        System.out.print("email: ");
        String email = getStringFromUser();
        System.out.print("ID: ");
        String ID = getStringFromUser();
        System.out.print("phone: ");
        String phone = getStringFromUser();
        System.out.print("companyNumber: ");
        int companyNumber = scan.nextInt();
        boolean perm = false;
        while (true) {
            System.out.print("do you a permanent order days? y/n: ");
            String permanent = getStringFromUser();
            if (permanent.equals("y")) {
                perm = true;
                break;
            } else if (permanent.equals("n")) {
                perm = false;
                break;
            } else System.out.println("wrong input please choose again\n");
        }
        boolean self = false;
        while (true) {
            System.out.print("do you deliver the orders by yourself? y/n: ");
            String selfD = getStringFromUser();
            if (selfD.equals("y")) {
                self = true;
                break;
            } else if (selfD.equals("n")) {
                self = false;
                break;
            } else System.out.println("wrong input please choose again\n");
        }
        String pay = "";
        while (true) {
            System.out.print("how whould you like to pay?\n1. Cash\n2. Bank transfer\n3. check\nchoose number: ");
            int payment = scan.nextInt();
            if (payment == 1) {
                pay = "cash";
                break;
            } else if (payment == 2) {
                pay = "bankTrasfer";
                break;
            } else if (payment == 3) {
                pay = "check";
                break;
            } else System.out.println("\nwrong input please choose again\n");
        }
        System.out.println(pc.addSupplier(firstName, lName, email, ID, phone, companyNumber, perm, self, pay));
        while(true){
            System.out.println("please choose option:");
            System.out.println("1. add item to agreement");
            System.out.println("2. stop add items to agreement");
            System.out.print("Option: ");
            int opt=scan.nextInt();
            if(opt==1) {
                System.out.println("choose items to agreement: ");
                System.out.print("enter product id: ");
                int productID=scan.nextInt();
                System.out.println("enter you company product id");
                int companyProductID=scan.nextInt();
                System.out.println(pc.addItemToagreement(ID,productID,companyProductID));
            }
        }
    }

    private void getSupplier() throws ReflectiveOperationException {
        System.out.print("please enter supplier id: ");
        String supplierId = getStringFromUser();
        System.out.println(pc.getSupplier(supplierId));
    }

    private void addQuantityList() throws ReflectiveOperationException { //case 4
        System.out.print("please enter supplier id: ");
        String supplierId = getStringFromUser();
        pc.addQuantityList(supplierId);
        while (true) {
            System.out.println("1. add new product");
            System.out.println("2. exit");
            int num = scan.nextInt();
            if (num == 2)
                break;
            System.out.println("please enter the following details: ");
            System.out.print("product id: ");
            int productId = scan.nextInt();
            System.out.print("amount of products to get a discount: ");
            int amount = scan.nextInt();
            System.out.print("discount amount: ");
            int discount = scan.nextInt();
            System.out.println(pc.addQuantityListItem(supplierId, productId, amount, discount));
        }
        System.out.println(pc.getQuantityList(supplierId));
    }

    private void editQuantityList() throws ReflectiveOperationException { //case 5
        System.out.print("please enter supplier id: ");
        String supplierId = getStringFromUser();
        System.out.println("Please choose an option to Edit:");
        System.out.println("1. edit product amount");
        System.out.println("2. edit product discount");
        System.out.println("3. add new product");
        System.out.println("4. delete product");
        System.out.println("5. delete quantity list");

        int num = scan.nextInt();
        switch (num) {
            case 1: { //edit product amount
                System.out.println("product id: ");
                int prodId = scan.nextInt();
                System.out.println("new amount of products to get a discount: ");
                int amount = scan.nextInt();
                System.out.println(pc.editQuantityListAmount(supplierId, prodId, amount));
                break;
            }
            case 2: {  //edit product discount
                System.out.println("product id: ");
                int prodId = scan.nextInt();
                System.out.println("new discount: ");
                int discount = scan.nextInt();
                System.out.println(pc.editQuantityListDiscount(supplierId, prodId, discount));
                break;
            }
            case 3: { //add new product
                System.out.println("product id: ");
                int productId = scan.nextInt();
                System.out.println("amount of products to get a discount: ");
                int amount = scan.nextInt();
                System.out.println("discount amount: ");
                int discount = scan.nextInt();
                System.out.println(pc.addQuantityListItem(supplierId, productId, amount, discount));
                break;
            }
            case 4: { //delete product
                System.out.println("product id: ");
                int productId = scan.nextInt();
                System.out.println(pc.deleteQuantityListItem(supplierId, productId));
            }
            case 5: { //delete quantity list
                System.out.println(pc.deleteQuantityList(supplierId));
            }
        }
    }

    private void createNewOrder() throws ReflectiveOperationException { //case 6
        System.out.print("please enter\nsupplier id: ");
        String supplierId = getStringFromUser();
        System.out.print("date (dd/mm/yyyy): ");
        String date = getStringFromUser();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate lclDate = LocalDate.parse(date, dateTimeFormatter);
        String order = pc.createOrder(lclDate, supplierId);
        System.out.println(order);
        int orderId=-1;
        if (order.split(" ")[1].equals("details:")) {
            orderId = Integer.parseInt(order.split(" ")[3]);

            while (true) {
                System.out.println("\nplease choose an option:");
                System.out.println("1. add new product");
                System.out.println("2. exit");
                int num = scan.nextInt();
                if (num == 2)
                    break;
                System.out.println("please enter the following details: ");
                System.out.println("product id: ");
                int productId = scan.nextInt();
                System.out.println("amount of products: ");
                int amount = scan.nextInt();
                System.out.println(pc.addProductToOrder(orderId, productId, amount));
            }
        }
    }

    private void setPermanentOrder() throws ReflectiveOperationException { //case 7
        System.out.print("please enter\nsupplier id: ");
        String supplierId = getStringFromUser();
        System.out.println("day (1-7) in a week to get the order");
        int day = scan.nextInt();
        String order = pc.createPernamentOrder(day, supplierId);
        System.out.println(order);
        int orderId=-1;
        if (order.split(" ")[1].equals("details")) {
            orderId = Integer.parseInt(order.split(" ")[3]);
            while (true) {
                System.out.println("1. add new product");
                System.out.println("2. exit");
                int num = scan.nextInt();
                if (num == 2)
                    break;
                System.out.println("please enter the following details: ");
                System.out.println("product id: ");
                int productId = scan.nextInt();
                System.out.println("amount of products: ");
                int amount = scan.nextInt();
                System.out.println(pc.addProductToOrder(orderId, productId, amount));
            }
        }
    }

    //a helper funtion that edit supplier details in the system
    private void updateSupplierDetailFunc() throws ReflectiveOperationException {
        int opt = -1;
        System.out.println("please enter supplier id");
        String supplierID = getStringFromUser();
        boolean flag = true;
        while (flag) {
            System.out.println("Please choose an option to Edit:");
            System.out.println("1. edit first name");
            System.out.println("2. edit last name");
            System.out.println("3. edit phone number");
            System.out.println("4. edit email");
            System.out.println("5. edit company number");
            System.out.println("6. edit permanent orders option");
            System.out.println("7. edit delivery option");
            System.out.println("8. edit payment method");
            System.out.println("9. add contact person");
            System.out.println("10. remove contact person");
            System.out.println("for main menu choose any other option");
            System.out.print("option : ");
            int choose = scan.nextInt();
            switch (choose) {
                case 1:
                    System.out.print("please enter first name: ");
                    pc.updateFirstName(supplierID, getStringFromUser());
                    break;
                case 2:
                    System.out.print("please enter last name: ");
                    pc.updateLastName(supplierID, getStringFromUser());
                    break;
                case 3:
                    System.out.print("please enter phone number: ");
                    pc.updatePhone(supplierID, getStringFromUser());
                    break;
                case 4:
                    System.out.print("please enter email: ");
                    pc.updateEmail(supplierID, getStringFromUser());
                    break;
                case 5:
                    System.out.print("please enter company number: ");
                    pc.updateCompanyNumber(supplierID, scan.nextInt());
                    break;
                case 6:
                    while (true) {
                        System.out.print("choose:\n1. permanent days\n2. non permanent days\n option number: ");
                        opt = scan.nextInt();
                        if (opt == 1) {
                            //pc.updatePernamentDays(supplierID, true);
                            break;
                        } else if (opt == 2) {
                            //pc.updatePernamentDays(supplierID, false);
                            break;
                        } else
                            System.out.println("invalid option please chose again");
                    }
                    break;
                case 7:
                    while (true) {
                        System.out.print("choose:\n1. self delivery\n2. not self delivery\n option number: ");
                        opt = scan.nextInt();
                        if (opt == 1) {
                            pc.updateSelfDelivery(supplierID, true);
                            break;
                        } else if (opt == 2) {
                            pc.updateSelfDelivery(supplierID, false);
                            break;
                        } else
                            System.out.println("invalid option please chose again");
                    }
                    break;
                case 8:
                    while (true) {
                        System.out.print("please choose a payment method\n1. Cash\n2. Bank transfer\n3. check\nchoose number: ");
                        opt = scan.nextInt();
                        if (opt == 1) {
                            //pc.updatePayment(supplierID, "cash");
                            break;
                        }
                        if (opt == 2) {
                            //pc.updatePayment(supplierID, "bankTrasfer");
                            break;
                        }
                        if (opt == 3) {
                            //pc.updatePayment(supplierID,"check");
                            break;
                        } else
                            System.out.println("invalid option please chose again");
                    }
                case 9:
                    while (true) {
                        System.out.println("Choose an option:\n1. add a contact person\n2. back to main menu");
                        opt = scan.nextInt();
                        if (opt == 2)
                            break;
                        else if (opt != 1)
                            System.out.println("invalid option");
                        else if (opt == 1) {
                            System.out.print("please enter following details: ");
                            System.out.print("\nfirst name: ");
                            String firstName = getStringFromUser();
                            System.out.print("last name: ");
                            String lName = getStringFromUser();
                            System.out.print("email: ");
                            String email = getStringFromUser();
                            System.out.print("ID: ");
                            String ID = getStringFromUser();
                            System.out.print("phone: ");
                            String phone = getStringFromUser();
                            pc.addContactMember(supplierID, firstName, lName, email, ID, phone);
                        }
                    }
                    break;
                case 10:
                    System.out.println("please enter a contact member id to remove:");
                    pc.deleteContactMember(supplierID, getStringFromUser());
                    break;
                default:
                    flag = false;
            }
        }
    }

    //a helper function that approve that the order arrived in the system
    private void approveOrder() {
        System.out.println("please enter order ID");
        pc.approveOrder(scan.nextInt());
    }

    //a helper function to get an order from the system
    private void getOrder() {
        System.out.println("please enter order ID");
        pc.getOrder(scan.nextInt());
    }

    //a helper function to add a product to an order
    private void addProductToOrder() {
        System.out.println("please enter order ID");
        int orderID = scan.nextInt();
        System.out.println("enter a product company number ID");
        int productID = scan.nextInt();
        System.out.println("enter amount of products: ");
        int amount = scan.nextInt();
        pc.addProductToOrder(orderID, productID, amount);
    }

    //a helper function to get a product
    private void getProduct() {
        System.out.println("please enter product ID");
        pc.getProduct(scan.nextInt());
    }

    //a helper function to add a new product to the system
    private void createProduct() throws ReflectiveOperationException {
        System.out.println("please enter product name");
        String name = getStringFromUser();
        System.out.println("please enter manufacturer name");
        String manufacturer = getStringFromUser();
        pc.createProduct(name, manufacturer);
    }

    //a helper function to get a string from the user
    private String getStringFromUser() throws ReflectiveOperationException {

        boolean con = true;
        String output = "";
        while (con) {
            try {
                output = scan.next();
                if (output.equals("-1")) throw new ReflectiveOperationException("by pressing -1 you chose to go back");
                con = false;
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("wrong input please try again");
                scan.nextLine();
            }
        }
        return output;

    }

    public void createObjectsForTests() {
        int id = 333333333;
        int phone = 544444444;
        for (int i = 0; i < 10; i++, phone++, id++)
            pc.addSupplier("Supplier", "LastName", "email" + i + "@gmail.com", "" + id, "0" + phone, 1, true, true, "cash");
        for (int i = 10; i < 20; i++, phone++, id++)
            pc.addSupplier("Supplier", "LastName", "email" + i + "@gmail.com", "" + id, "0" + phone, 1, false, true, "check");
        for (int i = 0; i < 10; i++)
            pc.createProduct("name" + i, "osem");
        for (int i = 10; i < 20; i++)
            pc.createProduct("name" + i, "elit");
        for (int i = 10; i < 30; i++)
            pc.createProduct("name" + i, "tnuva");
        for (int i = 30; i < 40; i++)
            pc.createProduct("name" + i, "gad");
        for (int i = 40; i < 50; i++)
            pc.createProduct("name" + i, "knor");
    }
}
