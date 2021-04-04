package Employees.business_layer.Employee;

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
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBankBranch(int bankBranch) {
        this.bankBranch = bankBranch;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
