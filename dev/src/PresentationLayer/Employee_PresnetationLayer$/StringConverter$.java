package PresentationLayer.Employee_PresnetationLayer$;

import BusinessLayer.EmployeePackage.Facade.FacadeObject.*;


public class StringConverter$ {
    private static StringConverter$ instance = null;

    private StringConverter$(){}

    public static StringConverter$ getInstance(){
        if(instance == null)
            instance = new StringConverter$();
        return instance;
    }
    private String convertBankAccountInfo( FacadeBankAccountInfo facadeBankAccountInfo){
         return "Account Number: " + facadeBankAccountInfo.getAccountNumber()+"\n"
                + "Bank branch: " + facadeBankAccountInfo.getBankBranch ()+"\n"
                + "Bank name: " + facadeBankAccountInfo.getBank();
    }

    public String convertConstraint(FacadeConstraint facadeConstraint){
        String constraint="Date: "+ facadeConstraint.getDate().toString();
        if(facadeConstraint.isMorningShift() & facadeConstraint.isEveningShift()){
            constraint += " Shift: All day\n" + "Reason: "+facadeConstraint.getReason();
        }
        else if(facadeConstraint.isMorningShift()){
            constraint += " Shift: Morning\n" + "Reason: "+facadeConstraint.getReason();
        }
        else if(facadeConstraint.isEveningShift()){
            constraint += " Shift: Evening\n" + "Reason: "+facadeConstraint.getReason();
        }
        return constraint;
    }

    public String convertEmployee(FacadeEmployee facadeEmployee){
        return "ID: "+ facadeEmployee.getID()+"\n"
                + "Role: "+ facadeEmployee.getRole()+"\n"
                + "Transaction date: " + facadeEmployee.getTransactionDate().toString() +"\n" +
                "Bank account info: " + convertBankAccountInfo ( facadeEmployee.getFacadeBankAccountInfo () ) + "\n" +
                "Terms of employment: " + convertTermsOfEmployment ( facadeEmployee.getFacadeTermsOfEmployment () );
    }

    public String convertShift(FacadeShift facadeShift){
        String isFull;
        if(facadeShift.isMissing()){isFull="No";}
        else{isFull="Yes";}
        String manning="";
        if(!facadeShift.getManning ().containsKey ( "shiftManager" ) || facadeShift.getManning ().get ( "shiftManager").isEmpty ())
            isFull += "\nNOTE: THERE IS NO SHIFT MANAGER IN THIS SHIFT!";
        for (String role: facadeShift.getManning().keySet()) {
            manning+= role+":\n ";
            for (String ID : facadeShift.getManning().get(role)) {
                manning+=ID+"\n ";
            }
            manning  += "\n";
        }
        return "Shift type: " + facadeShift.getType()+"\n"
                + "Shift staffing:\n "+manning
                + "A fully staffed shift: " + isFull;
    }

    private String convertTermsOfEmployment(FacadeTermsOfEmployment facadeTermsOfEmployment){
        return "Salary: "+facadeTermsOfEmployment.getSalary()+"\n"
                + "Education fund: "+ facadeTermsOfEmployment.getEducationFund()+"\n"
                + "Sick days: "+ facadeTermsOfEmployment.getSickDays() +"\n"
                + "Days off: "+ facadeTermsOfEmployment.getDaysOff();
    }

    public String convertWeeklyShiftSchedule(FacadeWeeklyShiftSchedule facadeWeeklyShiftSchedule){
        String startOfWeek = facadeWeeklyShiftSchedule.getDate().toString();
        String endOfWeek = facadeWeeklyShiftSchedule.getDate().plusWeeks(1).toString();
        FacadeShift [][]shifts = facadeWeeklyShiftSchedule.getShifts();
        String list="";
        for(int i =0 ; i<7; i++){
            if(shifts[i][0] == null && shifts[i][1] != null)
            {
                list+= "Day: "+shifts[i][1].getDate().getDayOfWeek()+"("+shifts[i][1].getDate().toString()+"):\n"
                        + convertShift(shifts[i][1])+"\n"+"\n";
            }
            else if(shifts[i][1] == null && shifts[i][0] != null)
            {
                list+= "Day: "+shifts[i][0].getDate().getDayOfWeek()+"("+shifts[i][0].getDate().toString()+"):\n"
                        + convertShift(shifts[i][0])+"\n"+"\n";
            }
            else if(shifts[i][0] != null)
                list+= "Day: "+shifts[i][0].getDate().getDayOfWeek()+"("+shifts[i][0].getDate().toString()+"):\n"
                    + convertShift(shifts[i][0])+"\n"+convertShift(shifts[i][1]) +"\n";
        }

        return "Week: "+ startOfWeek+" - "+ endOfWeek+"\n" + list;
    }
}
