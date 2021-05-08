package BusinessLayer.InventoryPackage.SalePackage;

import InfrastructurePackage.Pair;

import java.time.LocalDate;
import java.util.Calendar;

public abstract class Sale {
    protected String name;
    protected double discount;
    protected Pair<LocalDate, LocalDate> saleDates;

    public Sale(String name, double discount, Calendar startDate, Calendar endDate) {
        this.name = name;
        this.discount = discount;
        this.saleDates = new Pair<>(startDate, endDate);
        //Remove redundant time from dates
        startDate.clear(Calendar.MILLISECOND);
        startDate.clear(Calendar.SECOND);
        startDate.clear(Calendar.MINUTE);
        startDate.clear(Calendar.HOUR);
        endDate.clear(Calendar.MILLISECOND);
        endDate.clear(Calendar.SECOND);
        endDate.clear(Calendar.MINUTE);
        endDate.clear(Calendar.HOUR);
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

    public Calendar getStartDate() {
        return saleDates.getFirst();
    }

    public Calendar getEndDate() {
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
