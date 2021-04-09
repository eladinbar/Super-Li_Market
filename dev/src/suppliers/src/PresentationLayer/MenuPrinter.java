package PresentationLayer;

import BusinessLayer.supplierPackage.supplier;

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
        createObjects c = new createObjects(pc);
        c.createObjectsForTests();
    }

    //the main function
    public void startWork() throws ReflectiveOperationException {
        int choose = 1;
/*        boolean logged = true;
        System.out.println("\nWelcome to Super-Lee, Please enter password: ");
        while (logged) {
            String pass = getStringFromUser();
            if (!pass.equals("superlee7"))
                System.out.print("Wrong Password, try again: ");
            else
                logged = false;
        }*/
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
                case 13 -> editAgrreement();
                case 14 -> removeProductFromOrder();
                case 15 -> getQuantityList();
            }
        }
    }

    //a helper function to print the menu
    public int PrintMenu() throws ReflectiveOperationException {
        System.out.println("welcome to Super Lee");
        System.out.println("1. add supplier");
        System.out.println("2. get supplier");
        System.out.println("3. update supplier details");
        System.out.println("4. add quantity List");
        System.out.println("5. edit quantity List");
        System.out.println("6. create new order");
        System.out.println("7. set permanent order");
        System.out.println("8. approve order");
        System.out.println("9. get order");
        System.out.println("10. add product to order");
        System.out.println("11. add new product");
        System.out.println("12. get product");
        System.out.println("13. edit agreement");
        System.out.println("14. remove product from order");
        System.out.println("15. get quantityList");
        System.out.println("Please choose an option:");
        return getIntFromUser();
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
        int companyNumber = getIntFromUser();
        boolean perm;
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
        boolean self;
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
            System.out.print("how would you like to pay?\n1. Cash\n2. Bank transfer\n3. check\nchoose number: ");
            int payment = getIntFromUser();
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
        String print = pc.addSupplier(firstName, lName, email, ID, phone, companyNumber, perm, self, pay);
        System.out.println(print);
        if (!print.split(" ")[0].equals("\nError:")) {
            while (true) {
                System.out.println("please choose option:");
                System.out.println("1. add item to agreement");
                System.out.println("2. stop add items to agreement");
                System.out.print("Option: ");
                int opt = getIntFromUser();
                if (opt == 1) {
                    System.out.println("choose items to agreement: ");
                    System.out.print("enter product id: ");
                    int productID = getIntFromUser();
                    System.out.print("enter your company product id: ");
                    int companyProductID = getIntFromUser();
                    System.out.print("enter price: ");
                    int price = getIntFromUser();
                    System.out.println(pc.addItemToagreement(ID, productID, companyProductID, price));
                } else if (opt == 2) break;
                else System.out.println("invalid option try again");
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
        System.out.println(pc.addQuantityList(supplierId));
        while (true) {
            System.out.println("please choose an option");
            System.out.println("1. add new product");
            System.out.println("2. exit");
            System.out.print("option: ");
            int num = getIntFromUser();
            if (num == 2)
                break;
            System.out.println("please enter the following details: ");
            System.out.print("product id: ");
            int productId = getIntFromUser();
            System.out.print("amount of products to get a discount: ");
            int amount = getIntFromUser();
            System.out.print("discount amount: ");
            int discount = getIntFromUser();
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

        int num = getIntFromUser();
        switch (num) {
            case 1: { //edit product amount
                System.out.println("product id: ");
                int prodId = getIntFromUser();
                System.out.println("new amount of products to get a discount: ");
                int amount = getIntFromUser();
                System.out.println(pc.editQuantityListAmount(supplierId, prodId, amount));
                break;
            }
            case 2: {  //edit product discount
                System.out.println("product id: ");
                int prodId = getIntFromUser();
                System.out.println("new discount: ");
                int discount = getIntFromUser();
                System.out.println(pc.editQuantityListDiscount(supplierId, prodId, discount));
                break;
            }
            case 3: { //add new product
                System.out.println("product id: ");
                int productId = getIntFromUser();
                System.out.println("amount of products to get a discount: ");
                int amount = getIntFromUser();
                System.out.println("discount amount: ");
                int discount = getIntFromUser();
                System.out.println(pc.addQuantityListItem(supplierId, productId, amount, discount));
                break;
            }
            case 4: { //delete product
                System.out.println("product id: ");
                int productId = getIntFromUser();
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
        int orderId = -1;
        if (order.split(" ")[1].equals("details:")) {
            orderId = Integer.parseInt(order.split(" ")[3]);

            while (true) {
                System.out.println("\nplease choose an option:");
                System.out.println("1. add new product");
                System.out.println("2. exit");
                int num = getIntFromUser();
                if (num == 2)
                    break;
                System.out.println("please enter the following details: ");
                System.out.print("product id: ");
                int productId = getIntFromUser();
                System.out.print("amount of products: ");
                int amount = getIntFromUser();
                System.out.println(pc.addProductToOrder(orderId, productId, amount));
            }
        }
    }

    private void setPermanentOrder() throws ReflectiveOperationException { //case 7
        System.out.print("please enter\nsupplier id: ");
        String supplierId = getStringFromUser();
        System.out.println("day (1-7) in a week to get the order");
        int day = getIntFromUser();
        String order = pc.createPernamentOrder(day, supplierId);
        System.out.println(order);
        int orderId = -1;
        if (order.split(" ")[1].equals("details")) {
            orderId = Integer.parseInt(order.split(" ")[3]);
            while (true) {
                System.out.println("1. add new product");
                System.out.println("2. exit");
                int num = getIntFromUser();
                if (num == 2)
                    break;
                System.out.println("please enter the following details: ");
                System.out.println("product id: ");
                int productId = getIntFromUser();
                System.out.println("amount of products: ");
                int amount = getIntFromUser();
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
            int choose = getIntFromUser();
            switch (choose) {
                case 1:
                    System.out.print("please enter first name: ");
                    System.out.println(pc.updateFirstName(supplierID, getStringFromUser()));
                    break;
                case 2:
                    System.out.print("please enter last name: ");
                    System.out.println(pc.updateLastName(supplierID, getStringFromUser()));
                    break;
                case 3:
                    System.out.print("please enter phone number: ");
                    System.out.println(pc.updatePhone(supplierID, getStringFromUser()));
                    break;
                case 4:
                    System.out.print("please enter email: ");
                    System.out.println(pc.updateEmail(supplierID, getStringFromUser()));
                    break;
                case 5:
                    System.out.print("please enter company number: ");
                    System.out.println(pc.updateCompanyNumber(supplierID, getIntFromUser()));
                    break;
                case 6:
                    while (true) {
                        System.out.print("choose:\n1. permanent days\n2. non permanent days\n option number: ");
                        opt = getIntFromUser();
                        if (opt == 1) {
                            System.out.println(pc.updatePernamentDays(supplierID, true));
                            break;
                        } else if (opt == 2) {
                            System.out.println(pc.updatePernamentDays(supplierID, false));
                            break;
                        } else
                            System.out.println("invalid option please chose again");
                    }
                    break;
                case 7:
                    while (true) {
                        System.out.print("choose:\n1. self delivery\n2. not self delivery\n option number: ");
                        opt = getIntFromUser();
                        if (opt == 1) {
                            System.out.println(pc.updateSelfDelivery(supplierID, true));
                            break;
                        } else if (opt == 2) {
                            System.out.println(pc.updateSelfDelivery(supplierID, false));
                            break;
                        } else
                            System.out.println("invalid option please chose again");
                    }
                    break;
                case 8:
                    while (true) {
                        System.out.print("please choose a payment method\n1. Cash\n2. Bank transfer\n3. check\nchoose number: ");
                        opt = getIntFromUser();
                        if (opt == 1) {
                            System.out.println(pc.updatePayment(supplierID, "cash"));
                            break;
                        }
                        if (opt == 2) {
                            System.out.println(pc.updatePayment(supplierID, "bankTrasfer"));
                            break;
                        }
                        if (opt == 3) {
                            System.out.println(pc.updatePayment(supplierID, "check"));
                            break;
                        } else
                            System.out.println("invalid option please chose again");
                    }
                case 9:
                    while (true) {
                        System.out.println("Choose an option:\n1. add a contact person\n2. back to main menu");
                        opt = getIntFromUser();
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
                            System.out.println(pc.addContactMember(supplierID, firstName, lName, email, ID, phone));
                        }
                    }
                    break;
                case 10:
                    System.out.println("please enter a contact member id to remove:");
                    System.out.println(pc.deleteContactMember(supplierID, getStringFromUser()));
                    break;
                default:
                    flag = false;
            }
        }
    }

    //a helper function that approve that the order arrived in the system
    private void approveOrder() throws ReflectiveOperationException {
        System.out.println("please enter order ID");
        System.out.println(pc.approveOrder(getIntFromUser()));
    }

    //a helper function to get an order from the system
    private void getOrder() throws ReflectiveOperationException {
        System.out.print("please enter order ID: ");
        System.out.println(pc.getOrder(getIntFromUser()));
    }

    private void getQuantityList() throws ReflectiveOperationException {
        System.out.print("please enter supplier ID: ");
        String supplierID = getStringFromUser();
        System.out.println(pc.getQuantityList(supplierID));
    }


    //a helper function to add a product to an order
    private void addProductToOrder() throws ReflectiveOperationException {
        System.out.println("please enter order ID");
        int orderID = getIntFromUser();
        System.out.println("enter a product company number ID");
        int productID = getIntFromUser();
        System.out.println("enter amount of products: ");
        int amount = getIntFromUser();
        System.out.println(pc.addProductToOrder(orderID, productID, amount));
    }

    //a helper function to get a product
    private void getProduct() throws ReflectiveOperationException {
        System.out.println("please enter product ID");
        System.out.println(pc.getProduct(getIntFromUser()));
    }


    //a helper function to add a new product to the system
    private void createProduct() throws ReflectiveOperationException {
        System.out.println("please enter product name");
        String name = getStringFromUser();
        System.out.println("please enter manufacturer name");
        String manufacturer = getStringFromUser();
        System.out.println(pc.createProduct(name, manufacturer));
    }

    private void editAgrreement() throws ReflectiveOperationException {
        System.out.print("Please enter supplier ID:");
        String supplierId = getStringFromUser();
        boolean flag = true;
        while (flag) {
            System.out.println("please choose an option:");
            System.out.println("1. add product");
            System.out.println("2. remove product");
            System.out.println("3. edit company product id");
            System.out.println("4. edit product price");
            System.out.println("5. back to main menu");
            System.out.print("Option:");
            int opt = getIntFromUser();
            int productId = -1;
            switch (opt) {
                case 1 -> {//add new product
                    System.out.print("please enter system product id: ");
                    productId = getIntFromUser();
                    System.out.print("please enter your company product id: ");
                    int compNum = getIntFromUser();
                    System.out.print("please enter price: ");
                    int price = getIntFromUser();
                    System.out.print(pc.addItemToagreement(supplierId, productId, compNum, price));
                }
                case 2 -> {//delete product
                    System.out.print("product id: ");
                    productId = getIntFromUser();
                    System.out.println(pc.removeItemFromAgreement(supplierId, productId));
                }
                case 3 -> {
                    System.out.println("please enter the following details: ");
                    System.out.print("supplier id: ");
                    supplierId = getStringFromUser();
                    System.out.println("product id: ");
                    productId = scan.nextInt();
                    System.out.println("new company product id: ");
                    int newCompId = scan.nextInt();
                    System.out.println(pc.editAgreementItemCompanyProductID(supplierId, productId, newCompId));
                }

                case 4 -> {
                    System.out.println("please enter the following details: ");
                    System.out.print("supplier id: ");
                    supplierId = getStringFromUser();
                    System.out.print("product id: ");
                    productId = scan.nextInt();
                    System.out.print("new price: ");
                    int price = scan.nextInt();
                    System.out.println(pc.editAgreementItemPrice(supplierId, productId, price));
                }
                case 5 -> flag = false;
                default -> System.out.println("wrong input try again");
            }
        }
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

    //a helper function to get in from the user
    private int getIntFromUser() throws ReflectiveOperationException {
        int choose = -1;
        boolean scannerCon = true;
        while (scannerCon) {
            try {
                choose = scan.nextInt();
                if (choose == -1) throw new ReflectiveOperationException("by pressing -1 you chose to go back");
                if (choose < 0) {
                    System.out.println("you must choose an none-negative number ");
                } else {
                    scannerCon = false;
                }
            } catch (InputMismatchException ie) {
                System.out.println("wrong input - a number must be inserted please try again ");
                scan.nextLine();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("wrong input - a number must be inserted please try again ");
                scan.nextLine();
            }
        }
        return choose;
    }

    private void removeProductFromOrder() {
        System.out.println("please enter the following details: ");
        System.out.println("order id: ");
        int orderId = scan.nextInt();
        System.out.println("product id: ");
        int productId = scan.nextInt();
        System.out.println(pc.removeProductFromOrder(orderId, productId));
    }
}
