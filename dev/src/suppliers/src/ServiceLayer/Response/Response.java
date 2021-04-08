package ServiceLayer.Response;

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

    public void setErrorOccurred(boolean errorOccurred) {
        this.errorOccurred = errorOccurred;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}