package Employees.business_layer.Employee;

import Employees.EmployeeException;
import Employees.business_layer.facade.facadeObject.FacadeBankAccountInfo;
import Employees.business_layer.facade.facadeObject.FacadeEmployee;
import Employees.business_layer.facade.facadeObject.FacadeTermsOfEmployment;
import Trucking.Business_Layer_Trucking.Resources.Driver;
import Trucking.Business_Layer_Trucking.Resources.ResourcesController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EmployeeController {
    private static EmployeeController instance = null;

    private HashMap<String, Employee> employees;
    private Employee loggedIn;

    private EmployeeController(){
        this.loggedIn = null;
        employees = new HashMap<>();
    }

    public static EmployeeController getInstance() {
        if (instance == null)
            instance = new EmployeeController ();
        return instance;
    }

    public Employee getLoggedIn() throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is logged in");
        }
        return loggedIn;
    }

    public HashMap<String, Employee> getEmployees() throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is logged in");
        }

        if(!loggedIn.getIsManager()) {
            throw new EmployeeException("Only an administrator can perform this operation");
        }

        if(employees.isEmpty()){
            throw new EmployeeException("There is no record of the super's employees");
        }

        return employees;
    }
    public Employee login(String ID, String role) throws EmployeeException {
        if(loggedIn!= null){
            throw new EmployeeException("Two users cannot be logged in at the same time");
        }
        if(!employees.containsKey(ID)) {
            throw new EmployeeException("There is no record of this employee in the system");
        }
            Employee toLogin = employees.get(ID);
            if(!toLogin.isEmployed()){
                throw new EmployeeException("The employee is not employed ");
            }
            if (toLogin.getRole() != Role.valueOf(role)) {
                throw new EmployeeException("Id does not match to the role");
            }
        loggedIn = toLogin;
        return employees.get(ID);
    }

    public Employee logout() throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is loggedIn");
        }
        String id = loggedIn.getID();
        loggedIn= null;
        return employees.get(id);
    }

    public Employee getEmployee(String Id) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is logged in");
        }

        if(!loggedIn.getIsManager()) {
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)) {
            throw new EmployeeException("Employee not found");
        }
        return employees.get(Id);
    }

    public void giveConstraint(LocalDate date, int shift, String reason) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is logged in");
        }

        if(loggedIn.getIsManager()){
            throw new EmployeeException("The method 'giveConstraint' was called from a user in a managerial position");
        }

        if(!loggedIn.isEmployed()) {
            throw new EmployeeException("The employee is not employed ");
        }
        try {
            loggedIn.giveConstraint(date, shift, reason);
            if(loggedIn.getRole ().equals ( Role.driverC) || loggedIn.getRole ().equals ( Role.driverC1))
            {
                try {
                    ResourcesController.getInstance ( ).addDriverConstraint ( loggedIn.getID ( ), date, shift );
                } catch (IllegalArgumentException e){
                    deleteConstraint ( date,shift );
                }
            }
        }catch (EmployeeException e){
            if(loggedIn.getRole ().equals ( Role.driverC) || loggedIn.getRole ().equals ( Role.driverC1))
                throw e;
        }

    }


    public void deleteConstraint (LocalDate date, int shift) throws EmployeeException {
        if (loggedIn == null) {
            throw new EmployeeException ( "No user is logged in" );
        }

        if (loggedIn.getIsManager ( )) {
            throw new EmployeeException ( "The method 'giveConstraint' was called from a user in a managerial position" );
        }
        if (!loggedIn.isEmployed ( )) {
            throw new EmployeeException ( "The employee is not employed " );
        }
        String reason = loggedIn.getConstraints ( ).get ( date ).getReason ( );
        loggedIn.deleteConstraint ( date, shift );
        if (loggedIn.getRole ( ).equals ( Role.driverC ) || loggedIn.getRole ( ).equals ( Role.driverC1 )) {
            try {
                ResourcesController.getInstance ( ).deleteDriverConstraint ( loggedIn.getID ( ), date, shift );
            } catch (IllegalArgumentException e) {
                giveConstraint ( date, shift, reason );
            }
        }
    }

    public HashMap<LocalDate, Constraint> getConstraints() throws EmployeeException {
        if(loggedIn.getIsManager()){
            throw new EmployeeException("A manager has no list of constraints");
        }
        return loggedIn.getConstraints();
    }

    public HashMap<LocalDate, Constraint> getConstraints(String Id) throws EmployeeException {
        if(!loggedIn.getIsManager()){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        return employees.get(Id).getConstraints();
    }

    public Employee addEmployee(FacadeEmployee e) throws EmployeeException {

        if(loggedIn==null){
            throw new EmployeeException("No user is logged in");
        }

        if(!loggedIn.getIsManager()) {
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!validId(e.getID())){
            throw new EmployeeException("An invalid ID was entered ");
        }
        if(employees.containsKey(e.getID())){
            throw new EmployeeException("Employee already added to the system");
        }
       TermsOfEmployment terms = creatTermsOfEmployment ( e.getFacadeTermsOfEmployment () );
       BankAccountInfo bank = createAccount ( e.getFacadeBankAccountInfo () );
       if(!validId(e.getID())){throw new EmployeeException("An invalid ID was entered ");}
       Employee newEmployee = new Employee(e.getRole(), e.getID(),terms, e.getTransactionDate(), bank);
       employees.put(e.getID(), newEmployee);
       return newEmployee;
    }

    public Employee addDriver(FacadeEmployee e, String name) throws EmployeeException {
        Employee driver = addEmployee ( e );
        ResourcesController.getInstance ().addDriver ( e.getID (), name, Driver.License.valueOf ( (e.getRole ().equals ( "driverC" )) ? "120000" : "200000" ) );
        return driver;
    }

    private void addEmplForExistingData(Employee e) throws EmployeeException {
        if(!validId(e.getID())){
            throw new EmployeeException("An invalid ID was entered ");
        }
        if(employees.containsKey(e.getID())){
            throw new EmployeeException("Employee already added to the system");
        }
        employees.put(e.getID(), e);
    }

    public Employee addManager(FacadeEmployee e) throws EmployeeException {
        if(!e.isManager ()){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!validId(e.getID())){
            throw new EmployeeException("An invalid ID was entered ");
        }
        if(employees.containsKey(e.getID())){
            throw new EmployeeException("Manager already added to the system");
        }
        TermsOfEmployment terms = creatTermsOfEmployment ( e.getFacadeTermsOfEmployment () );
        BankAccountInfo bank =createAccount ( e.getFacadeBankAccountInfo () );
        if(!validId(e.getID())){ throw new EmployeeException("An invalid ID was entered "); }
        Employee newEmployee = new Employee(e.getRole(), e.getID(),terms, e.getTransactionDate(), bank);
        employees.put(e.getID(), newEmployee);
        return newEmployee;
    }

    public Employee removeEmployee(String Id) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is logged in");
        }

        if(!loggedIn.getIsManager()){
            throw new EmployeeException("Only an administrator can perform this operation");
        }

        if(!employees.containsKey(Id)){
                throw new EmployeeException("Employee is not in the system");
        }
        if(Id.equals(loggedIn.getID())){ logout();}
        employees.get(Id).setEmployed(false);
        return employees.get(Id);
    }


    public void updateBankAccount(String Id, int accountNum, int bankBranch, String bank) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is logged in");
        }

        if(!loggedIn.getIsManager()){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
            throw new EmployeeException("Employee not found");
        }
        BankAccountInfo toUpdate = employees.get(Id).getBank();
        toUpdate.setAccountNumber(accountNum);
        toUpdate.setBankBranch(bankBranch);
        toUpdate.setBank(bank);
    }

    public void updateTermsOfEmployee(String Id, int salary, int educationFund, int sickDays, int daysOff) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is logged in");
        }

        if(!loggedIn.getIsManager()){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
            throw new EmployeeException("Employee not found");
        }
        TermsOfEmployment toUpdate = employees.get(Id).getTerms();
        toUpdate.setSalary(salary);
        toUpdate.setEducationFund(educationFund);
        toUpdate.setSickDays(sickDays);
        toUpdate.setDaysOff(daysOff);
    }

    public LinkedList<String> getRoleInDate(LocalDate date, Role roleName, int shift) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is logged in");
        }
        if(!loggedIn.getIsManager()){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(employees.isEmpty()){
            throw new EmployeeException("The list of employees is empty");
        }
        LinkedList<String> specificRole = new LinkedList<>();
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            Employee employee = entry.getValue();
            if(employee.getRole()== roleName){
                if(employee.getConstraints().containsKey(date)) {//Checks if the employee has a constraint on this day
                    if(shift==0) {//Checks on morning shift
                        if (!employee.getConstraints().get(date).isMorningShift())// If the employee is free on this shift
                            specificRole.add(employee.getID());
                    }
                    else if(shift==1) {//Checks on evening shift
                        if (!employee.getConstraints().get(date).isEveningShift())// If the employee is free on this shift
                            specificRole.add(employee.getID());
                    }
                }
                else{ // If the employee is free on this day
                    specificRole.add(employee.getID());
                }
            }
        }
        return specificRole;
    }

    public boolean isExist(String role, String Id){
        boolean isExist = employees.containsKey(Id);
        isExist = isExist && employees.get(Id).isEmployed();
        isExist = isExist &&employees.get ( Id ).getRole ().name ().equals ( role );
        return isExist &&employees.get ( Id ).getRole ().name ().equals ( role );
    }

    public void createData () throws EmployeeException {
            createUshers();
            createGuard();
            creatCashier();
            creatStoreKeeper();
            createManagers();
            createShiftManagers();
            creatDriverC ();
            creatDriverC1 ();
            creatTruckingManager ();
    }

    private void createShiftManagers() throws EmployeeException {
        int accountNum = 476, bankBranch=11, salary=7000, educationFund=1232, sickDays=10, daysOff=30;
        String bankName = "Otzar hachayal";
        for(int i=0; i<2; i++){
            BankAccountInfo employeeAccountInfo = new BankAccountInfo(accountNum+i, bankBranch, bankName);
            TermsOfEmployment termsOfEmployment = new TermsOfEmployment(salary+1, educationFund,sickDays, daysOff );
            Employee shiftManager = new Employee("shiftManager", "66666666"+i, termsOfEmployment, LocalDate.now(), employeeAccountInfo);
            giveConstrForExistingData(shiftManager, LocalDate.now().plusWeeks(3).plusDays ( 3 ),0,"Bat-Mitzva");
            addEmplForExistingData(shiftManager);
        }
    }

    private void createManagers() throws EmployeeException {
        int accountNum = 546, bankBranch=11, salary=8000, educationFund=1232, sickDays=10, daysOff=40;
        String bankName = "Hpoalim";
        FacadeBankAccountInfo employeeAccountInfo1 = new FacadeBankAccountInfo(accountNum, bankBranch, bankName);
        FacadeTermsOfEmployment termsOfEmployment1 = new FacadeTermsOfEmployment(salary, educationFund,sickDays, daysOff );
        FacadeEmployee branchManager = new FacadeEmployee("branchManager", "123000000", LocalDate.now(), employeeAccountInfo1, termsOfEmployment1);

        FacadeBankAccountInfo employeeAccountInfo2 = new FacadeBankAccountInfo(accountNum+2, bankBranch, bankName);
        FacadeTermsOfEmployment termsOfEmployment2 = new FacadeTermsOfEmployment(salary, educationFund,sickDays, daysOff );
        FacadeEmployee branchManagerAssistant = new FacadeEmployee("branchManagerAssistant", "456000000", LocalDate.now(), employeeAccountInfo2, termsOfEmployment2);

        FacadeBankAccountInfo employeeAccountInfo3 = new FacadeBankAccountInfo(accountNum+3, bankBranch, bankName);
        FacadeTermsOfEmployment termsOfEmployment3 = new FacadeTermsOfEmployment(salary, educationFund,sickDays, daysOff );
        FacadeEmployee humanResourcesManager = new FacadeEmployee("humanResourcesManager", "789000000", LocalDate.now(), employeeAccountInfo3, termsOfEmployment3);
        addManager(branchManager);
        addManager(branchManagerAssistant);
        addManager(humanResourcesManager);
    }


    private void createUshers() throws EmployeeException {
        int accountNum = 456, bankBranch=11, salary=5000, educationFund=1232, sickDays=10, daysOff=30;
        String bankName = "Hpoalim";
        for(int i=0; i<4; i++){
            BankAccountInfo employeeAccountInfo = new BankAccountInfo(accountNum+i, bankBranch, bankName);
            TermsOfEmployment termsOfEmployment = new TermsOfEmployment(salary, educationFund,sickDays, daysOff );
            Employee usher = new Employee("usher", "00000000"+i, termsOfEmployment, LocalDate.now(), employeeAccountInfo);
            giveConstrForExistingData(usher, LocalDate.now().plusWeeks(2).plusDays ( 3 ),0,"wedding");
            addEmplForExistingData(usher);
        }
    }

    private void createGuard()  {
        int accountNum = 356, bankBranch=10, salary=5500, educationFund=1232, sickDays=10, daysOff=30;
        String bankName = "Leumi";
        for(int i=0; i<2; i++){
            BankAccountInfo employeeAccountInfo = new BankAccountInfo(accountNum+i, bankBranch, bankName);
            TermsOfEmployment termsOfEmployment = new TermsOfEmployment(salary+1, educationFund,sickDays, daysOff );
            Employee guard = new Employee("guard", "01111111"+i, termsOfEmployment, LocalDate.now(), employeeAccountInfo);

            try {
                giveConstrForExistingData(guard, LocalDate.now().plusWeeks(2).plusDays(5), 1, "wedding");
                addEmplForExistingData(guard);
            }
            catch(EmployeeException e){}
        }
    }

    private void creatCashier() {
        int accountNum = 256, bankBranch=13, salary=5500, educationFund=1232, sickDays=10, daysOff=30;
        String bankName = "Leumi";
        for(int i=0; i<4; i++){
            BankAccountInfo employeeAccountInfo = new BankAccountInfo(accountNum+i, bankBranch, bankName);
            TermsOfEmployment termsOfEmployment = new TermsOfEmployment(salary+1, educationFund,sickDays, daysOff );
            Employee cashier = new Employee("cashier", "02222222"+i, termsOfEmployment, LocalDate.now(), employeeAccountInfo);
            try {
                addEmplForExistingData(cashier);
            }
            catch(EmployeeException e){}
        }
    }

    private void creatStoreKeeper() throws EmployeeException {
        int accountNum = 156, bankBranch=13, salary=6000, educationFund=1232, sickDays=10, daysOff=30;
        String bankName = "Leumi";
        for(int i=0; i<2; i++){
            BankAccountInfo employeeAccountInfo = new BankAccountInfo(accountNum+i, bankBranch, bankName);
            TermsOfEmployment termsOfEmployment = new TermsOfEmployment(salary+1, educationFund,sickDays, daysOff );
            Employee storeKeeper = new Employee("storeKeeper", "03333333"+i, termsOfEmployment, LocalDate.now(), employeeAccountInfo);
            addEmplForExistingData(storeKeeper);
        }
    }

    private void creatDriverC() throws EmployeeException {
        int accountNum = 589, bankBranch=27, salary=6000, educationFund=1000, sickDays=21, daysOff=15;
        String bankName = "Diskont";
        for(int i=0; i<3; i++){
            BankAccountInfo employeeAccountInfo = new BankAccountInfo(accountNum+i, bankBranch, bankName);
            TermsOfEmployment termsOfEmployment = new TermsOfEmployment(salary+1, educationFund,sickDays, daysOff );
            Employee storeKeeper = new Employee("driverC", "04444444"+i, termsOfEmployment, LocalDate.now(), employeeAccountInfo);
            addEmplForExistingData(storeKeeper);
        }
    }

    private void creatDriverC1() throws EmployeeException {
        int accountNum = 663, bankBranch=54, salary=7000, educationFund=1000, sickDays=21, daysOff=15;
        String bankName = "Diskont";
        for(int i=0; i<3; i++){
            BankAccountInfo employeeAccountInfo = new BankAccountInfo(accountNum+i, bankBranch, bankName);
            TermsOfEmployment termsOfEmployment = new TermsOfEmployment(salary+1, educationFund,sickDays, daysOff );
            Employee storeKeeper = new Employee("driverC1", "05555555"+i, termsOfEmployment, LocalDate.now(), employeeAccountInfo);
            addEmplForExistingData(storeKeeper);
        }
    }

    private void creatTruckingManager() throws EmployeeException {
        int accountNum = 729, bankBranch = 27, salary = 9000, educationFund = 1000, sickDays = 21, daysOff = 15;
        String bankName = "Diskont";
        BankAccountInfo employeeAccountInfo = new BankAccountInfo ( accountNum, bankBranch, bankName );
        TermsOfEmployment termsOfEmployment = new TermsOfEmployment ( salary + 1, educationFund, sickDays, daysOff );
        Employee truckingManager = new Employee ( "truckingManager", "066666666", termsOfEmployment, LocalDate.now ( ), employeeAccountInfo );
        addEmplForExistingData ( truckingManager );
    }

    private void giveConstrForExistingData(Employee employee, LocalDate date, int shift, String reason) throws EmployeeException {
        employee.giveConstraint(date, shift, reason);
    }

    // private methods
    private BankAccountInfo createAccount(FacadeBankAccountInfo f) throws EmployeeException {
        if(f.getAccountNumber () < 0 | f.getBankBranch () < 0 ){
            throw new EmployeeException("One of the details entered is negative ");
        }
        if(f.getBank () == null || Character.isDigit (f.getBank ().charAt ( 0 )))
            throw new EmployeeException ( "Bank name is illegal." );
        return new BankAccountInfo(f.getAccountNumber (), f.getBankBranch (), f.getBank ());
    }

    private TermsOfEmployment creatTermsOfEmployment( FacadeTermsOfEmployment f) throws EmployeeException {
        if(f.getSalary () < 0 | f.getEducationFund () < 0 | f.getSickDays () < 0 | f.getDaysOff () < 0){
            throw new EmployeeException("One of the details entered is negative ");
        }
        return new TermsOfEmployment(f.getSalary (), f.getEducationFund (), f.getSickDays (), f.getDaysOff ());
    }

    private boolean validId(String ID){
        if(ID.length () != 9)
            return false;
        try{
            Integer.parseInt ( ID );
            return true;
        }catch (NumberFormatException n) {
            return false;
        }
    }


}
