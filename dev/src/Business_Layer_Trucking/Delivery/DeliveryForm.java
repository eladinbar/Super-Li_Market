package Business_Layer_Trucking.Delivery;

import java.util.Dictionary;
import java.util.HashMap;

public class DeliveryForm {
    private int ID;
    private int origin;
    private int destination;
    private HashMap<Integer, Integer> items;
    private int leavingWeight;
    private int trID;
    private boolean completed;

    public DeliveryForm(int ID, int origin, int destination, HashMap<Integer,Integer> items,
                        int leavingWeight, int trID){
        this.ID=ID;
        this.origin=origin;
        this.destination=destination;
        this.items=items;
        this.leavingWeight=leavingWeight;
        this.trID=trID;
        this.completed=false;

        }

    public DeliveryForm(DeliveryForm df) {

        this.ID = df.getID();
        this.completed = df.isCompleted();
        this.origin = df.getOrigin();
        this.destination = df.getDestination();
        this.items = df.getItems();
        this.leavingWeight = df.getLeavingWeight();
        this.trID = df.getTrID();


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

    public void setCompleted() {
        this.completed = true;
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

