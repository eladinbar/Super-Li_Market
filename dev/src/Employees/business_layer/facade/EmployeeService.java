package Employees.business_layer.facade;

import Employees.business_layer.Employee.EmployeeController;

public class EmployeeService {
    EmployeeController employeeController;

    public EmployeeService(EmployeeController employeeController)
    {
        this.employeeController = employeeController;
    }
}
