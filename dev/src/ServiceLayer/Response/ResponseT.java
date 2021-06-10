package ServiceLayer.Response;

public class ResponseT <T> extends Response{
    public final T value;

    //an exception message was sent
    public ResponseT(String errorMessage)
    {
        super(errorMessage);
        value = null;
    }
    public ResponseT(boolean errorOccurred, String message, T data) {
        super(errorOccurred,message);
        this.value = data;
    }

    //no exception
    public ResponseT(T value)
    {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
        if(errorOccurred())
            return "\nError: "+getErrorMessage()+"\n";
        return "\nSuccess: \n"+value.toString();
    }
    public T getValue() {
        return value;
    }
}