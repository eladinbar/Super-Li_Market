package Employees.business_layer.facade;

public class ResponseT <T> extends Response{
    public final T value;

    //an exception message was sent
    public ResponseT(String exeptionMessage)
    {
        super(exeptionMessage);
        value = null;
    }

    //no exception
    public ResponseT(T value)
    {
        super();
        this.value = value;
    }
}
