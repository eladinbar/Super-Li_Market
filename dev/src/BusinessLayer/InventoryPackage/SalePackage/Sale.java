package BusinessLayer.InventoryPackage.SalePackage;

import InfrastructurePackage.Pair;

import java.time.LocalDate;

public abstract class Sale {
    protected String name;
    protected double discount;
    protected Pair<LocalDate, LocalDate> saleDates;

    public Sale(String name) {
        this.name = name;
    }

    public Sale(String name, double discount, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.discount = discount;
        this.saleDates = new Pair<>(startDate, endDate);
    }

    public double getDiscount() {
        return discount;
    }

    public abstract void setDiscount(double discount);

    public Pair<LocalDate, LocalDate> getSaleDates() {
        return saleDates;
    }

    public LocalDate getStartDate() {
        return saleDates.getFirst();
    }

    public abstract void setStartDate(LocalDate startDate);

    public LocalDate getEndDate() {
        return saleDates.getSecond();
    }

    public abstract void setEndDate(LocalDate endDate);

    public String getName() {
        return name;
    }

    public abstract void setName(String name);

    public void setSaleDates(LocalDate startDate, LocalDate endDate) {
        setStartDate(startDate);
        setEndDate(endDate);
    }
}
