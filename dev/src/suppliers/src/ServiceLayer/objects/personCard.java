package ServiceLayer.objects;

public class personCard {
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private String phone;

    public personCard(String firstName, String lastName, String email, String id, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.phone = phone;
    }
}