package Employees.Test;

import Employees.EmployeeException;
import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.facade.facadeObject.FacadeBankAccountInfo;
import Employees.business_layer.facade.facadeObject.FacadeEmployee;
import Employees.business_layer.facade.facadeObject.FacadeTermsOfEmployment;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerTest {
        //private EmployeeException expected;
        private EmployeeController employeeController;
        private FacadeEmployee manager;
        private FacadeEmployee employee;

        @BeforeEach
        public void setEmployeeController() {
            employeeController = EmployeeController.getInstance ();
            FacadeBankAccountInfo ManagerAccountInfo = new FacadeBankAccountInfo ( 123, 11, "Hpoalim" );
            FacadeTermsOfEmployment termsOfManager = new FacadeTermsOfEmployment ( 5500, 1212, 10, 30 );
            manager = new FacadeEmployee ( "humanResourcesManager", "315000000", LocalDate.now ( ), ManagerAccountInfo, termsOfManager );
            try {
                employeeController.addManager ( manager );
            } catch (EmployeeException e) {
                System.out.println ( e.getMessage ( ) );
            }
            FacadeBankAccountInfo employeeAccountInfo = new FacadeBankAccountInfo ( 456, 01, "Hpoalim" );
            FacadeTermsOfEmployment termsOfEmployment = new FacadeTermsOfEmployment ( 8000, 1212, 10, 40 );
            employee = new FacadeEmployee ( "usher", "994000000", LocalDate.now ( ), employeeAccountInfo, termsOfEmployment );
        }

        @AfterEach
        public void logout() {
            try {
                employeeController.logout ( );
            } catch (EmployeeException e) {
            }

        }

        @Test
        public void login_LoggedInNotNull() {
            try {
                employeeController.login ( "315000000", "humanResourcesManager" );
                employeeController.login ( "994000000", "usher" );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                assertEquals ( "Two users cannot be logged in at the same time", e.getMessage ( ) );
            }
        }

        @Test
        public void login_IdNotExist() {
            try {
                employeeController.login ( "002000000", "usher" );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                assertEquals ( "There is no record of this employee in the system", e.getMessage ( ) );
            }
        }

        @Test
        public void login_RoleNotMatchId() {
            try {
                employeeController.login ( manager.getID ( ), "guard" );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                assertEquals ( "Id does not match to the role", e.getMessage ( ) );
            }
        }

        @Test
        public void login_NotEmployee() {
            try {
                employeeController.login ( manager.getID ( ), manager.getRole ( ) );
                employeeController.addEmployee ( employee );
                employeeController.getEmployee ( employee.getID ( ) ).setEmployed ( false );
                employeeController.login ( employee.getID (), employee.getRole () );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                assertEquals ( "The employee is not employed ", e.getMessage ( ) );
            }
        }

        @After
        public void setEmployedBack() {
            try {
                employeeController.login ( manager.getID (), manager.getRole () );
                employeeController.getEmployee ( employee.getID ( ) ).setEmployed ( true );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                System.out.println ( e.getMessage ( ) );
            }
        }

        @Test
        public void removeEmployee_ManagerDeleteHimself() {
            try {
                employeeController.login ( manager.getID ( ), manager.getRole ( ) );
                employeeController.removeEmployee ( manager.getID ( ) );
                assertNull ( employeeController.getLoggedIn ( ) );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                System.out.println ( e.getMessage ( ) );
            }
        }

        @Test
        public void removeEmployee_NotManager() {
            try {
                employeeController.login ( manager.getID (), manager.getRole () );
                employeeController.addEmployee ( employee );
                employeeController.logout ();
                employeeController.login ( employee.getID ( ), employee.getRole ( ) );
                employeeController.removeEmployee ( manager.getID () );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                assertEquals ( "Only an administrator can perform this operation", e.getMessage ( ) );
            }
        }

        @Test
        public void addEmployee_invalidId() {
            FacadeBankAccountInfo employeeAccountInfo = new FacadeBankAccountInfo ( 452, 01, "Benleomi" );
            FacadeTermsOfEmployment termsOfEmployment = new FacadeTermsOfEmployment ( 6000, 3232, 10, 30 );
            FacadeEmployee employee2 = new FacadeEmployee ( "shiftManager", "000000A89", LocalDate.now ( ), employeeAccountInfo, termsOfEmployment );
            try {
                employeeController.login ( manager.getID (), manager.getRole () );
                employeeController.addEmployee ( employee2 );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                assertEquals ( "An invalid ID was entered ", e.getMessage ( ) );
            }
        }

        @Test
        public void addEmployee_AlreadyExist() {
            try {
                employeeController.login ("315000000", "humanResourcesManager" );
                employeeController.addEmployee ( employee );
                employeeController.addEmployee ( employee );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                assertEquals ( "Employee already added to the system", e.getMessage ( ) );
            }
        }

        @Test
        public void addManager_AlreadyExist() {
            try {
                employeeController.addManager ( employee );
                employeeController.addManager ( employee );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                assertEquals ( "Manager already added to the system", e.getMessage ( ) );
            }
        }

        @Test
        public void giveConstraint() {

            LocalDate invalidDate = LocalDate.now ( ).plusWeeks ( 1 );
            try {
                employeeController.login ( manager.getID (), manager.getRole ());
                employeeController.addEmployee ( employee );
                employeeController.logout ();
                employeeController.login ( employee.getID (), employee.getRole () );
                employeeController.giveConstraint ( invalidDate, 0, "doctor appointment" );
                fail ( "Exception not thrown" );
            } catch (EmployeeException e) {
                assertEquals ( "A constraint can be filed up to two weeks in advance", e.getMessage ( ) );
            }
        }


        @Test
        public void isExist_failed() {
            assertFalse ( employeeController.isExist ( "usher", "111111111" ) );

        }

        @Test
        void isExist_succeed() {
            assertFalse ( employeeController.isExist ( manager.getRole ( ), manager.getID ( ) ) );
        }
    }
