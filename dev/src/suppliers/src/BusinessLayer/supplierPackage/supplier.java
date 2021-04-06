package BusinessLayer.supplierPackage;
import java.util.regex.*;
import java.util.*;

public class supplier {
    private supplierCard sc;
    private agreement ag;
    private final int MaxCompanyNumber=100;
    private final int MaxNamesLength=20;
    private final int PhoneLength=10;
    private final int idLength=9;

    public supplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) throws Exception {
        companyNumberCheck(companyNumber);
        payment pay=paymentCheck(payment);
        NameCheck(firstName);
        NameCheck(lastName);
        idCheck(id);
        phoneCheck(phone);
        emailCheck(email);
        this.sc = new supplierCard(firstName,lastName,email,id,phone,companyNumber,isPernamentDays,selfDelivery,pay);
        this.ag = new agreement();
    }

    //method that check if companyNumber is legal
    private void companyNumberCheck(int comanyNumber) throws Exception {
        if(comanyNumber<0 || comanyNumber>MaxCompanyNumber)
            throw new Exception("invalid company number"+comanyNumber);
    }
    //method that checks if payment is legal
    private payment paymentCheck(String pay){
        return payment.valueOf(pay);//checks if pay belongs to enum
    }
    private void NameCheck(String name) throws Exception {
        if(name==null || name.equals(""))
            throw new Exception("name cannot be null or empty spaces");
        //checks length
        if(name.length()>MaxNamesLength)
            throw new Exception("name length is too long");
        //check if contains only letters
        char[] arrayName = name.toLowerCase().toCharArray();
        for (char c:arrayName) {
            if(c<'a'||c>'z')
                throw new Exception("the name must contain letters only");
        }
    }

    private void idCheck(String id) throws Exception {
        if(id==null || id.equals(""))
            throw new Exception("id cannot be null or empty spaces");
        if(id.length()!=idLength)
            throw new Exception("id must contain "+idLength+" letters");
        //check if contains only numbers
        char[] idArray = id.toCharArray();
        for (char c:idArray) {
            if(c<'0'||c>'9')
                throw new Exception("the name must contain numbers only");
        }
    }
    private void phoneCheck(String phone) throws Exception {
        if(phone==null || phone.equals(""))
            throw new Exception("phone cannot be null or empty spaces");
        if(phone.length()!=PhoneLength)
            throw new Exception("phone must contain "+PhoneLength+" letters");
        //check if contains only numbers
        char[] idArray = phone.toCharArray();
        for (char c:idArray) {
            if(c<'0'||c>'9')
                throw new Exception("the name must contain numbers only");
        }
        if(phone.charAt(0)!='0')
            throw new Exception("phone number must begin with 0");
    }

    private void emailCheck(String email) throws Exception {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            throw new Exception("email cannot be null");
        if(!pat.matcher(email).matches())
            throw new Exception("invalid email");
    }
}
