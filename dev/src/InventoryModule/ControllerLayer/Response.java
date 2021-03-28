package InventoryModule.ControllerLayer;

public class Response<T> {
    private boolean errorOccurred;
    private String message;
    private T data;
}
