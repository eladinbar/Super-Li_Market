package BusinessLayer.TruckingPackage.Facade;

public class Response {
    private boolean excepted;
    private String exceptionMessage;

    public Response(boolean excepted,String exceptionMessage)
    {
        this.excepted=excepted;
        this.exceptionMessage=exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public boolean isExcepted() {
        return excepted;
    }
}
