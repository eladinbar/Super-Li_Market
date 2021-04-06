package InventoryModule.BusinessLayer.DefectsPackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectsLogger {
    private List<DefectEntry> defectEntries;

    public DefectsLogger() {
        this.defectEntries = new ArrayList<>();
    }

    public List<DefectEntry> getDefectEntries() {
        return defectEntries;
    }

    public DefectEntry getDefectEntry(int itemId, Date entryDate) {
        for (DefectEntry defectEntry : defectEntries) {
            if (defectEntry.getEntryDate().equals(entryDate) & defectEntry.getItemID() == itemId)
                return defectEntry;
        }
        return null;
    }

    public void addDefectEntry(DefectEntry defectEntry) {
        this.defectEntries.add(defectEntry);
    }

    public void removeDefectEntry(DefectEntry defectEntry) {
        this.defectEntries.remove(defectEntry);
    }
}
