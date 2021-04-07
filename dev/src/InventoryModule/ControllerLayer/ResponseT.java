package InventoryModule.ControllerLayer;

public class ResponseT<T> extends Response {
    private final T element;

    public ResponseT(boolean errorOccurred, String message, T element) {
        super(errorOccurred,message);
        this.element = element;
    }

    public T getElement() {
        return element;
    }
}
