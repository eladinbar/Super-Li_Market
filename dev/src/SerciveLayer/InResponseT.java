package SerciveLayer;

public class InResponseT<T> extends InResponse {
    private final T value;

    public InResponseT(boolean errorOccurred, String message, T data) {
        super(errorOccurred,message);
        this.value = data;
    }

    public T getValue() {
        return value;
    }
}
