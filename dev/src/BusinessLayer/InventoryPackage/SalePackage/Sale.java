package BusinessLayer.InventoryPackage.SalePackage;

import InfrastructurePackage.Pair;

import java.time.LocalDate;

public abstract class Sale {
    protected String name;
    protected double discount;
    protected Pair<LocalDate, LocalDate> saleDates;

    public Sale(String name, double discount, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.discount = discount;
        this.saleDates = new Pair<>(startDate, endDate);
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Pair<LocalDate, LocalDate> getSaleDates() {
        return saleDates;
    }

    public LocalDate getStartDate() {
        return saleDates.getFirst();
    }

    public LocalDate getEndDate() {
        return saleDates.getSecond();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSaleDates(LocalDate startDate, LocalDate endDate) {
        this.saleDates.setFirst(startDate);
        this.saleDates.setSecond(endDate);
    }
}
