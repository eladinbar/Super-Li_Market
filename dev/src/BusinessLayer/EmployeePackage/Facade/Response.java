package BusinessLayer.EmployeePackage.Facade;

public class Response {
    private String errorMessage = null;

    public Response(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public Response() { }

    public  boolean errorOccured()
    {return errorMessage != null; }

    public String getErrorMessage() {
        return errorMessage;
    }
}
