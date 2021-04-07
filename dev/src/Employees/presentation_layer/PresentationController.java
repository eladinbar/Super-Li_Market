package Employees.presentation_layer;

import Employees.business_layer.Employee.Role;
import Employees.business_layer.Shift.Shift;
import Employees.business_layer.Shift.ShiftTypes;
import Employees.business_layer.Shift.WeeklyShiftSchedule;
import Employees.business_layer.facade.FacadeService;
import Employees.business_layer.facade.Response;
import Employees.business_layer.facade.ResponseT;
import Employees.business_layer.facade.facadeObject.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresentationController {
    FacadeService facadeService;
    MenuPrinter menuPrinter;
    StringConverter stringConverter;

    public PresentationController(){
        facadeService = new FacadeService ();
        menuPrinter = new MenuPrinter ();
        stringConverter = new StringConverter ();
    }

    public void start(){
        int choice = menuPrinter.uploadProgram ();
        if(choice == 0)
            uploadClean ();
        else
            uploadData ();
    }

    public void uploadClean(){
        char choice = menuPrinter.uploadClean ();
        int choice2, id;
        Role role;
        FacadeEmployee manager;
        if(choice == 'y') {
            manager = menuPrinter.createManagerAccountMenu ( );
            facadeService.addEmployee ( manager );
            while(login () == false);
        }
        else
            menuPrinter.print ("Only a manager can start a clean program." );
    }

    public void uploadData(){

    }

    private void handleManagerChoice(int choice){
        switch (choice){
            case 1:
                createWeeklyshiftSchedule ( );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
            case 2:
                getWeeklyShiftScheduleManager ( );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
            case 3:
                createShiftType (  );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
            case 4:
                getEmployeeList();
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
            case 5:
                getEmployeeByManager( );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
            case 6:
                getEmployeeConstraints();
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
            case 7:
                addEmployee (  );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
            case 8:
                if(logout () == false)
                {
                    choice = menuPrinter.managerMenu ();
                    handleManagerChoice ( choice );
                }
                while(login () == false);
            default:
                menuPrinter.printChoiceException();
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
        }
    }

    private void handleSimpleEmployeeChoice(int choice){
        switch (choice){
            case 1:
                getWeeklyShiftSchedule ( );
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
            case 2:
                getShift();
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
            case 3:
                giveConstraint (  );
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
            case 4:
                deleteConstraint (  );
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
            case 5:
                getEmployee ();
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
            case 6:
                getConstraints ();
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
            case 7:
                if(logout () == false)
                {
                    choice = menuPrinter.simpleEmployeeMenu ();
                    handleSimpleEmployeeChoice ( choice );
                }
                while(login () == false);
            default:
                menuPrinter.printChoiceException();
        }
    }

    private void handleManagerScheduleChoice(LocalDate date, int shift, int choice) {
        switch (choice) {
            case 1:
                changeShift ( date, shift );
                choice = menuPrinter.scheduleManagerMenu ( );
                handleManagerScheduleChoice ( date, shift, choice );
            case 2:
                addEmployeeToShift ( date, shift );
                choice = menuPrinter.scheduleManagerMenu ( );
                handleManagerScheduleChoice ( date, shift, choice );
            case 3:
                deleteEmployeeFromShift ( date, shift );
                choice = menuPrinter.scheduleManagerMenu ( );
                handleManagerScheduleChoice ( date, shift, choice );
            case 4:
                changeShiftType ( date, shift );
                choice = menuPrinter.scheduleManagerMenu ( );
                handleManagerScheduleChoice ( date, shift, choice );
            case 5:
                return;
            default:
                menuPrinter.printChoiceException ( );
                return;
        }
    }

    public void getRecommendation() {
        LocalDate date = menuPrinter.schedule ();
    }

    public void createWeeklyshiftSchedule()
    {
        int choice = menuPrinter.createWeeklyShiftSchedule();
        if(choice < 1 || choice > 2)
        {
            menuPrinter.printChoiceException();
            return;
        }
        if(choice == 1) {
            menuPrinter.print ( "Write the shift's details: \n" );
            LocalDate date = menuPrinter.schedule ( );
            FacadeShift[][] shifts = new FacadeShift[7][2];
            for ( int i = 0 ; i < 6 ; i++ ) {
                shifts[i][0] = createFirstShift ( date.plusDays ( i ) );
                shifts[i][1] = createSecondShift ( date.plusDays ( i ) );
            }
            shifts[5][0] = createFirstShift ( date.plusDays ( 5 ) );
            shifts[6][1] = createSecondShift ( date.plusDays ( 6 ) );
            ResponseT<FacadeWeeklyShiftSchedule> weeklyShiftSchedule = facadeService.createWeeklyshiftSchedule ( date, shifts );
            if (weeklyShiftSchedule.errorOccured ( )) {
                menuPrinter.print ( weeklyShiftSchedule.getErrorMessage ( ) );
                return;
            }
            menuPrinter.print ( "Weekly shift schedule created successfully.\n" );
        }
        else
            getRecommendation (  );
        editSchedule ();
    }

    private FacadeShift createFirstShift(LocalDate date)
    {
        menuPrinter.print( "Write the type of the first shift in date " + date );
        String type = menuPrinter.getString ();
        FacadeShift shift = new FacadeShift ( date, type, 0 );
        HashMap <String, List<String>> manning = createManning ();
        shift.setManning ( manning );
        return shift;
    }

    private FacadeShift createSecondShift(LocalDate date)
    {
        menuPrinter.print( "Write the type of the first second in date " + date );
        String type = menuPrinter.getString ();
        FacadeShift shift = new FacadeShift ( date, type, 1 );
        HashMap <String, List<String>> manning = createManning ();
        shift.setManning ( manning );
        return shift;
    }

    private HashMap<String, List<String>> createManning(){
        String role = menuPrinter.roleMenu ();
        HashMap <String, List<String>> manning = new HashMap<> (  );
        List<String> roleManning = new ArrayList<> (  );
        while(role != null)
        {
            String id = menuPrinter.idGetter();
            while (id != "0")
            {
                roleManning.add ( id );
            }
            manning.put ( role, roleManning.subList ( 0,roleManning.size () ) );
            roleManning.clear ();
        }
        return manning;
    }

    public void addShift(LocalDate date, int shift, HashMap<Role, List<Integer>> manning) {

    }

    public void changeShift(LocalDate date, int shift) {
        HashMap<String, List<String>> manning = createManning ();
        Response response = facadeService.changeShift ( date, shift, manning );
        if(response.errorOccured ())
        {
            menuPrinter.print ( response.getErrorMessage ());
            return;
        }
        menuPrinter.print ( "Shift changed successfully.\n" );
    }

    public void addEmployeeToShift(LocalDate date, int shift) {
        String role = menuPrinter.roleMenu ( );
        menuPrinter.print ( "ID: " );
        String ID = menuPrinter.getString ( );
        Response response = facadeService.addEmployeeToShift ( role, ID, date, shift );
        if (response.errorOccured ( )) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Employee added to shift successfully.\n" );
    }

    public void deleteEmployeeFromShift(LocalDate date, int shift)  {
        String role = menuPrinter.roleMenu ( );
        menuPrinter.print ( "ID: " );
        String ID = menuPrinter.getString ( );
        Response response = facadeService.deleteEmployeeFromShift ( role, ID, date, shift );
        if (response.errorOccured ( )) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Employee deleted from shift successfully.\n" );
    }

    public void changeShiftType(LocalDate date, int shift) {
        menuPrinter.print ( "Write shift type name: " );
        String shiftType = menuPrinter.getString ( );
        Response response = facadeService.changeShiftType ( date, shift, shiftType );
        if (response.errorOccured ( )) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Shift type changed successfully.\n" );
    }

    public void createShiftType(){
        menuPrinter.print ( "Write the shift type name: " );
        String shiftype = menuPrinter.getString ();
        HashMap<String, Integer> manning = new HashMap<> (  );
        String role = menuPrinter.roleMenu ();
        while (role != null)
        {
            menuPrinter.print ( "Write the num of employees you would like in this role: " );
            manning.put ( role, ( menuPrinter.getInt () ) );
            role = menuPrinter.roleMenu ();
        }
        Response response = facadeService.createShiftType ( shiftype, manning );
        if (response.errorOccured ( )) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Shift type added successfully.\n" );
    }

    public void updateRoleManning(String shiftType, Role role, int num) {

    }

    public void addRoleManning(String shiftType, Role role, int num) {

    }

    public void getWeeklyShiftSchedule() {
        LocalDate date = menuPrinter.schedule();
        ResponseT<FacadeWeeklyShiftSchedule> schedule = facadeService.getWeeklyShiftSchedule ( date );
        if(schedule.errorOccured ())
        {
            menuPrinter.print ( schedule.getErrorMessage () );
            return;
        }
        String s = stringConverter.convertWeeklyShiftSchedule ( schedule.value );
        menuPrinter.print(s);
    }

    public boolean login() {
        int id = menuPrinter.loginID ( );
        String role = menuPrinter.roleMenu ();
        int choice;
        if(role != null) {
            ResponseT<FacadeEmployee> employee = facadeService.login ( id, role );
            if(employee.errorOccured ())
            {
                menuPrinter.print ( employee.getErrorMessage () );
                return false;
            }
            if(employee.value.isManager ()) {
                choice = menuPrinter.managerMenu ( );
                handleManagerChoice ( choice );
                return true;
            }
            else {
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
                return true;
            }
        }
        else
            return false;
    }



    private void getShift() {
        LocalDate date = menuPrinter.getShiftDate ();
        int shift = menuPrinter.getShiftNum ();
        if(shift < 0 || shift > 2) {
            menuPrinter.printChoiceException();
            return;
        }
        if(shift < 2) {
            ResponseT<FacadeShift> facadeShift = facadeService.getShift ( date, shift );
            if(facadeShift.errorOccured ())
            {
                menuPrinter.print ( facadeShift.getErrorMessage () );
                return;
            }
            String s = stringConverter.convertShift ( facadeShift.value );
            menuPrinter.print ( s );
        }
        else
        {
            ResponseT<FacadeShift> facadeShift = facadeService.getShift ( date, 0 );
            if(facadeShift.errorOccured ())
            {
                menuPrinter.print ( facadeShift.getErrorMessage () );
                return;
            }
            String s = stringConverter.convertShift ( facadeShift.value );
            menuPrinter.print ( s );
            facadeShift = facadeService.getShift ( date, 1 );
            if(facadeShift.errorOccured ())
            {
                menuPrinter.print ( facadeShift.getErrorMessage () );
                return;
            }
            s = stringConverter.convertShift ( facadeShift.value );
            menuPrinter.print ( s );
        }
    }

    private void getConstraints() {
        menuPrinter.print ( "Constraints:\n" );
        ResponseT<HashMap<LocalDate, FacadeConstraint>> constraints = facadeService.getConstraints();
        if(constraints.errorOccured ())
        {
            menuPrinter.print ( constraints.getErrorMessage () );
            return;
        }
        for ( Map.Entry<LocalDate, FacadeConstraint> entry: constraints.value.entrySet ()) {
            menuPrinter.print ( stringConverter.convertConstraint ( entry.getValue () ) );
        }
    }

    private void getConstraintsOfEmployee() {
        menuPrinter.print ( "ID of employee you would like to display: " );
        String id = menuPrinter.getString ();
        ResponseT<List<FacadeConstraint>> constraints = facadeService.getConstraints(id);
        if(constraints.errorOccured ())
        {
            menuPrinter.print ( constraints.getErrorMessage () );
            return;
        }
        menuPrinter.print ( "Constraints:\n" );
        for ( FacadeConstraint entry: constraints.value) {
            menuPrinter.print ( stringConverter.convertConstraint ( entry ) );
        }
    }

    private boolean logout()  {
        ResponseT<FacadeEmployee> employee = facadeService.logout ().errorOccured ();
        if(employee.errorOccured ()) {
            menuPrinter.print ( employee.getErrorMessage ( ) );
            return false;
        }
        menuPrinter.print ("ID " + employee.value.getID () + " loggedout succesfuly.");
        return true;
    }

    public void giveConstraint() {
        menuPrinter.print ( "Write the constraint detail:\n" );
        LocalDate date = menuPrinter.dateMenu ();
        int shift = menuPrinter.getShiftNum ();
        menuPrinter.print ( "reason: " );
        String reason = menuPrinter.getString();
        Response r = facadeService.giveConstraint ( date, shift, reason );
        if (r.errorOccured ()) {
            menuPrinter.print ( r.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Constraint added successfully.\n" );
    }

    public void deleteConstraint ()  {
        LocalDate date = menuPrinter.dateMenu ();
        int shift = menuPrinter.getShiftNum ();
        Response r = facadeService.deleteConstraint (date, shift);
        if (r.errorOccured ()) {
            menuPrinter.print ( r.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Constraint deleted successfully.\n" );
    }

    public void addEmployee() {
        menuPrinter.print ( "Write the new employee details:\n" );
        FacadeEmployee facadeEmployee = menuPrinter.getEmployeeDetails ( );
        Response response = facadeService.addEmployee ( facadeEmployee );
        if(response.errorOccured ())
        {
            menuPrinter.print ( response.getErrorMessage () );
            return;
        }
        menuPrinter.print ( "Employee added successfully.\n" );
    }

    public void removeEmployee()  {
        String Id;

    }

    public void updateBankAccount(String ID) {
        menuPrinter.print ( "Write account number: " );
        int accountNum = menuPrinter.getInt ();
        menuPrinter.print ( "Write bank branch: " );
        int bankBranch = menuPrinter.getInt ();
        menuPrinter.print ( "Write bank name: " );
        String bank = menuPrinter.getString ();
        Response response = facadeService.updateBankAccount ( ID, accountNum, bankBranch, bank );
        if(response.errorOccured ())
        {
            menuPrinter.print ( response.getErrorMessage () );
            return;
        }
        menuPrinter.print ( "Bank account changed successfully." );
    }

    public void updateTermsOfemployee(String ID) {
        menuPrinter.print ( "Write salary: " );
        int salary = menuPrinter.getInt ();
        menuPrinter.print ( "Write education fund: " );
        int educationFund = menuPrinter.getInt ();
        menuPrinter.print ( "Write sick days: " );
        int sickDays = menuPrinter.getInt ();
        menuPrinter.print ( "Write days off: " );
        int daysOff = menuPrinter.getInt ();
        Response response = facadeService.updateTermsOfEmployee ( ID, salary, educationFund, sickDays, daysOff );
        if(response.errorOccured ())
        {
            menuPrinter.print ( response.getErrorMessage () );
            return;
        }
        menuPrinter.print ( "Terms of employment changed successfully." );
    }

    public void getEmployee() {
        ResponseT<FacadeEmployee> employee = facadeService.getLoggedin();
        if (employee.errorOccured ())
        {
            menuPrinter.print ( employee.getErrorMessage () );
            return;
        }
        String s = stringConverter.convertEmployee ( employee.value );
        menuPrinter.print ( s );
    }

    private void getWeeklyShiftScheduleManager() {
        getWeeklyShiftSchedule ( );
        editSchedule ();
    }

    private void editSchedule(){
        menuPrinter.print ( "Would you like to edit one of the shifts? n\\y" );
        char c = menuPrinter.getChar ( );
        if (c == 'y') {
            LocalDate date = menuPrinter.getShiftDate ( );
            int shift = menuPrinter.getOneShiftNum ( );
            int choice = menuPrinter.scheduleManagerMenu ( );
            handleManagerScheduleChoice (date, shift, choice );
        }
    }

    private void getEmployeeByManager() {
        menuPrinter.print ( "Write the ID of the employee you would like to display: " );
        String ID = menuPrinter.getString ( );
        ResponseT<FacadeEmployee> employee = facadeService.getEmployee ( ID );
        if (employee.errorOccured ())
        {
            menuPrinter.print ( employee.getErrorMessage () );
            return;
        }
        String s = stringConverter.convertEmployee ( employee.value );
        menuPrinter.print ( s );
        int choice = menuPrinter.getEmployeeMenu ();
        if(choice < 4)
            updateEmployee (employee.value, choice);
    }

    private void updateEmployee(FacadeEmployee facadeEmployee, int choice) {
        Response response;
        switch (choice){
            case 1:
                response = facadeService.removeEmployee ( facadeEmployee.getID () );
                if(response.errorOccured ())
                {
                    menuPrinter.print ( response.getErrorMessage () );
                    return;
                }
                menuPrinter.print ( "Employee deleted successfully." );
            case 2:
                updateBankAccount (facadeEmployee.getID ());
            case 3:
                updateTermsOfemployee (facadeEmployee.getID ());
            default:
                menuPrinter.printChoiceException ();
        }
    }

    private void getEmployeeConstraints() {
        menuPrinter.print ( "Write the ID of the employee you would like to display: " );
        String ID = menuPrinter.getString ( );
        ResponseT< HashMap <LocalDate, FacadeConstraint> > constraints = facadeService.getConstraints ( ID );
    }

    private void getEmployeeList() {
        ResponseT<HashMap<Role, List<String>>> employees = facadeService.getEmployees ( );
        if(employees.errorOccured ())
        {
            menuPrinter.print ( employees.getErrorMessage () );
            return;
        }
        for ( Map.Entry<Role, List<String>> entry : employees.value.entrySet () ){
            menuPrinter.print ( entry.getKey ().name () +":\n" );
            for(String s: entry.getValue ())
            {
                menuPrinter.print ( s + "\n" );
            }
        }
    }
}
