package BusinessLayer;

public abstract class Notification {
    String content;

    public Notification(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
