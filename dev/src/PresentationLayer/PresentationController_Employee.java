package PresentationLayer;

import BusinessLayer.EmployeePackage.EmployeeNotification;
import BusinessLayer.EmployeePackage.EmployeePackage.Role;
import BusinessLayer.EmployeePackage.ShiftPackage.ShiftTypes;
import BusinessLayer.Notification;
import DataAccessLayer.DalControllers.EmployeeControllers.DalAlertEmployeeController;
import ServiceLayer.Employee_Package_FacadeService;
import ServiceLayer.FacadeObjects.FacadeConstraint;
import ServiceLayer.FacadeObjects.FacadeEmployee;
import ServiceLayer.FacadeObjects.FacadeShift;
import ServiceLayer.FacadeObjects.FacadeWeeklyShiftSchedule;
import ServiceLayer.Response.Response;
import ServiceLayer.Response.ResponseT;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresentationController_Employee {
    Employee_Package_FacadeService facadeService;
    MenuPrinter_Employee menuPrinter;
    StringConverter stringConverter;
    HashMap<Role,List<EmployeeNotification>> alerts;

    public PresentationController_Employee() {
        facadeService = new Employee_Package_FacadeService ( );
        menuPrinter = new MenuPrinter_Employee ( );
        stringConverter =  StringConverter.getInstance ( );
        alerts = new HashMap<> (  );
    }

    public void start() throws SQLException {
        int choice = menuPrinter.uploadProgram ( );
        if (choice == 2)
            uploadClean ( );
        else
            uploadData ( );
    }

    private void uploadClean() throws SQLException {
        ResponseT<Boolean> start = loadData ( );
        loadAlerts ();
        if (start.errorOccurred ( )) {
            menuPrinter.print ( start.getErrorMessage ( ) );
            return;
        }
        if (!start.value) {
            char choice = menuPrinter.uploadClean ( );
            FacadeEmployee manager;
            if (choice == 'y') {
                manager = menuPrinter.createManagerAccountMenu ( );
                while (manager == null) {
                    manager = menuPrinter.createManagerAccountMenu ( );
                }
                ResponseT<FacadeEmployee> facadeEmployee = facadeService.addManager ( manager );
                if (facadeEmployee.errorOccurred ( )) {
                    menuPrinter.print ( facadeEmployee.getErrorMessage ( ) );
                    return;
                }
                while (!login ( true ));
                while (!login ( false )) ;
            } else
                menuPrinter.print ( "Only a manager can start a clean program." );
        } else {
            if (ShiftTypes.getInstance ().numOfShiftTypes () < 2) {
                char choice = menuPrinter.uploadClean ( );
                if (choice == 'y') {
                    while (!login ( true )) ;
                } else
                    menuPrinter.print ( "Only a manager can start a program without shift types." );
            } else {
                while (!login ( false )) ;
            }
        }
    }

    private void loadAlerts() throws SQLException {
        ResponseT alerts = facadeService.loadAlerts();
        if(alerts.errorOccurred ())
            menuPrinter.print ( alerts.getMessage () );
        this.alerts = (HashMap<Role, List<EmployeeNotification>>) alerts.value;
    }

    private ResponseT<Boolean> loadData() throws SQLException {
        return facadeService.loadData();
    }

    private void uploadData() throws SQLException {
        if(!createData())
            return;
        LogisticsManagerMenu.getInstance().putInitialTestState();
        while (!login ( false ));
    }

    private boolean createData() throws SQLException {
        Response response = facadeService.createData();
        if (response.errorOccurred ())
        {
            menuPrinter.print ( response.getErrorMessage () );
            return false;
        }
        return true;
    }

    private void handleManagerChoice(int choice, boolean start) throws SQLException {
        switch (choice){
            case 1:
                createWeeklyShiftSchedule ( start );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice, false );
                break;
            case 2:
                getWeeklyShiftScheduleManager ( );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice, start );
                break;
            case 3:
                getShiftType ();
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice, start );
                break;
            case 4:
                createShiftType ( 2 );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice, start );
                break;
            case 5:
                getEmployeeList();
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice, start );
                break;
            case 6:
                getEmployeeByManager( );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice, start );
                break;
            case 7:
                getConstraintsOfEmployee ();
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice, start );
                break;
            case 8:
                addEmployee (  );
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice, start );
                break;
            case 9:
                if(!logout ())
                {
                    choice = menuPrinter.managerMenu ();
                    handleManagerChoice ( choice, start );
                }
                while(!login (false));
                break;
            default:
                menuPrinter.printChoiceException();
                choice = menuPrinter.managerMenu ();
                handleManagerChoice ( choice, start );
        }
    }

    private void handleSimpleEmployeeChoice(int choice) throws SQLException {
        switch (choice){
            case 1:
                getWeeklyShiftSchedule ( );
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
                break;
            case 2:
                getShift();
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
                break;
            case 3:
                giveConstraint (  );
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
                break;
            case 4:
                deleteConstraint (  );
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
                break;
            case 5:
                getEmployee ();
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
                break;
            case 6:
                getConstraints ();
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
                break;
            case 7:
                if(!logout ())
                {
                    choice = menuPrinter.simpleEmployeeMenu ();
                    handleSimpleEmployeeChoice ( choice );
                }
                while(!login (false));
                break;
            default:
                menuPrinter.printChoiceException();
        }
    }

    private void handleManagerScheduleChoice(LocalDate date, int shift, int choice) throws SQLException {
        if(shift != 1 & shift != 2)
            menuPrinter.printChoiceException ();
        switch (choice) {
            case 1:
                changeShift ( date, shift );
                choice = menuPrinter.scheduleManagerMenu ( );
                handleManagerScheduleChoice ( date, shift, choice );
                break;
            case 2:
                addEmployeeToShift ( date, shift );
                choice = menuPrinter.scheduleManagerMenu ( );
                handleManagerScheduleChoice ( date, shift, choice );
                break;
            case 3:
                deleteEmployeeFromShift ( date, shift );
                choice = menuPrinter.scheduleManagerMenu ( );
                handleManagerScheduleChoice ( date, shift, choice );
                break;
            case 4:
                changeShiftType ( date, shift );
                choice = menuPrinter.scheduleManagerMenu ( );
                handleManagerScheduleChoice ( date, shift, choice );
                break;
            case 5:
                break;
            default:
                menuPrinter.printChoiceException ( );
                return;
        }
    }

    private void handleShiftTypeMenuChoice(String shiftType, int choice) {
        switch (choice){
            case 1:
                updateRoleManning ( shiftType );
                choice = menuPrinter.shiftTypeMenu ( );
                handleShiftTypeMenuChoice ( shiftType, choice);
                break;
            case 2:
                addRoleManning ( shiftType );
                choice = menuPrinter.shiftTypeMenu ( );
                handleShiftTypeMenuChoice ( shiftType, choice);
                break;
            case 3:
                deleteRoleManning( shiftType );
                choice = menuPrinter.shiftTypeMenu ( );
                handleShiftTypeMenuChoice ( shiftType, choice);
                break;
            case 4:
                break;
            default:
                menuPrinter.printChoiceException ();
                return;
        }
    }

    private void createShiftTypes() throws SQLException {
        menuPrinter.print ( "For continuing you have to create and characterize morning shift type and evening shift type.\n\n" +
                "Create morning shift type:\n" );
        createShiftType(0);
        menuPrinter.print ( "Create evening shift type:" );
        createShiftType(1);
    }

    private void deleteRoleManning(String shiftType) {
        String role = menuPrinter.roleMenu_withoutManagers ();
        if(role == null)
            return;
        Response response = facadeService.deleteRoleFromShiftType(shiftType, role);
        if (response.errorOccurred ()) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return; }
        menuPrinter.print ( "Role manning updated successfully.\n" );
    }

    private void updateRoleManning(String shiftType) {
        String role = menuPrinter.roleMenu_withoutManagers ();
        if(role == null)
            return;
        menuPrinter.print ( "Write the manning you would like for role: " );
        int num = menuPrinter.getInt ();
        Response response = facadeService.updateRoleManning ( shiftType, role, num );
        if (response.errorOccurred ()) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Role manning updated successfully." );
    }

    private void addRoleManning(String shiftType) {
        String role = menuPrinter.roleMenu_withoutManagers ();
        if (role == null)
            return;
        menuPrinter.print ( "Write the manning you would like for role: " );
        int num = menuPrinter.getInt ();
        Response response = facadeService.addRoleManning ( shiftType, role, num );
        if (response.errorOccurred ()) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Role added successfully." );
    }

    private void getRecommendation(boolean start) throws SQLException {
        LocalDate date = menuPrinter.schedule ();
        ResponseT<FacadeWeeklyShiftSchedule> weeklyShiftSchedule = facadeService.getRecommendation ( date, start );
        if(weeklyShiftSchedule.errorOccurred ())
        {
            menuPrinter.print ( weeklyShiftSchedule.getErrorMessage () );
            return;
        }
        menuPrinter.print ( stringConverter.convertWeeklyShiftSchedule ( weeklyShiftSchedule.value ) );
        editSchedule ();
    }

    private void createWeeklyShiftSchedule(boolean start) throws SQLException {
        int choice = menuPrinter.createWeeklyShiftSchedule();
        if(choice < 1 || choice > 2)
        {
            menuPrinter.printChoiceException();
            return;
        }
        if(choice == 1) {
            boolean  dateLegal = false;
            menuPrinter.print ( "Write the shifts details:" );
            LocalDate date = menuPrinter.schedule ( );
            while (!dateLegal) {
                if (date == null)
                    return;
                if(!start && !date.getDayOfWeek ().equals ( DayOfWeek.SUNDAY )){
                    menuPrinter.print ( "The selected date is not sunday.\n try again." );
                    date = menuPrinter.schedule ();
                }
                else
                    dateLegal = true;
            }
            FacadeShift[][] shifts = new FacadeShift[7][2];
            for ( int i = 0 ; i < 6 ; i++ ) {
                shifts[i][0] = createFirstShift ( date.plusDays ( i ) );
                shifts[i][1] = createSecondShift ( date.plusDays ( i ) );
            }
            shifts[5][0] = createFirstShift ( date.plusDays ( 5 ) );
            shifts[6][1] = createSecondShift ( date.plusDays ( 6 ) );
            ResponseT<FacadeWeeklyShiftSchedule> weeklyShiftSchedule = facadeService.createWeeklyShiftSchedule ( date, shifts );
            if (weeklyShiftSchedule.errorOccurred ( )) {
                menuPrinter.print ( weeklyShiftSchedule.getErrorMessage ( ) );
                return;
            }
            menuPrinter.print ( "Weekly shift schedule created successfully." );
            menuPrinter.print ( stringConverter.convertWeeklyShiftSchedule ( weeklyShiftSchedule.value ) );
            editSchedule ();
        }
        else
            getRecommendation ( start );
    }

    private FacadeShift createFirstShift(LocalDate date) throws SQLException {
        String choice = menuPrinter.chooseShiftType(date, 1);
        while (choice == null)
        {
            menuPrinter.printChoiceException ();
            choice = menuPrinter.chooseShiftType ( date, 1 );
        }
        if(choice.equals ("new"))
            choice = createShiftType (2);
        FacadeShift shift = new FacadeShift ( date, choice, 0 );
        HashMap <String, List<String>> manning = createManning ();
        shift.setManning ( manning );
        return shift;
    }

    private FacadeShift createSecondShift(LocalDate date) throws SQLException {
        String choice = menuPrinter.chooseShiftType(date, 2);
        while (choice == null)
        {
            menuPrinter.printChoiceException ();
            choice = menuPrinter.chooseShiftType ( date, 2 );
        }
        if(choice.equals ("new"))
            choice = createShiftType (2);
        FacadeShift shift = new FacadeShift ( date, choice, 1 );
        HashMap <String, List<String>> manning = createManning ();
        shift.setManning ( manning );
        return shift;
    }

    private HashMap<String, List<String>> createManning(){
        String role = menuPrinter.roleMenu_withoutManagers ();
        HashMap <String, List<String>> manning = new HashMap<> (  );
        List<String> roleManning = new ArrayList<> (  );
        while(role != null)
        {
            String id = menuPrinter.idGetter();
            while (!id.equals ("0"))
            {
                roleManning.add ( id );
                id = menuPrinter.idGetter();
            }
            manning.put ( role, roleManning.subList ( 0,roleManning.size () ) );
            roleManning.clear ();
            role = menuPrinter.roleMenu_withoutManagers ( );
        }
        return manning;
    }

    private void changeShift(LocalDate date, int shift) throws SQLException {
        HashMap<String, List<String>> manning = createManning ();
        Response response = facadeService.changeShift ( date, shift, manning );
        if(response.errorOccurred ())
        {
            menuPrinter.print ( response.getErrorMessage ());
            return;
        }
        menuPrinter.print ( "Shift changed successfully.\n" );
    }

    private void addEmployeeToShift(LocalDate date, int shift) throws SQLException {
        String role = menuPrinter.roleMenu_withoutManagers ( );
        if(role == null)
            return;
        menuPrinter.print ( "ID: " );
        String ID = menuPrinter.getString ( );
        Response response = facadeService.addEmployeeToShift ( role, ID, date, shift );
        if (response.errorOccurred ( )) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Employee added to shift successfully.\n" );
    }

    private void deleteEmployeeFromShift(LocalDate date, int shift) throws SQLException {
        String role = menuPrinter.roleMenu_withoutManagers ( );
        if(role == null)
            return;
        menuPrinter.print ( "ID: " );
        String ID = menuPrinter.getString ( );
        Response response = facadeService.deleteEmployeeFromShift ( role, ID, date, shift );
        if (response.errorOccurred ( )) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Employee deleted from shift successfully.\n" );
    }

    private void changeShiftType(LocalDate date, int shift) throws SQLException {
        menuPrinter.print ( "choose shift type you would like to change: " );
        String shiftType = menuPrinter.getShifTypes ();
        Response response = facadeService.changeShiftType ( date, shift, shiftType );
        if (response.errorOccurred ( )) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Shift type changed successfully." );
    }

    private String createShiftType(int type) throws SQLException {
        String shiftType;
        if(type == 0)
            shiftType = "morningShift";
        else if(type == 1)
            shiftType = "eveningShift";
        else {
            menuPrinter.print ( "Write the shift type name: " );
            shiftType = menuPrinter.getString ( );
        }
        HashMap<String, Integer> manning = new HashMap<> (  );
        String role = menuPrinter.roleMenu_withoutManagers ();
        while (role != null)
        {
            menuPrinter.print ( "Write the num of employees you would like in this role: " );
            manning.put ( role, ( menuPrinter.getInt () ) );
            role = menuPrinter.roleMenu_withoutManagers ();
        }
        Response response = facadeService.createShiftType ( shiftType, manning );
        if (response.errorOccurred ( )) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return null;
        }
        menuPrinter.print ( "Shift type added successfully." );
        return shiftType;
    }

    private void getShiftType() {
        menuPrinter.print ( "Choose the shift type you would like to display: " );
        String shiftType = menuPrinter.getShifTypes (  );
        ResponseT<HashMap<Role, Integer>> manning = facadeService.getShiftType(shiftType);
        if(manning.errorOccurred ())
        {
            menuPrinter.print ( manning.getErrorMessage () );
            return;
        }
        for( Map.Entry<Role, Integer> entry : manning.value.entrySet () )
        {
            menuPrinter.print ( entry.getKey ().name () + " - " + entry.getValue () );
        }
        int choice = menuPrinter.shiftTypeMenu();
        handleShiftTypeMenuChoice(shiftType, choice);
    }



    private boolean getWeeklyShiftSchedule() {
        boolean  dateLegal = false;
        LocalDate date = menuPrinter.schedule();
        while (!dateLegal) {
            if (date == null)
                return false;
            if(!date.getDayOfWeek ().equals ( DayOfWeek.SUNDAY )){
                menuPrinter.print ( "The selected date is not sunday.\n try again." );
                date = menuPrinter.schedule ();
            }
            else
                dateLegal = true;
        }
        ResponseT<FacadeWeeklyShiftSchedule> schedule = facadeService.getWeeklyShiftSchedule ( date );
        if(schedule.errorOccurred ())
        {
            menuPrinter.print ( schedule.getErrorMessage () );
            return false;
        }
        String s = stringConverter.convertWeeklyShiftSchedule ( schedule.value );
        menuPrinter.print(s);
        return true;
    }

    private boolean login(boolean first) throws SQLException {
        String id = menuPrinter.loginID ( );
        String role = menuPrinter.roleMenu ();
        int choice;
        if(role != null) {
            ResponseT<FacadeEmployee> employee = facadeService.login ( id, Role.valueOf (role) );
            if(employee.errorOccurred ())
            {
                menuPrinter.print ( employee.getErrorMessage () );
                return false;
            }
            if(employee.value.getRole ().equals ( Role.humanResourcesManager )) {
                if(first) {
                    createShiftTypes ( );
                    menuPrinter.print ( "For continuing you have to create logistics manager account" );
                    FacadeEmployee logisticsManager = menuPrinter.getEmployeeDetails ( Role.logisticsManager.name () );
                    while (logisticsManager == null) {
                        logisticsManager = menuPrinter.createManagerAccountMenu ( );
                    }
                    Response response = facadeService.addEmployee ( logisticsManager );
                    while(response.errorOccurred ( )) {
                        menuPrinter.print ( response.getErrorMessage ( ) );
                        menuPrinter.print ( "For continuing you have to create logistics manager account" );
                        logisticsManager = menuPrinter.getEmployeeDetails ( Role.logisticsManager.name ( ) );
                        while (logisticsManager == null) {
                            logisticsManager = menuPrinter.createManagerAccountMenu ( );
                        }
                        response = facadeService.addEmployee ( logisticsManager );
                    }
                }
                else
                    printAlerts();
                choice = menuPrinter.managerMenu ( );
                handleManagerChoice ( choice, first );
            } else if(role.equals ( Role.logisticsManager )){
                LogisticsManagerMenu.getInstance ().mainMenu ();
                logout ();
                while(!login (false));
            } else if(role.equals ( Role.storeKeeper )) {
                choice = menuPrinter.storeKeeperMenu ();
                handleStoreKeeperChoice ( choice );
            } else if(role.equals ( Role.branchManager ) || role.equals ( Role.branchManagerAssistant )) {
                //מנהל חנות
            } else {
                choice = menuPrinter.simpleEmployeeMenu ();
                handleSimpleEmployeeChoice ( choice );
            }
            return true;
        }
        else
            return false;
    }

    private void printAlerts() throws SQLException {
        for( EmployeeNotification alert: alerts.get ( Role.humanResourcesManager ))
        {
            menuPrinter.print ( "Alert: " + alert.getContent () );
            facadeService.deleteAlert(alert);
        }
    }

    private void handleStoreKeeperChoice(int choice) throws SQLException {
        if(choice == 7) {
            //go to store keeper menu
        }
        if(choice == 8)
            choice --;
        handleSimpleEmployeeChoice ( choice );
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
            if(facadeShift.errorOccurred ())
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
            if(facadeShift.errorOccurred ())
            {
                menuPrinter.print ( facadeShift.getErrorMessage () );
                return;
            }
            String s = stringConverter.convertShift ( facadeShift.value );
            menuPrinter.print ( s );
            facadeShift = facadeService.getShift ( date, 1 );
            if(facadeShift.errorOccurred ())
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
        if(constraints.errorOccurred ())
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
        ResponseT<HashMap<LocalDate,FacadeConstraint>> constraints = facadeService.getConstraints ( id );
        if(constraints.errorOccurred ())
        {
            menuPrinter.print ( constraints.getErrorMessage () );
            return;
        }
        menuPrinter.print ( "Constraints:\n" );
        for ( Map.Entry<LocalDate, FacadeConstraint> entry: constraints.value.entrySet ()) {
            menuPrinter.print ( stringConverter.convertConstraint ( entry.getValue () ) );
        }
    }

    private boolean logout()  {
        Response response = facadeService.logout();
        if(response.errorOccurred ()) {
            menuPrinter.print ( response.getErrorMessage ( ) );
            return false;
        }
        menuPrinter.print ( "logged out successfully.");
        return true;
    }

    private void giveConstraint() throws SQLException {
        menuPrinter.print ( "Write the constraint detail:\n" );
        LocalDate date = menuPrinter.dateMenu ();
        if(date == null)
            return;
        int shift = menuPrinter.getShiftNum ();
        if(shift > 2 || shift < 0) {
            menuPrinter.printChoiceException ( );
            return;
        }
        menuPrinter.print ( "reason: " );
        String reason = menuPrinter.getString();
        Response r = facadeService.giveConstraint ( date, shift, reason );
        if (r.errorOccurred ()) {
            menuPrinter.print ( r.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Constraint added successfully." );
    }

    private void deleteConstraint () throws SQLException {
        LocalDate date = menuPrinter.dateMenu ();
        if(date == null)
            return;
        int shift = menuPrinter.getShiftNum ();
        Response r = facadeService.deleteConstraint (date, shift);
        if (r.errorOccurred ()) {
            menuPrinter.print ( r.getErrorMessage ( ) );
            return;
        }
        menuPrinter.print ( "Constraint deleted successfully.\n" );
    }

    private void addEmployee() throws SQLException {
        menuPrinter.print ( "Write the new employee details:" );
        String role = menuPrinter.roleMenu ( );
        if(role == null)
            return;
        FacadeEmployee facadeEmployee = menuPrinter.getEmployeeDetails ( role );
        Response response;
        if(role.equals ( "driverC" ) || role.equals ( "driverC1" ))
        {
            System.out.println("reached here 690");
            menuPrinter.print ( "name:" );
            String name = menuPrinter.getString ();
            response = facadeService.addDriver ( facadeEmployee, name );
        }
        else
            response = facadeService.addEmployee ( facadeEmployee );
        if(response.errorOccurred ())
        {
            menuPrinter.print ( response.getErrorMessage () );
            return;
        }
        menuPrinter.print ( "Employee added successfully." );
    }

    private void removeEmployee(String ID)  {
        Response response = facadeService.removeEmployee ( ID );
        if(response.errorOccurred ())
        {
            menuPrinter.print ( response.getErrorMessage () );
            return;
        }
        menuPrinter.print ( "Employee deleted successfully." );
    }

    private void updateBankAccount(String ID) {
        menuPrinter.print ( "Write account number: " );
        int accountNum = menuPrinter.getInt ();
        menuPrinter.print ( "Write bank branch: " );
        int bankBranch = menuPrinter.getInt ();
        menuPrinter.print ( "Write bank name: " );
        String bank = menuPrinter.getString ();
        Response response = facadeService.updateBankAccount ( ID, accountNum, bankBranch, bank );
        if(response.errorOccurred ())
        {
            menuPrinter.print ( response.getErrorMessage () );
            return;
        }
        menuPrinter.print ( "Bank account changed successfully." );
    }

    private void updateTermsOfEmployee(String ID) throws SQLException {
        menuPrinter.print ( "Write salary: " );
        int salary = menuPrinter.getInt ();
        menuPrinter.print ( "Write education fund: " );
        int educationFund = menuPrinter.getInt ();
        menuPrinter.print ( "Write sick days: " );
        int sickDays = menuPrinter.getInt ();
        menuPrinter.print ( "Write days off: " );
        int daysOff = menuPrinter.getInt ();
        Response response = facadeService.updateTermsOfEmployee ( ID, salary, educationFund, sickDays, daysOff );
        if(response.errorOccurred ())
        {
            menuPrinter.print ( response.getErrorMessage () );
            return;
        }
        menuPrinter.print ( "Terms of employment changed successfully." );
    }

    private void getEmployee() {
        ResponseT<FacadeEmployee> employee = facadeService.getLoggedIn();
        if (employee.errorOccurred ())
        {
            menuPrinter.print ( employee.getErrorMessage () );
            return;
        }
        String s = stringConverter.convertEmployee ( employee.value );
        menuPrinter.print ( s );
    }

    private void getWeeklyShiftScheduleManager() throws SQLException {
        boolean succeed = getWeeklyShiftSchedule ();
        if(succeed)
            editSchedule ();
    }

    private void editSchedule() throws SQLException {
        menuPrinter.print ( "Would you like to edit one of the shifts? n\\y" );
        char c = menuPrinter.getChar ( );
        if (c == 'y') {
            LocalDate date = menuPrinter.getShiftDate ( );
            int shift = menuPrinter.getOneShiftNum ( );
            int choice = menuPrinter.scheduleManagerMenu ( );
            handleManagerScheduleChoice (date, shift, choice );
        }
    }

    private void getEmployeeByManager() throws SQLException {
        menuPrinter.print ( "Write the ID of the employee you would like to display: " );
        String ID = menuPrinter.getString ( );
        ResponseT<FacadeEmployee> employee = facadeService.getEmployee ( ID );
        if (employee.errorOccurred ())
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

    private void updateEmployee(FacadeEmployee facadeEmployee, int choice) throws SQLException {
        switch (choice){
            case 1:
                removeEmployee (facadeEmployee.getID ());
                break;
            case 2:
                updateBankAccount (facadeEmployee.getID ());
                break;
            case 3:
                updateTermsOfEmployee (facadeEmployee.getID ());
                break;
            default:
                menuPrinter.printChoiceException ();
        }
    }

    private void getEmployeeList() {
        ResponseT<HashMap<Role, List<String>>> employees = facadeService.getEmployees ( );
        if(employees.errorOccurred ())
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

    public void addAnAlert(Role role, Notification alert) throws SQLException {
        ResponseT<Integer> id = facadeService.addAlert(role, alert);
        if(id.errorOccurred ()) {
            menuPrinter.print ( id.getErrorMessage ( ) );
            return;
        }
        if(!alerts.containsKey ( role ))
            alerts.put ( role, new ArrayList<> (  ) );
        alerts.get ( role ).add ( new EmployeeNotification ( id.value, role.name (), alert.getContent () ));
    }
}
