package DataAccessLayer.DalObjects.EmployeeObjects;

public class DalBankBranch {
    private String employeeId;
    private String bank;
    private int bankBranch;
    private int accountNumber;


    public DalBankBranch( String employeeId, String bank, int bankBranch, int accountNumber){
        this.employeeId = employeeId;
        this.bank = bank;
        this.bankBranch = bankBranch;
        this.accountNumber = accountNumber;
    }
// getters

    public String getEmployeeId() {
        return employeeId;
    }

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

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

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
