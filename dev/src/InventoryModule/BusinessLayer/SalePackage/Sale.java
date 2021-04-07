package InventoryModule.BusinessLayer.SalePackage;

import InfrastructurePackage.Pair;

import java.util.Calendar;

public abstract class Sale {
    protected String name;
    protected double discount;
    protected Pair<Calendar, Calendar> saleDates;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Pair<Calendar, Calendar> getSaleDates() {
        return saleDates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStartDate(Calendar startDate) {
        return saleDates.getFirst();
    }

    public Calendar getEndDate(Calendar endDate) {
        return saleDates.getSecond();
    }

    public void setSaleDates(Pair<Calendar, Calendar> saleDates) {
        this.saleDates = saleDates;
    }

    public void setSaleDates(Calendar startDate, Calendar endDate) {
        this.saleDates.setFirst(startDate);
        this.saleDates.setSecond(endDate);
    }

    public void setStartSaleDate(Calendar startDate) {
        this.saleDates.setFirst(startDate);
    }

    public void setEndSaleDate(Calendar endDate) {
        this.saleDates.setSecond(endDate);
    }
}
