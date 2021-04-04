package ServiceLayer;

import BusinessLayer.supplierPackage.supplierController;
import ServiceLayer.Response.Response;
import ServiceLayer.objects.payment;
import ServiceLayer.objects.supplier;
import BusinessLayer.*;

public class supplierService {
    private supplierController sp;

    public supplierService() {
        sp=new supplierController();
    }

    public Response<supplier> addSupplier(String firstName, String lastName, String email, String id, int phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, payment payment) {
        Response<supplier> toReturn=null;
        try {
            sp.addSupplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> removeSupplier(String id) {
        return null;
    }
}
