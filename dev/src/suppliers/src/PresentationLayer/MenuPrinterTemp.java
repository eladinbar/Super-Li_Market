package PresentationLayer;
import java.util.*;
import java.util.Scanner;

public class MenuPrinterTemp {
    private PresentationController pc;
    private Scanner scan;

    public MenuPrinterTemp() {
        scan=new Scanner(System.in);
        this.pc = new PresentationController();
    }

    public void startWork() throws ReflectiveOperationException {
        System.out.println("welcome to Super Lee");
        int choose=1;
        while (choose>0) {
            choose = PrintMenu();
            switch (choose) {
                case 1:
                    System.out.print("please enter following details: ");
                    System.out.print("\nfirst name: ");
                    String firstName=getStringFromUser();
                    System.out.print("last name: ");
                    String lName=getStringFromUser();
                    System.out.print("email: ");
                    String email=getStringFromUser();
                    System.out.print("ID: ");
                    String ID=getStringFromUser();
                    System.out.print("phone: ");
                    String phone=getStringFromUser();
                    System.out.print("companyNumber: ");
                    int companyNumber=scan.nextInt();
                    System.out.print("do you a permanent order days? y/n: ");
                    String permanent=getStringFromUser();
                    System.out.print("do you deliver the orders by yourself? y/n: ");
                    String selfDelivery=getStringFromUser();
                    System.out.print("how whould you like to pay?\n1. Cash\n2. Bank transfer\n3. check\nchoose number: ");
                    int payment=scan.nextInt();
                    break;
            case 4:
                addQuantityList();
                break;
            case 5:
                editQuantityList();
                break;
            }
        }
    }

    private void getSupplier() throws ReflectiveOperationException {
        System.out.printf("please enter supplier id: ");
        String supplierId = getStringFromUser();
    }

    private void addQuantityList() throws ReflectiveOperationException { //case 4
        System.out.print("please enter supplier id: ");
        String supplierId = getStringFromUser();
        pc.addQuantityList(supplierId);
        while (true){
            System.out.println("1. add new product");
            System.out.println("2. exit");
            int num = scan.nextInt();
            if (num==2)
                break;
            System.out.println("please enter the following details: ");
            System.out.println("product id: ");
            int productId = scan.nextInt();
            System.out.println("amount of products to get a discount: ");
            int amount = scan.nextInt();
            System.out.println("discount amount: ");
            int discount = scan.nextInt();
            pc.addQuantityListItem(supplierId, productId, amount, discount);
        }
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
        switch(num){
            case 1 :{ //edit product amount
                System.out.println("product id: ");
                int prodId = scan.nextInt();
                System.out.println("new amount of products to get a discount: ");
                int amount = scan.nextInt();
                pc.editQuantityListAmount(supplierId, prodId, amount);
                break;
            }
            case 2: {  //edit product discount
                System.out.println("product id: ");
                int prodId = scan.nextInt();
                System.out.println("new discount: ");
                int discount = scan.nextInt();
                pc.editQuantityListDiscount(supplierId, prodId, discount);
                break;
            }
            case 3:{ //add new product
                System.out.println("product id: ");
                int productId = scan.nextInt();
                System.out.println("amount of products to get a discount: ");
                int amount = scan.nextInt();
                System.out.println("discount amount: ");
                int discount = scan.nextInt();
                pc.addQuantityListItem(supplierId, productId, amount, discount);
                break;
            }
            case 4:{ //delete product
                System.out.println("product id: ");
                int productId = scan.nextInt();
                pc.deleteQuantityListItem(supplierId, productId);
            }
            case 5:{ //delete quantity list
                pc.deleteQuantityList(supplierId);
            }
        }
    }

    private void createNewOrder() throws ReflectiveOperationException { //case 6
        System.out.print("please enter\nsupplier id: ");
        String supplierId = getStringFromUser();
        System.out.println("date: ");
        int date = scan.nextInt();
        System.out.println("please enter the following details: ");
        while (true){
            System.out.println("1. add new product");
            System.out.println("2. exit");
            int num = scan.nextInt();
            if (num==2)
                break;
            System.out.println("please enter the following details: ");
            System.out.println("product id: ");
            int productId = scan.nextInt();
            System.out.println("amount of products: ");
            int amount = scan.nextInt();
        }
    }

    private void setPermanentOrder() throws ReflectiveOperationException { //case 7
        System.out.print("please enter\nsupplier id: ");
        String supplierId = getStringFromUser();
        System.out.println("day (1-7) in a week to get the order");
        int day = scan.nextInt();
        pc.createPernamentOrder(day, supplierId);
        while (true){
            System.out.println("1. add new product");
            System.out.println("2. exit");
            int num = scan.nextInt();
            if (num==2)
                break;
            System.out.println("please enter the following details: ");
            System.out.println("product id: ");
            int productId = scan.nextInt();
            System.out.println("amount of products: ");
            int amount = scan.nextInt();

        }
    }

    private void approveOrder(){ //case 8
        System.out.println("please enter order id: ");
        int orderId = scan.nextInt();
        pc.approveOrder(orderId);
    }

    public int PrintMenu(){
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
        System.out.println("Please choose an option:");
        return scan.nextInt();
    }

    private String getStringFromUser() throws ReflectiveOperationException {
        boolean con = true;
        String output = "";
        while (con) {
            try {
                output =scan.next();
                if (output.equals("-1")) throw new ReflectiveOperationException("by pressing -1 you chose to go back");
                con= false;
            }catch (NoSuchElementException|IllegalStateException e){
                System.out.println("wrong input please try again");
                scan.nextLine();
            }
        }
        return output;

    }

}
