package BusinessLayer;

import java.time.LocalDate;

public class TruckingNotifications extends Notification{
    public TruckingNotifications (String content){
        super(content);
        this.content = LocalDate.now() + ":\n" + content;

    }
}
