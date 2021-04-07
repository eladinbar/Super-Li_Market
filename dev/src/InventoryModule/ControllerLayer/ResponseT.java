package InventoryModule.ControllerLayer;

public class ResponseT<T> extends Response {
    private final T data;

    public ResponseT(boolean errorOccurred, String message, T data) {
        super(errorOccurred,message);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
