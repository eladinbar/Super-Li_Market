package DataAccessLayer.DalObjects.EmployeeObjects;

public class DalShiftType {
    private int amount;
    private String type;
    private String role;

    public DalShiftType(int amount, String type, String role){
        this.amount =amount;
        this.type = type;
        this.role = role;
    }

    // getters


    public int getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getRole() {
        return role;
    }
// setters
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
