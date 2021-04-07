package InventoryModule.ControllerLayer.SimpleObjects;

import InfrastructurePackage.Pair;

import java.util.Date;

public class Sale<T extends SimpleEntity> {
    private String name;
    private double discount;
    private Pair<Date, Date> saleDates;
    private T appliesOn;

    public Sale(String name, double discount, Pair<Date, Date> saleDates, T appliesOn) {
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

    public Pair<Date, Date> getSaleDates() {
        return saleDates;
    }

    public T getAppliesOn() {
        return appliesOn;
    }
}
