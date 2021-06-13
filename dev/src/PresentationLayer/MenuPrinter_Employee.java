package PresentationLayer;

import BusinessLayer.EmployeePackage.ShiftPackage.ShiftTypes;
import ServiceLayer.FacadeObjects.FacadeBankAccountInfo;
import ServiceLayer.FacadeObjects.FacadeEmployee;
import ServiceLayer.FacadeObjects.FacadeTermsOfEmployment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class MenuPrinter_Employee {

BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

    //Scanner sc = new Scanner ( System.in );

    public int uploadProgram(){
        //sc.useDelimiter ( "\n" );
        System.out.println ("choose the option you'd like:\n1.upload a program with an existing data.\n2.upload a program without an existing data." );
        return getInt ();
    }

    public char uploadClean(){
        System.out.print ("Are you a manager in Super-Lee? y/n\nyour answer:" );
        return getChar ();
    }

    public String loginID(){
        System.out.println ("To login type you Id:" );
        return getString ();
    }

    public String roleMenu(){
        System.out.println ("Choose role:\n1.branch manager\n2.branch manager assistant\n" +
                "3.human resources manager\n4.cashier\n5.guard\n6.usher\n7.store keeper\n8.shift manager\n9.driver - license c\n10.driver - license c1\n11.logistics manager" );
        int choice =  getInt ();
        switch (choice){
            case 1:
                return "branchManager";
            case 2:
                return "branchManagerAssistant";
            case 3:
                return "humanResourcesManager";
            case 4:
                return "cashier";
            case 5:
                return "guard";
            case 6:
                return "usher";
            case 7:
                return "storeKeeper";
            case 8:
                return "shiftManager";
            case 9:
                return "driverC";
            case 10:
                return "driverC1";
            case 11:
                return "logisticsManager";
            default:
                System.out.println ("choice is illegal.");
                return null;
        }
    }

    public String roleMenu_withoutManagers(){
        System.out.println ("Choose role:\n1.cashier\n2.guard\n3.usher\n4.store keeper\n5.shift manager\n6.driver - license c\n7.driver - license c1\n8.back" );
        int choice =  getInt ();
        switch (choice){
            case 1:
                return "cashier";
            case 2:
                return "guard";
            case 3:
                return "usher";
            case 4:
                return "storeKeeper";
            case 5:
                return "shiftManager";
            case 6:
                return "driverC";
            case 7:
                return "driverC1";
            case 8:
                return null;
            default:
                System.out.println ("choice is illegal.");
                return null;
        }
    }

    public int managerMenu(){
        System.out.println ("Choose your next action:\n" +
                "1.Create weekly shift schedule\n" +
                "2.Show weekly shift schedule\n" +
                "3.Show an existing shift type\n" +
                "4.Create a new Shift type\n" +
                "5.Get a list of employees ordered by role\n" +
                "6.Get employee information\n" +
                "7.Get employee constraints\n" +
                "8.Add a new employee to the system\n" +
                "9.Logout" );
        return getInt ();
    }

    public int simpleEmployeeMenu(){
        System.out.println ("Choose your next action:\n" +
                "1.Show a weekly shift schedule\n" +
                "2.Show a single shift\n" +
                "3.Add a new constraint\n" +
                "4.delete an existing constraint\n" +
                "5.Show my employee card\n" +
                "6.Show constraints\n" +
                "7.Logout" );
        return getInt ();
    }


    public FacadeEmployee createManagerAccountMenu() {
        String role;
        System.out.println ( "Create account:\n" +
                "choose role:\n1.branch manager\n2.branch manager assistant\n3.human resources manager" );
        int input = getInt ();
        if(input == 1)
            role = "branchManager";
        else if (input == 2)
            role = "branchManagerAssistant";
        else if(input == 3)
            role = "humanResourcesManager";
        else{
            printChoiceException ();
            return null;
        }
        return getEmployeeDetails ( role );
    }

    public FacadeEmployee getEmployeeDetails(String role){
        int accountNumber, bankBranch, salary, educationFund, sickDays, daysOff;
        String ID, bank;
        FacadeBankAccountInfo bankAccountInfo;
        FacadeTermsOfEmployment termsOfEmployment;
        LocalDate transactionDate;
        System.out.println ( "ID: " );
        ID = getString ();
        System.out.println ( "transaction date:");
        transactionDate = dateMenu ();
        System.out.print ("bank account info:\nbank: " );
        while(Character.isDigit((bank = getString ()).charAt ( 0 ))){
            print ( "Illegal bank name." );
            System.out.print ("bank account info:\nbank: " );
        }
        System.out.print ("bank branch: " );
        bankBranch = getInt ();
        System.out.print ("bank account number: " );
        accountNumber = getInt ();
        bankAccountInfo = new FacadeBankAccountInfo (accountNumber, bankBranch, bank );
        System.out.print ("terms of employment:\nsalary: " );
        salary = getInt ();
        System.out.print ("education fund: " );
        educationFund = getInt ();
        System.out.print ("sick days: " );
        sickDays = getInt ();
        System.out.print ("days off: " );
        daysOff = getInt ();
        termsOfEmployment = new FacadeTermsOfEmployment ( salary, educationFund, sickDays, daysOff );
        return new FacadeEmployee ( role, ID, transactionDate, bankAccountInfo, termsOfEmployment );
    }

    public LocalDate dateMenu()
    {
        int input;
        System.out.print ( "day:" );
        input = getInt ();
        int day, month;
        while (input > 31 || input < 1) {
            System.out.println ( "day is illegal" );
            System.out.print ( "day:" );
            input = getInt ();
        }
        day = input;
        System.out.print ("month:" );
        input = getInt();
        while (input > 12 || input < 1) {
            System.out.println ( "month is illegal" );
            System.out.print ("month:" );
            input = getInt();
        }
        month = input;
        System.out.print ("year:" );
        input = getInt ();
        while (input > 2021 || input < 1961) {
            System.out.println ( "year is illegal" );
            System.out.print ("year:" );
            input = getInt ();
        }
        return LocalDate.of (input, month, day);
    }

    public LocalDate schedule() {
        System.out.println ("First date of the week:" );
        return dateMenu ();
    }

    public LocalDate getShiftDate() {
        System.out.println ("Shift date:" );
        return dateMenu ();
    }

    public int getShiftNum(){
        System.out.print ( "Type 0 for morning shift, 1 for evening shift and 2 for both: " );
        return getInt ();
    }

    public int getOneShiftNum(){
        System.out.print ( "Type 0 for morning shift and 1 for evening shift: " );
        return getInt ();
    }

    public void print(String s) {
        System.out.println ( s );
    }

    public String idGetter() {
        System.out.println ("Write the ID of an employee you would like to add to the shift or 0 if you are done: ");
        return getString();
    }

    public int scheduleManagerMenu() {
        System.out.println ("Choose the option you would like:\n" +
                "1.Change shift by creating a new one\n" +
                "2.Add an employee to shift\n" +
                "3.Delete an employee from shift\n" +
                "4.Change shift type\n" +
                "5.Back" );
        return getInt ();
    }

    public int createWeeklyShiftSchedule() {
        System.out.println ("Choose an option: \n" +
                "1. Creating a shift schedule manually\n" +
                "2. Getting a system recommendation." );
        return getInt ();
    }

    public char getChar() {
        try{
            return sc.readLine().charAt ( 0 );
        }
        catch (Exception e){
            System.out.println("Something went wrong");
            return ' ';
        }
    }
    public String getString() {
        try {
            return sc.readLine();
        } catch (Exception e) {
            System.out.println("Something went wrong");
            return "";
        }
    }

    public int getInt(){
        int out;
        try {
            out =  Integer.parseInt(sc.readLine ( ));
            return out;
        }
        catch (Exception e) {
            System.out.println ( "Not a number, please try again." );
            return getInt();
        }
    }

    public void printChoiceException() {
        System.out.println ("Choice is illegal.");
    }

    public int getEmployeeMenu() {
        System.out.println ("Choose the option you would like:\n" +
                "1.Delete employee\n" +
                "2.Update employee bank account\n" +
                "3.Update employee terms of employment\n" +
                "4.Back" );
        return getInt ();
    }

    public int shiftTypeMenu() {
        System.out.println ("Choose the option you would like:\n" +
                "1.Update an existing role manning\n" +
                "2.Add a new role manning\n" +
                "3.Delete an existing role from shift\n" +
                "4.Back" );
        return getInt ();
    }

    public String chooseShiftType(LocalDate date, int shiftNum) {
        System.out.println ("Choose the shift type you would like for " + date + "in " + shiftNum + " or type 0 to create a new one:\n");
        return getShifTypes ();
    }

    public String getShifTypes()
    {
        String[] shiftTypes = ShiftTypes.getInstance ().getShiftTypes();
        int i = 1;
        for ( String shift : shiftTypes )
        {
            System.out.println ("" + i++ +"." + shift );
        }
        int choice = getInt();
        if(choice == 0)
            return "new";
        if(choice > shiftTypes.length || choice < 0)
            return null;
        return shiftTypes[choice - 1];
    }

    public int storeKeeperMenu() {
        System.out.println ("Choose your next action:\n" +
                "1.Show a weekly shift schedule\n" +
                "2.Show a single shift\n" +
                "3.Add a new constraint\n" +
                "4.delete an existing constraint\n" +
                "5.Show my employee card\n" +
                "6.Show constraints\n" +
                "7.Store management\n" +
                "8.Logout" );
        return getInt ();
    }
}
