package InventoryModule.PresentationLayer;


import InfrastructurePackage.SystemLogger;
import org.apache.log4j.Logger;

public class Main {
    static Logger log = SystemLogger.getLogger();
    public static void main(String[] args) {
        log.error("this is a massage");
        System.out.println("Hello World!");
    }
}
