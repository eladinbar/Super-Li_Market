package BusinessLayer.InventoryPackage.DefectsPackage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DefectsLogger {
    private final List<DefectEntry> defectEntries;

    public DefectsLogger() {
        this.defectEntries = new ArrayList<>();
    }

    public List<DefectEntry> getDefectEntries() {
        return defectEntries;
    }

    public DefectEntry getDefectEntry(int itemId, LocalDate entryDate) {
        for (DefectEntry defectEntry : defectEntries) {
            if (defectEntry.getEntryDate().equals(entryDate) & defectEntry.getItemID() == itemId)
                return defectEntry;
        }
        return null;
    }

    public void addDefectEntry(DefectEntry defectEntry) {
        this.defectEntries.add(defectEntry);
    }
}
