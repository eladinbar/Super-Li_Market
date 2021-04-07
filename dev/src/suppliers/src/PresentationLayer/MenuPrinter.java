package PresentationLayer;
import java.util.*;
import java.util.Scanner;

public class MenuPrinter {
    private PresentationController pc;
    private Scanner scan;

    public MenuPrinter() {
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
                    addSupplierFunc();
                    break;
            }
        }
    }
    private void addSupplierFunc() throws ReflectiveOperationException {
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
    }
    private void updateSupplierDetailFunc() throws ReflectiveOperationException {
        System.out.println("please enter supplier id");
        String supplierID=getStringFromUser();
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
        int choose= scan.nextInt();
        switch (choose){
            case 1:

        }
    }

    public int PrintMenu(){
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
