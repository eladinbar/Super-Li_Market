package BusinessLayer;

import DataAccessLayer.DalObjects.TruckingObjects.DalTruckingNotification;

import java.time.LocalDate;

public class TruckingNotifications extends Notification{

    public TruckingNotifications (String content){
        super(content);
        this.content = LocalDate.now() + ":\n" + content;
    }

    public TruckingNotifications(DalTruckingNotification dalTruckingNotification)
    {
        super(dalTruckingNotification.getContent());
    }
}
