package SerciveLayer.SimpleObjects;

import InfrastructurePackage.Pair;

import java.time.LocalDate;
import java.util.Calendar;

public class Sale<T extends SimpleEntity> extends SimpleEntity {
    private String name;
    private double discount;
    private Pair<LocalDate, LocalDate> saleDates;
    private T appliesOn;

    public Sale(String name, double discount, Pair<LocalDate, LocalDate> saleDates, T appliesOn) {
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

    public Pair<LocalDate, LocalDate> getSaleDates() {
        return saleDates;
    }

    public T getAppliesOn() {
        return appliesOn;
    }

    public void setAppliesOn(T appliesOn) {
        this.appliesOn = appliesOn;
    }
}
