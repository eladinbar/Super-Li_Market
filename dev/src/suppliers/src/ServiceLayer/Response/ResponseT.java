package ServiceLayer.Response;

public class ResponseT<T> extends Response {
    private T date;

    public ResponseT(boolean errorOccurred, String message,T date) {
        super(errorOccurred,message);
        this.date = date;
    }
}