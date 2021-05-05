package SerciveLayer;

public class InResponse {
    protected boolean errorOccurred;
    protected String message;


    public InResponse(boolean errorOccurred, String message) {
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
