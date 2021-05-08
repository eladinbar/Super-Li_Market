package InfrastructurePackage;

import SerciveLayer.SimpleObjects.*;
import SerciveLayer.objects.Product;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TextFormatter {
    private int paddingSize = 20;


    public String formatItemMenuColumns() {
        Field[] itemFields = Item.class.getDeclaredFields();
        String outPut = "";
        for (int i = 0; i < itemFields.length - 1; i++) {
            String currentField = itemFields[i].getName();
            outPut = outPut + centerString(currentField, 20) + "|";
        }
        return outPut + centerString(itemFields[itemFields.length - 1].getName(), paddingSize);
    }

    public String formatProductMenuColumns(){
        Field[] productFields = Product.class.getDeclaredFields();
        String outPut = "";
        for (int i = 0; i < productFields.length - 1; i++) {
            String currentField = productFields[i].getName();
            outPut = outPut + centerString(currentField, 20) + "|";
        }
        return outPut + centerString(productFields[productFields.length - 1].getName(), paddingSize);
    }

    public String defectsMenuFormat(){
        Field[] defectFields = DefectEntry.class.getDeclaredFields();
        String output = "";
        for (int i = 0; i < defectFields.length - 1; i++) {
            String currentField = defectFields[i].getName();
            output = output + centerString(currentField, 20) + "|";
        }
        return output + centerString(defectFields[defectFields.length - 1].getName(), paddingSize);
    }

    public void categoryMenuFormat(Category category) {
        String subCategories = category.getSubCategories().stream().reduce("", (acc, curr) -> acc + curr + ", ");
        subCategories = subCategories.substring(0,subCategories.length() - 2);
        System.out.println("Category Name: " + category.getName() + "\n" +
                "Parent Category: " + category.getParentCategory() + "\n" +
                "Sub-categories: " + subCategories +
                "\nCategory items: " );
    }

    public <T extends SimpleEntity> void saleMenuFormat(Sale<T> sale) {
        System.out.println("Category Name: " + sale.getName() + "\n" +
                "Discount: " + sale.getDiscount() + "\n" +
                "Sale Dates: " + dateFormat(sale.getSaleDates().getFirst()) + " until " + dateFormat(sale.getSaleDates().getSecond()));
    }
    public <T extends SimpleEntity> void discountMenuFormat(Discount<T> discount) {
        System.out.println("Supplier ID: " + discount.getSupplierID() + "\n" +
                "Discount: " + discount.getDiscount() + "\n" +
                "Date: " + dateFormat(discount.getDate()) + "\n" +
                "Count: " + discount.getItemCount() + "\n"+
                 "Applied On:");
    }



    public String centerString(String s, int width) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public int getPaddingSize() {
        return paddingSize;
    }

    public String dateFormat(Calendar date){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(date.getTime());
    }
}
