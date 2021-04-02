package Employees.business_layer.facade.facadeObject;

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
}
