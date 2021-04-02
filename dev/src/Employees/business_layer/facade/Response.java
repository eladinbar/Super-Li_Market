package Employees.business_layer.facade;

public class Response {
    private boolean excepted;
    private String exceptionMessage;

    public Response(String exceptionMessage)
    {
        excepted = true;
        this.exceptionMessage = exceptionMessage;
    }

    public Response()
    {
        this.excepted = false;
        exceptionMessage = "";
    }
}
