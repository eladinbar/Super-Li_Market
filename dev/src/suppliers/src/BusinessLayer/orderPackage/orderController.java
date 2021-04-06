package BusinessLayer.orderPackage;
import java.util.*;

public class orderController {
    private final int days = 7;
    private Map<Integer,order> orders;
    private Map<Integer , List<order>> pernamentOrders;
    private Map<Integer,product> products;

    public orderController() {
        pernamentOrders = new HashMap<>();
        for (int i= 1 ;i<=days ; i++){
          pernamentOrders.put(i, new ArrayList<>());
        }
        orders = new HashMap<>();
        products = new HashMap<>();
    }

    public void removeSupplier(String id){
        for (int i=1 ; i<=days ; i++){
            for (order o : pernamentOrders.get(i)) {
                if (o.getSupplier().getSc().getId().equals(id))
                    pernamentOrders.get(i).remove(o);
            }
        }
    }

    public void createOrder(Date date, String supplierID) {

    }
}
