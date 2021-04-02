package InventoryModule.BusinessLayer.SalePackage;

import InfrastructurePackage.Pair;

import java.util.Date;

public abstract class Sale {
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

    public Date getStartDate(Date startDate) {
        return saleDates.getFirst();
    }

    public Date getEndDate(Date endDate) {
        return saleDates.getSecond();
    }

    public void setSaleDates(Pair<Date, Date> saleDates) {
        this.saleDates = saleDates;
    }
}
