package ru.foobarbaz.converter;

public enum UnitOfMeasure {
    METER(1),
    KILOMETER(1000),
    CENTIMETER(0.01),
    FOOT(0.3048),
    MILE(1609.34),
    INCH(0.0254);

    private double metersRatio;

    UnitOfMeasure(double metersRatio){
        this.metersRatio = metersRatio;
    }

    public double toMeters(double x){
        return x * metersRatio;
    }

    public double fromMeters(double x){
        return x / metersRatio;
    }

    public double toUnit(double x, UnitOfMeasure otherUnit){
        return otherUnit.fromMeters(toMeters(x));
    }
}
