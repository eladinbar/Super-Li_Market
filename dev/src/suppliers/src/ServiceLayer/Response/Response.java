package ServiceLayer.Response;

public class Response<T> {
    public String ErrorMessage;

    public Response() {
    }

    public Response(String msg) {
        this.ErrorMessage = msg;
    }

    public boolean ErrorOccured() {
        return ErrorMessage != null;
    }
}