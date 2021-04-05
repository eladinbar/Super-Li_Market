package Employees.business_layer.facade.facadeObject;

import Employees.business_layer.Employee.BankAccountInfo;

public class FacadeBankAccountInfo implements FacadeObject{
    private int accountNumber;
    private int bankBranch;
    private String bank;

    public FacadeBankAccountInfo(int accountNumber, int bankBranch, String bank)
    {
        this.accountNumber = accountNumber;
        this.bankBranch = bankBranch;
        this.bank = bank;
    }

    public FacadeBankAccountInfo(BankAccountInfo bankAccountInfo)
    {
        accountNumber = bankAccountInfo.getAccountNumber();
        bankBranch = bankAccountInfo.getBankBranch();
        bank = bankAccountInfo.getBank();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getBankBranch() {
        return bankBranch;
    }

    public String getBank() {
        return bank;
    }
}
