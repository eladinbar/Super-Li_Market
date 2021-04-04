package BusinessLayer.supplierPackage;

import java.util.HashMap;
import java.util.Map;

public class agreement {
    private Map<int, int> products;
    private Map<int, int> prices;
    private quantityList ql;

    public agreement() {
        this.ql=null;
        products=new HashMap<int,int>();
        prices=new HashMap<int,int>();
    }
}
