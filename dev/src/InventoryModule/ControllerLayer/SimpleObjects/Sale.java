package InventoryModule.ControllerLayer.SimpleObjects;

import InfrastructurePackage.Pair;

import java.util.Calendar;
import java.util.Date;

public class Sale<T extends SimpleEntity> {
    private String name;
    private double discount;
    private Pair<Calendar, Calendar> saleDates;
    private T appliesOn;

    public Sale(String name, double discount, Pair<Calendar, Calendar> saleDates, T appliesOn) {
        this.name = name;
        this.discount = discount;
        this.saleDates = saleDates;
        this.appliesOn = appliesOn;
    }

    public String getName() {
        return name;
    }

    public double getDiscount() {
        return discount;
    }

    public Pair<Calendar, Calendar> getSaleDates() {
        return saleDates;
    }

    public T getAppliesOn() {
        return appliesOn;
    }

    public void setAppliesOn(T appliesOn) {
        this.appliesOn = appliesOn;
    }
}
