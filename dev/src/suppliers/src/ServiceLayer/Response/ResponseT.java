package ServiceLayer.Response;

public class ResponseT <T> extends Response{
    public final T value;

    //an exception message was sent
    public ResponseT(String errorMessage)
    {
        super(errorMessage);
        value = null;
    }

    //no exception
    public ResponseT(T value)
    {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
        if(errorOccured())
            return "Error: "+getErrorMessage();
        return value.toString();
    }
}