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

    public void getRecommendation(LocalDate startingDate) {
//        if()
//        stringConverter.convertWeeklyShiftSchedule (facadeService.getRecommendation ( startingDate ).value);
    }

    public void createWeeklyshiftSchedule()
    {
        menuPrinter.print ( "Write the shift's details: \n" );
        LocalDate date = menuPrinter.schedule ();
        FacadeShift[][] shifts = new FacadeShift[7][2];
        for(int i = 0; i < 6; i++)
        {
            shifts[i][0] = createFirstShift ( date.plusDays ( i ) );
            shifts[i][1] = createSecondShift ( date.plusDays ( i ) );
        }
        shifts[5][0] = createFirstShift ( date.plusDays ( 5 ) );
        shifts[6][1] = createSecondShift ( date.plusDays ( 6 ) );
        ResponseT<FacadeWeeklyShiftSchedule> weeklyShiftSchedule = facadeService.createWeeklyshiftSchedule ( date, shifts );
        if(weeklyShiftSchedule.errorOccured ())
        {
            menuPrinter.print ( weeklyShiftSchedule.getErrorMessage () );
            return;
        }
    }

    private FacadeShift createFirstShift(LocalDate date)
    {
        menuPrinter.print( "Write the type of the first shift in date " + date );
        String type = menuPrinter.getString ();
        FacadeShift shift = new FacadeShift ( date, type, 0 );
        HashMap <Role, List<String>> manning = createManning ();
        shift.setManning ( manning );
        return shift;
    }

    private FacadeShift createSecondShift(LocalDate date)
    {
        menuPrinter.print( "Write the type of the first second in date " + date );
        String type = menuPrinter.getString ();
        FacadeShift shift = new FacadeShift ( date, type, 1 );
        HashMap <Role, List<String>> manning = createManning ();
        shift.setManning ( manning );
        return shift;
    }

    private HashMap<Role, List<String>> createManning(){
        Role role = menuPrinter.roleMenu ();
        HashMap <Role, List<String>> manning = new HashMap<> (  );
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

    public void changeShift(LocalDate date, int shift, HashMap<Role, List<Integer>> manning) {

    }

    public void addEmployeeToShift(Role role, String ID, LocalDate date, int shift){

    }

    public void deleteEmployeeFromShift(Role role, int ID, LocalDate date, int shift)  {

    }

    public void changeShiftType(LocalDate date, int shift, String shiftType) {

    }

    public void createShiftType(String shiftype, HashMap<Role, Integer> manning){

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
        Role role = menuPrinter.roleMenu ();
        int choice;
        FacadeEmployee facadeEmployee;
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
                getEmployee( );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
            case 4:
                addEmployee (  );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
            case 5:
                removeEmployee (  );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice );
            case 6:
                logout ();
            default:
                System.out.println ("choice is illegal.");
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
//            case 5:
//                updateConstraint (  );
//                choice = menuPrinter.simpleEmployeeMenu ();
//                handleSimpleEmployeeChoice ( choice );
            case 5:
                getConstraints();
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
            case 6:
                if(logout () == false)
                {
                    choice = menuPrinter.simpleEmployeeMenu ();
                    handleSimpleEmployeeChoice ( choice );
                }
                while(login () == false);
            default:
                System.out.println ("choice is illegal.");
        }
    }

    private void getShift() {
        LocalDate date = menuPrinter.getShiftDate ();
        int shift = menuPrinter.getShiftNum ();
        if(shift < 0 || shift > 2) {
            menuPrinter.print ( "Shift type is illegal.\n" );
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
        ResponseT<List<FacadeConstraint>> constraints = facadeService.getConstraints();
        if(constraints.errorOccured ())
        {
            menuPrinter.print ( constraints.getErrorMessage () );
            return;
        }
        for ( FacadeConstraint entry: constraints.value) {
            menuPrinter.print ( stringConverter.convertConstraint ( entry ) );
        }
    }

    public boolean logout()  {
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
        if (r.errorOccured ())
            menuPrinter.print ( r.getErrorMessage () );
    }

//    public void updateConstraint (LocalDate date, int shift, String reason) {
//        FacadeConstraint facadeConstraint = menuPrinter.giveConstraintMenu ();
//        facadeService.updateConstraint ( facadeConstraint );
//    }

    public void deleteConstraint ()  {
        LocalDate date = menuPrinter.dateMenu ();
        int shift = menuPrinter.getShiftNum ();
        Response r = facadeService.deleteConstraint ( date, shift);
        if (r.errorOccured ())
            menuPrinter.print ( r.getErrorMessage () );
    }

    public void addEmployee() {
        Role role;
        String Id;
        FacadeTermsOfEmployment terms;
        LocalDate transactionDate;
        FacadeBankAccountInfo bank;
    }

    public void removeEmployee()  {
        String Id;

    }

    public void updateBankAccount() {
        String Id;
        int accountNum;
        int bankBranch;
        String bank;
    }

    public void updateTermsOfemployee() {
        String Id;
        int salary;
        int educationFund;
        int sickDays;
        int daysOff;
    }

    public void getEmployee() {

    }

    private void getWeeklyShiftScheduleManager() {

    }
}
