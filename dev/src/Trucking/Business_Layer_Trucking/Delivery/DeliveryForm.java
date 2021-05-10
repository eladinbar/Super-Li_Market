package Trucking.Business_Layer_Trucking.Delivery;

import DAL.*;
import DAL.DalControllers_Trucking.DalDeliveryFormController;
import DAL.DalObjects_Trucking.DalDeliveryForm;

import java.sql.SQLException;
import java.util.HashMap;

public class DeliveryForm {
    private int ID;
    private int origin;
    private int destination;
    private HashMap<Integer, Integer> items;
    private int leavingWeight;
    private int trID;
    private boolean completed;

    public DeliveryForm(int ID, int origin, int destination, HashMap<Integer, Integer> items,
                        int leavingWeight, int trID) throws SQLException {
        this.ID = ID;
        this.origin = origin;
        this.destination = destination;
        this.items = items;
        this.leavingWeight = leavingWeight;
        this.trID = trID;
        this.completed = false;

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

    public DeliveryForm(DalDeliveryForm deliveryForm) {
        this.ID = deliveryForm.getID();
        this.completed = deliveryForm.isCompleted();
        this.origin = deliveryForm.getOrigin();
        this.destination = deliveryForm.getDestination();
        this.leavingWeight = deliveryForm.getLeavingWeight();
        this.trID = deliveryForm.getTRID();
        this.items = new HashMap<>();
    }

    public void addItem(int itemID, int amount) {
        if (items == null)
            items = new HashMap<>();
        items.put(itemID, amount);
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

    public void setCompleted() throws SQLException {
        this.completed = true;
    }

    public void setDestination(int destination) throws SQLException {
        this.destination = destination;

    }

    public void setID(int ID) throws SQLException {
        this.ID = ID;

    }

    public void setItems(HashMap<Integer, Integer> items) throws SQLException {
        this.items = items;
    }

    public void setLeavingWeight(int leavingWeight) throws SQLException {
        this.leavingWeight = leavingWeight;
    }

    public void setOrigin(int origin) throws SQLException {
        this.origin = origin;
    }

    public void setTrID(int trID) throws SQLException {
        this.trID = trID;
    }

    public void setUncompleted() throws SQLException {
        completed = false;
/*
        DalDeliveryFormController.getInstance().update(new DalDeliveryForm(ID, origin, destination, completed,leavingWeight,trID));
*/
    }

    public void setIDWithoutSave(int lastDeliveryForms) {
        this.ID = lastDeliveryForms;
    }
}

