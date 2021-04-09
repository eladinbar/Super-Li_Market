package InfrastructurePackage;

import InventoryModule.ControllerLayer.SimpleObjects.*;

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

    public String DefectsMenuFormat(){
        Field[] defectFields = DefectEntry.class.getDeclaredFields();
        String output = "";
        for (int i = 0; i < defectFields.length - 1; i++) {
            String currentField = defectFields[i].getName();
            output = output + centerString(currentField, 20) + "|";
        }
        return output + centerString(defectFields[defectFields.length - 1].getName(), paddingSize);
    }

    public void CategoryMenuFormat(Category category) {
        System.out.println("Category Name: " + category.getName() + "\n" +
                "Parent Category: " + category.getParentCategory() + "\n" +
                "Sub-categories: " + category.getSubCategories().stream().reduce("", (acc, curr) -> acc + ", " + curr)+
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
                "Date: " + discount.getDate().toString() + "\n" +
                "Count: " + discount.getItemCount() +
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
