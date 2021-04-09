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

    public void setSaleDates(Calendar startDate, Calendar endDate) {
        this.saleDates.setFirst(startDate);
        this.saleDates.setSecond(endDate);
    }
}
