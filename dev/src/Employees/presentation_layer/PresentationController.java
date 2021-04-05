package Employees.presentation_layer;

import Employees.business_layer.Employee.Role;
import Employees.business_layer.facade.FacadeService;
import Employees.business_layer.facade.facadeObject.FacadeBankAccountInfo;
import Employees.business_layer.facade.facadeObject.FacadeEmployee;
import Employees.business_layer.facade.facadeObject.FacadeShift;
import Employees.business_layer.facade.facadeObject.FacadeTermsOfEmployment;

import java.time.LocalDate;
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
            login ();
        }
        else
            System.out.println ("Only a manager can start a clean program." );
    }

    public void uploadData(){

    }

    public void getRecommendation(LocalDate startingDate) {
//        if()
//        stringConverter.convertWeeklyShiftSchedule (facadeService.getRecommendation ( startingDate ).value);
    }

    public void createWeeklyshiftSchedule(LocalDate startingDate, FacadeShift[][] shifts)
    {

    }

    public void createEmptyWeeklyShiftSchedule(LocalDate startingDate){

    }

    public void addShift(LocalDate date, int shift, HashMap<Role, List<Integer>> manning) {

    }

    public void changeShift(LocalDate date, int shift, HashMap<Role, List<Integer>> manning) {

    }

    public void addEmployeeToShift(Role role, int ID, LocalDate date, int shift){

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

    public void getWeeklyShiftSchedule(LocalDate date) {

    }

    public void login() {
        int id = menuPrinter.loginID ( );
        Role role = menuPrinter.loginRole ();
        int choice;
        FacadeEmployee facadeEmployee;
        if(role != null) {
            facadeEmployee = facadeService.login ( id, role );
            if(facadeEmployee.isManager ()) {
                choice = menuPrinter.managerMenu ( );
                handleManagerChoice ( choice );
            }
            else {
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
            }
        }
        else
            return;
    }

    private void handleManagerChoice(int choice){
        switch (choice){
            case 1:
                createWeeklyshiftSchedule ( );
            case 2:
                getWeeklyShiftScheduleManager ( );
            case 3:
                getEmployee( );
            case 4:
                addEmployee (  );
            case 5:
                removeEmployee (  );
            case 6:
                logout ();
            default:
                System.out.println ("choice is illegal.");
        }
    }

    private void handleSimpleEmployeeChoice(int choice){
        switch (choice){
            case 1:
                getWeeklyShiftSchedule (  );
            case 2:
                giveConstraint (  );
            case 3:
                deleteConstraint (  );
            case 4:
                updateConstraint (  );
            case 5:
                logout ();
            default:
                System.out.println ("choice is illegal.");
        }
    }

    public void logout()  {

    }

    public void giveConstraint(FacadeEmployee employee, LocalDate date, int shift, String reason) {

    }

    public void updateConstraint (FacadeEmployee employee, LocalDate date, int shift, String reason) {

    }

    public void deleteConstraint (FacadeEmployee employee, LocalDate date, int shift)  {

    }

    public void addEmployee(Role role, int Id, FacadeTermsOfEmployment terms, LocalDate transactionDate, FacadeBankAccountInfo bank) {

    }

    public void removeEmployee(int Id)  {

    }

    public void deleteBankAccount(int Id){

    }

    public void updateBankAccount(int Id, int accountNum, int bankBranch, String bank) {

    }

    public void updateTermsOfemployee(int Id, int salary, int educationFund, int sickDays, int daysOff) {

    }

    public void getEmployee() {

    }

    private void getWeeklyShiftScheduleManager() {

    }
}
