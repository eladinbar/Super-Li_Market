package ServiceLayer.objects;

public class personCard {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String id;
    protected String phone;

    public personCard(String firstName, String lastName, String email, String id, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.phone = phone;
    }

    public personCard(BusinessLayer.supplierPackage.personCard pc){
        this.email=pc.getEmail();
        this.firstName=pc.getFirstName();
        this.lastName=pc.getLastName();
        this.id=pc.getId();
        this.phone=pc.getPhone();
    }

    @Override
    public String toString() {
        return "personCard{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
