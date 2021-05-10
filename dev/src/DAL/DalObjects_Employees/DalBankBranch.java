package DAL.DalObjects_Employees;

public class DalBankBranch {
    private String bank;
    private int bankBranch;
    private int accountNumber;


    public DalBankBranch( String bank, int bankBranch, int accountNumber){
        this.bank = bank;
        this.bankBranch = bankBranch;
        this.accountNumber = accountNumber;
    }
// getters

    public String getBank() {
        return bank;
    }

    public int getBankBranch() {
        return bankBranch;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

// setters

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setBankBranch(int bankBranch) {
        this.bankBranch = bankBranch;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }


}
