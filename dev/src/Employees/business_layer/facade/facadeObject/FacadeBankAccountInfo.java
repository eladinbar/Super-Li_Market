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
        accountNumber = bankAccountInfo.getAccountInfo();
        bankBranch = bankAccountInfo.getBankBranch();
        bank = bankAccountInfo.getBank();
    }
}
