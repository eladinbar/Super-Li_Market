package DAL;

public class DalItemsOnDF implements DalObject{
    int itemID;
    int amount;

    public  DalItemsOnDF (int itemID,int amount)
    {
        this.amount=amount;
        this.itemID=itemID;
    }
}
