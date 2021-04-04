package BusinessLayer.supplierPackage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class agreement {
    private Map<int, int> products;
    private Map<int, int> prices;
    private quantityList ql;

    public agreement() {
        this.ql=null;
        products=new HashMap<int,int>();
        prices=new HashMap<int,int>();
        Scanner scanner=new Scanner(System.in);
        //todo after fix controller with products
        // get product from controller product
        // set on controller new map
        // create the agreement with the map
        while (true) {
            System.out.println("Please enter a product id");
            System.out.println("please enter product company id");
            System.out.println("please enter product price");
            System.out.println("to continue press 1 else 0");
            if(scanner.nextInt()==0)
                break;
        }
    }
}
