package Employees.presentation_layer;

import Employees.business_layer.Shift.ShiftTypes;
import Employees.business_layer.facade.facadeObject.FacadeBankAccountInfo;
import Employees.business_layer.facade.facadeObject.FacadeEmployee;
import Employees.business_layer.facade.facadeObject.FacadeTermsOfEmployment;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuPrinter {

    Scanner sc = new Scanner ( System.in );

    public int uploadProgram(){
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
                "3.human resources manager\n4.cashier\n5.guard\n6.usher\n7.store keeper\n8.shift manager\n9.back" );
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
        ID = sc.next ( );
        System.out.println ( "transaction date:");
        transactionDate = dateMenu ();
        System.out.print ("bank account info:\nbank: " );
        bank = sc.next ();
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
        if (input > 31 || input < 1) {
            System.out.println ( "day is illegal" );
            return null;
        }
        day = input;
        System.out.print ("month:" );
        input = sc.nextInt ( );
        if (input > 12 || input < 1) {
            System.out.println ( "month is illegal" );
            return null;
        }
        month = input;
        System.out.print ("year:" );
        input = getInt ();
        if (input > 2021 || input < 1961) {
            System.out.println ( "year is illegal" );
            return null;
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
        return sc.next ();
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
        return sc.next ().charAt ( 0 );
    }

    public String getString() {
        return sc.next ();
    }

    public int getInt(){
        while(true)
        {
            try{
                return sc.nextInt ();
            }catch (Exception e){
                System.out.println ("Not a number, please try again." );
            }
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
        int choice = sc.nextInt ();
        if(choice == 0)
            return "new";
        if(choice > shiftTypes.length || choice < 0)
            return null;
        return shiftTypes[choice - 1];
    }
}
