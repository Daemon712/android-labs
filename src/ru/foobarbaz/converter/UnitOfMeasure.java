package ru.foobarbaz.converter;

import ru.foobarbaz.R;

public enum UnitOfMeasure {
    METER(1, R.string.meter),
    KILOMETER(1000, R.string.kilometer),
    CENTIMETER(0.01, R.string.centimeter),
    FOOT(0.3048, R.string.foot),
    MILE(1609.34, R.string.mile),
    INCH(0.0254, R.string.inch);

    private double metersRatio;
    private int nameId;

    UnitOfMeasure(double metersRatio, int nameId){
        this.metersRatio = metersRatio;
        this.nameId = nameId;
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

    public int getNameId() {
        return nameId;
    }
}
