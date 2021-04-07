package InventoryModule.ControllerLayer;

public class Response {
    protected boolean errorOccurred;
    protected String message;


    public Response(boolean errorOccurred, String message) {
        this.errorOccurred = errorOccurred;
        this.message = message;
    }

    public boolean isErrorOccurred() {
        return errorOccurred;
    }

    public String getMessage() {
        return message;
    }
}