package SerciveLayer;

public class InResponseT<T> extends InResponse {
    private final T data;

    public InResponseT(boolean errorOccurred, String message, T data) {
        super(errorOccurred,message);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
