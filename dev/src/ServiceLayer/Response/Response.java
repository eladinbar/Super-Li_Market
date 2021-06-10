package ServiceLayer.Response;


public class Response {
    private String errorMessage;
    protected boolean errorOccurred;
    protected String message;


    public Response(String errorMessage)
    {
        this.errorMessage = errorMessage;
        this.errorOccurred = true;
        this.message = errorMessage;
    }


    public Response(boolean errorOccurred, String message) {
        this.errorOccurred = errorOccurred;
        this.message = message;
        if(errorOccurred)
            errorMessage = message;
    }
    public Response() {
        this.errorMessage=null;
        this.errorOccurred = false;
    }

    public  boolean errorOccurred()
    {return errorMessage != null; }

    public String getErrorMessage() {
        return errorMessage;
    }


    public String getMessage() {
        return message;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toString(){
        if (errorOccurred())
            return "\nError: "+getErrorMessage()+"\n";
        return "\nSuccess\n";
    }
}