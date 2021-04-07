package PresentationLayer;

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
                case 1:
                    addSupplierFunc();
                    break;
                case 2:
                    break;
                case 3:
                    updateSupplierDetailFunc();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    approveOrder();
                    break;
                case 9:
                    getOrder();
                    break;
                case 10:
                    addProductToOrder();
                    break;
                case 11:
                    createProduct();
                    break;
                case 12:
                    getProduct();
                    break;
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
        System.out.print("do you a permanent order days? y/n: ");
        String permanent = getStringFromUser();
        System.out.print("do you deliver the orders by yourself? y/n: ");
        String selfDelivery = getStringFromUser();
        System.out.print("how whould you like to pay?\n1. Cash\n2. Bank transfer\n3. check\nchoose number: ");
        int payment = scan.nextInt();
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
                    //pc.updateFirstName(supplierID, getStringFromUser());
                    break;
                case 2:
                    System.out.print("please enter last name: ");
                    //pc.updateLastName(supplierID, getStringFromUser());
                    break;
                case 3:
                    System.out.print("please enter phone number: ");
                    //pc.updatePhone(supplierID, getStringFromUser());
                    break;
                case 4:
                    System.out.print("please enter email: ");
                    //pc.updateEmail(supplierID, getStringFromUser());
                    break;
                case 5:
                    System.out.print("please enter company number: ");
                    //pc.updateCompanyNumber(supplierID, scan.nextInt());
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
                            //pc.updateSelfDelivery(supplierID, true);
                            break;
                        } else if (opt == 2) {
                            //pc.updateSelfDelivery(supplierID, false);
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
                            //pc.addContactMember(supplierID,firstName,lName,email,ID,phone);
                        }
                    }
                    break;
                case 10:
                    System.out.println("please enter a contact member id to remove:");
                    //pc.deleteContactMember(supplierID,getStringFromUser());
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
        pc.addProductToOrder(orderID, productID);
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
        int phone = 0544444444;
        for (int i = 0; i < 10; i++)
            pc.addSupplier("Supplier" + i, "LastName" + i, "email" + i + "@gmail.com", "" + (id + i), "" + (phone + i), 1, true, true, "cash");
        for (int i = 10; i < 20; i++)
            pc.addSupplier("Supplier" + i, "LastName" + i, "email" + i + "@gmail.com", "" + (id + i), "" + (phone + i), 1, false, true, "check");
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
