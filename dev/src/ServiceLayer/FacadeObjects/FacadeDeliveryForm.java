package ServiceLayer.FacadeObjects;

import BusinessLayer.TruckingPackage.DeliveryPackage.*;

import java.util.HashMap;
import java.util.Map;

public class FacadeDeliveryForm implements FacadeObject{
    private int ID;
    private int supplier;
    private HashMap<Integer, Integer> items;
    private int leavingWeight;
    private int trID;
    private boolean completed;


    public FacadeDeliveryForm(int ID, int origin, int supplier, HashMap<Integer,Integer> items,
                              int leavingWeight, int trID){
        this.ID = ID;
        this.supplier = supplier;
        this.items = items;
        this.leavingWeight = leavingWeight;
        this.trID =trID;
        completed = false;
    }

    public FacadeDeliveryForm(DeliveryForm df){
        this.ID =  df.getID();
        supplier = df.getDestination();
        items = new HashMap<Integer,Integer>();
        for (Map.Entry<Integer,Integer> entry: df.getItems().entrySet() ) {
            this.items.put(entry.getKey() , entry.getValue());
        }
        leavingWeight = df.getLeavingWeight();
        trID = df.getTrID();
        completed = df.isCompleted();
    }

    public HashMap<Integer, Integer> getItems() {
        return items;
    }

    public int getSupplier() {
        return supplier;
    }

    public int getID() {
        return ID;
    }

    public int getLeavingWeight() {
        return leavingWeight;
    }


    public int getTrID() {
        return trID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setSupplier(int supplier) {
        this.supplier = supplier;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setItems(HashMap<Integer, Integer> items) {
        this.items = items;
    }

    public void setLeavingWeight(int leavingWeight) {
        this.leavingWeight = leavingWeight;
    }


    public void setTrID(int trID) {
        this.trID = trID;
    }
}
