package BusinessLayer.EmployeePackage.EmployeePackage;

import BusinessLayer.EmployeePackage.EmployeeException;

public class BankAccountInfo {
    private int accountNumber;
    private int bankBranch;
    private String bank;

    public BankAccountInfo( int accountNumber, int bankBranch, String bank ){
        this.accountNumber = accountNumber;
        this.bankBranch = bankBranch;
        this.bank = bank;
    }

//Getters:
    public int getAccountNumber() {
        return accountNumber;
    }

    public int getBankBranch() {
        return bankBranch;
    }

    public String getBank() {
        return bank;
    }

//Setters:
    public void setAccountNumber(int accountNumber) throws EmployeeException {
        if(accountNumber<0){ throw new EmployeeException("Invalid account number entered");}
        this.accountNumber = accountNumber;
    }

    public void setBankBranch(int bankBranch) throws EmployeeException {
        if(bankBranch<0){throw new EmployeeException("Invalid branch number entered");}
        this.bankBranch = bankBranch;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
