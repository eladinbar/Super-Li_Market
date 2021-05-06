package SerciveLayer.Response;


public class Response {
    private String errorMessage;
    protected boolean errorOccurred;
    protected String message;

    public Response(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }


    public Response(boolean errorOccurred, String message) {
        this.errorOccurred = errorOccurred;
        this.message = message;
    }
    public Response() {
        this.errorMessage=null;
    }

    public  boolean errorOccured()
    {return errorMessage != null; }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isErrorOccurred() {
        return errorOccurred;
    }

    public String getMessage() {
        return message;
    }

    public String toString(){
        if (errorOccured())
            return "\nError: "+getErrorMessage()+"\n";
        return "\nSuccess\n";
    }
}