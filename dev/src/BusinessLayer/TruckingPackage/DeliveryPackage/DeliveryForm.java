package BusinessLayer.TruckingPackage.DeliveryPackage;

import DataAccessLayer.DalControllers.TruckingControllers.DalDeliveryFormController;
import DataAccessLayer.DalControllers.TruckingControllers.DalItemsOnDFController;
import DataAccessLayer.DalObjects.TruckingObjects.DalDeliveryForm;
import DataAccessLayer.DalObjects.TruckingObjects.DalItemsOnDF;
import InfrastructurePackage.Pair;

import java.sql.SQLException;
import java.util.HashMap;

public class DeliveryForm {
    private int ID;
    private int destination;
    private HashMap<Integer, Integer> items;
    private int leavingWeight;
    private int trID;
    private boolean completed;

    public DeliveryForm(int ID,  int destination, HashMap<Integer, Integer> items,
                        int leavingWeight, int trID)  {
        this.ID = ID;
        this.destination = destination;
        this.items = items;
        this.leavingWeight = leavingWeight;
        this.trID = trID;
        this.completed = false;
        try {
            DalDeliveryFormController.getInstance().insert(new DalDeliveryForm(ID,destination,completed,leavingWeight,trID));
        }
        catch (SQLException e){}

    }

    public DeliveryForm(DeliveryForm df) {

        this.ID = df.getID();
        this.completed = df.isCompleted();
        this.destination = df.getDestination();
        this.items = df.getItems();
        this.leavingWeight = df.getLeavingWeight();
        this.trID = df.getTrID();


    }

    public DeliveryForm(DalDeliveryForm deliveryForm) {
        this.ID = deliveryForm.getID();
        this.completed = deliveryForm.isCompleted();
        this.destination = deliveryForm.getDestination();
        this.leavingWeight = deliveryForm.getLeavingWeight();
        this.trID = deliveryForm.getTRID();
        this.items = new HashMap<>();
    }

    public void addItem(int itemID, int amount) throws SQLException {
        if (items == null)
            items = new HashMap<>();
        if (items.containsKey(itemID)){
            int prevAmount  = items.get(itemID);
            amount  = amount  + prevAmount;
            items.put(itemID, amount);
            DalItemsOnDFController.getInstance().update(new DalItemsOnDF(this.ID,itemID,amount));
        }
        else {
            items.put(itemID, amount);
            DalItemsOnDFController.getInstance().insert(new DalItemsOnDF(this.ID, itemID, amount));
        }

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


    public int getTrID() {
        return trID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() throws SQLException {
        this.completed = true;
        DalDeliveryFormController.getInstance().update(new DalDeliveryForm(this.ID,this.destination,this.completed,this.leavingWeight,this.trID));
    }

    public void setDestination(int destination) throws SQLException {
        this.destination = destination;
        DalDeliveryFormController.getInstance().update(new DalDeliveryForm(this.ID,this.destination,this.completed,this.leavingWeight,this.trID));

    }

    public void setID(int ID) throws SQLException {
        this.ID = ID;

    }

    public void setItems(HashMap<Integer, Integer> items) throws SQLException {
        this.items = items;
    }

    public void setLeavingWeight(int leavingWeight) throws SQLException {
        this.leavingWeight = leavingWeight;
        DalDeliveryFormController.getInstance().update(new DalDeliveryForm(this.ID,this.destination,this.completed,this.leavingWeight,this.trID));
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

