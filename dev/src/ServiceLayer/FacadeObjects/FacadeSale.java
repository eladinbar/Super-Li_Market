package ServiceLayer.FacadeObjects;

import InfrastructurePackage.Pair;

import java.time.LocalDate;

public class FacadeSale<T extends FacadeEntity> extends FacadeEntity {
    private String name;
    private double discount;
    private Pair<LocalDate, LocalDate> saleDates;
    private T appliesOn;

    public FacadeSale(String name, double discount, Pair<LocalDate, LocalDate> saleDates, T appliesOn) {
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
