package BusinessLayer.TruckingPackage.Facade;

import BusinessLayer.TruckingPackage.Facade.FacadeObject.FacadeObject;

public class ResponseT<T extends FacadeObject> extends Response{
    private T data;
    public ResponseT(boolean excepted, String exceptionMessage,T data) {
        super(excepted, exceptionMessage);
        this.data=data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
