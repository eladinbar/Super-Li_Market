package ServiceLayer.objects;
import java.util.regex.Pattern;

public class supplier {
    private supplierCard sc;
    private agreement ag;

    public supplier(supplierCard sc, agreement ag) throws Exception {
        this.sc = sc;
        this.ag = ag;
    }
}
