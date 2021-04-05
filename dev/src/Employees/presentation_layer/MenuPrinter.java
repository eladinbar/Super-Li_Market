package Employees.presentation_layer;

import Employees.business_layer.Employee.BankAccountInfo;
import Employees.business_layer.Employee.Role;
import Employees.business_layer.facade.facadeObject.FacadeBankAccountInfo;
import Employees.business_layer.facade.facadeObject.FacadeEmployee;
import Employees.business_layer.facade.facadeObject.FacadeTermsOfEmployment;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuPrinter {
    public int uploadProgram(){
        System.out.println ("choose the option you'd like:\n1.upload a program with an existing data.\n2.upload a program without an existing data." );
        Scanner sc = new Scanner ( System.in );
        return sc.nextInt ();
    }

    public char uploadClean(){
        System.out.println ("Are you a manager in Super-Lee? y/n\nyour answer:" );
        Scanner sc = new Scanner ( System.in );
        return sc.next().charAt ( 0 );
    }

    public int uploadData(){
        return 0;
    }

    public int loginID(){
        System.out.println ("To login type you Id:\n" );
        Scanner sc = new Scanner ( System.in );
        return sc.nextInt ();
    }

    public Role loginRole(){
        System.out.println ("Choose your role:\n1.branch manager\n2.banch manager assistent\n" +
                "3.human resources manager\n4.cashier\n5.guard\n6.usher\n7.store keeper\n" );
        Scanner sc = new Scanner ( System.in );
        int choice =  sc.nextInt ();
        switch (choice){
            case 1:
                return Role.branchManager;
            case 2:
                return Role.branchManagerAssistent;
            case 3:
                return Role.humanResourcesManager;
            case 4:
                return Role.cashier;
            case 5:
                return Role.guard;
            case 6:
                return Role.usher;
            case 7:
                return Role.storeKeeper;
            default:
                System.out.println ("choice is illegal.");
                return null;
        }
    }

    public int managerMenu(){
        System.out.println ("Choose your next action:\n1.Create weekly shift schedule\n" +
                "2.Show weekly shift schedule\n" +
                "3.Get employee information\n" +
                "4.Add new Employee to the system\n" +
                "5.Remove employee from the system\n" +
                "6.Logout\n" );
        Scanner sc = new Scanner ( System.in );
        return sc.nextInt ();
    }

    public int simpleEmployeeMenu(){
        System.out.println ("Choose your next action:\n" +
                "1.Show weekly shift schedule\n" +
                "2.Add a new constraint\n" +
                "3.delete an existing constraint\n" +
                "4.Update an existing constraint\n" +
                "5.Logout\n" );
        Scanner sc = new Scanner ( System.in );
        return sc.nextInt ();
    }


    public FacadeEmployee createManagerAccountMenu() {
        Role role;
        int ID, accountNumber, bankBranch, salary, educationFund, sickDays, daysOff;
        String bank;
        FacadeBankAccountInfo bankAccountInfo;
        FacadeTermsOfEmployment termsOfEmployment;
        LocalDate transactionDate;
        System.out.println ( "Create your account:\n" +
                "choose your role:\n1.branch manager\n2.branch manager assistent\n3.human resources manager\n" );
        Scanner sc = new Scanner ( System.in );
        int input = sc.nextInt ( );
        switch (input) {
            case 1:
                role = Role.branchManager;
            case 2:
                role = Role.branchManagerAssistent;
            case 3:
                role = Role.humanResourcesManager;
            default:
                System.out.println ( "your choice is out of bounds." );
                role = null;
        }
        if (role == null)
            return null;
        System.out.println ( "ID: " );
        ID = sc.nextInt ( );
        System.out.println ( "\n" );
        System.out.println ( "transaction date:\nday:" );
        input = sc.nextInt ( );
        int day, month;
        if (input > 31 || input < 1) {
            System.out.println ( "day is illegal" );
            return null;
        }
        day = input;
        System.out.println ("\nmonth:" );
        input = sc.nextInt ( );
        if (input > 12 || input < 1) {
            System.out.println ( "month is illegal" );
            return null;
        }
        month = input;
        System.out.println ("\nyear:" );
        input = sc.nextInt ( );
        if (input > 2021 || input < 1961) {
            System.out.println ( "year is illegal" );
            return null;
        }
        transactionDate = LocalDate.of (input, month, day);
        System.out.println ("\nWrite your bank account info:\nbank: " );
        bank = sc.next ();
        System.out.println ("\nbank branch: " );
        bankBranch = sc.nextInt ();
        System.out.println ("\nbank account number: " );
        accountNumber = sc.nextInt ();
        bankAccountInfo = new FacadeBankAccountInfo (accountNumber, bankBranch, bank );
        System.out.println ("\nWrite your terms of employment:\nsalary: " );
        salary = sc.nextInt ();
        System.out.println ("\neducation fund: " );
        educationFund = sc.nextInt ();
        System.out.println ("\nsick days: " );
        sickDays = sc.nextInt ();
        System.out.println ("\ndays off: " );
        daysOff = sc.nextInt ();
        termsOfEmployment = new FacadeTermsOfEmployment ( salary, educationFund, sickDays, daysOff );
        return new FacadeEmployee ( role, ID, transactionDate, bankAccountInfo, termsOfEmployment );
    }
}
