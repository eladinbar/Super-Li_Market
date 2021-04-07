package InventoryModule.ControllerLayer.SimpleObjects;

import java.util.Date;

public class DefectEntry implements SimpleEntity {
    Date entryDate;
    int itemId;
    String itemName;
    int quantity;
    String location;
}
