package InventoryModule.BusinessLayer.SalePackage;

import InfrastructurePackage.Pair;

import java.util.Date;

public abstract class Sale {
    protected String name;
    protected double discount;
    protected Pair<Date, Date> saleDates;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Pair<Date, Date> getSaleDates() {
        return saleDates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate(Date startDate) {
        return saleDates.getFirst();
    }

    public Date getEndDate(Date endDate) {
        return saleDates.getSecond();
    }

    public void setSaleDates(Pair<Date, Date> saleDates) {
        this.saleDates = saleDates;
    }

    public void setSaleDates(Date startDate, Date endDate) {
        this.saleDates.setFirst(startDate);
        this.saleDates.setSecond(endDate);
    }

    public void setStartSaleDate(Date startDate) {
        this.saleDates.setFirst(startDate);
    }

    public void setEndSaleDate(Date endDate) {
        this.saleDates.setSecond(endDate);
    }
}
