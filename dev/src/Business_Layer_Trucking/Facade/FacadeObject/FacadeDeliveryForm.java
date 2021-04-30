package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Delivery.DeliveryForm;
import Business_Layer_Trucking.Delivery.Item;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class FacadeDeliveryForm implements FacadeObject{
    private int ID;
    private int origin;
    private int destination;
    private HashMap<Integer, Integer> items;
    private int leavingWeight;
    private int trID;
    private boolean completed;


    public FacadeDeliveryForm(int ID, int origin, int destination, HashMap<Integer,Integer> items,
                        int leavingWeight, int trID){
        this.ID = ID;
        this.origin = origin;
        this.destination = destination;
        this.items = items;
        this.leavingWeight = leavingWeight;
        this.trID =trID;
        completed = false;
    }

    public FacadeDeliveryForm(DeliveryForm df){
        this.ID =  df.getID();
        this.origin = df.getOrigin();
        destination = df.getDestination();
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

    public int getDestination() {
        return destination;
    }

    public int getID() {
        return ID;
    }

    public int getLeavingWeight() {
        return leavingWeight;
    }

    public int getOrigin() {
        return origin;
    }

    public int getTrID() {
        return trID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setDestination(int destination) {
        this.destination = destination;
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

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public void setTrID(int trID) {
        this.trID = trID;
    }
}
