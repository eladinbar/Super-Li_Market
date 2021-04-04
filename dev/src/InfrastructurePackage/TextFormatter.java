package InfrastructurePackage;

import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.lang.reflect.Field;

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

    public String centerString(String s, int width) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public int getPaddingSize() {
        return paddingSize;
    }
}
