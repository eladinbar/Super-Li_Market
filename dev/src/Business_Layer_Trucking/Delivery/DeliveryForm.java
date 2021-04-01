package Business_Layer_Trucking.Delivery;

import java.util.Dictionary;
import java.util.HashMap;

public class DeliveryForm {
    private int ID;
    private int origin;
    private int destination;
    private HashMap<Item, Integer> items;
    private int leavingWeight;
    private int trID;

    public DeliveryForm(int ID, int origin, int destination, HashMap<Item,Integer> items,
                        int leavingWeight, int trID){
        // TODO need to be completed
        throw new UnsupportedOperationException();
    }

    public HashMap<Item, Integer> getItems() {
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


    // TODO need to check when can be setted and fix!
    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setItems(HashMap<Item, Integer> items) {
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

