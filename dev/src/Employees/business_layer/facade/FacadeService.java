package Employees.business_layer.facade;

public class FacadeService {
    private EmployeeService employeeService;
    private ShiftService shiftService;

    public  FacadeService()
    {
        employeeService = new EmployeeService ();
        shiftService = new ShiftService ();
    }
}
