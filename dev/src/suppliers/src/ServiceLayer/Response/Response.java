package ServiceLayer.Response;


public class Response {
    private String errorMessage;

    public Response(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
    public Response() {
        this.errorMessage=null;
    }

    public  boolean errorOccured()
    {return errorMessage != null; }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String toString(){
        if (errorOccured())
            return "\nError: "+getErrorMessage()+"\n";
        return "";
    }
}