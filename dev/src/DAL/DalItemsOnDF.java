package DAL;

public class DalItemsOnDF implements DalObject{
    int itemID;
    int amount;

    public  DalItemsOnDF (int itemID,int amount)
    {
        this.itemID=itemID;
        this.amount=amount;
    }

    public int getItemID() {
        return itemID;
    }

    public int getAmount() {
        return amount;
    }
}
