package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public class Const extends AbstractOperation {

    public static final Const PI2 = new Const("2*PI", 2 * Math.PI);

    private double value;
    private String name;
    private Complex zValue;

    public Const(String name, double value) {
        this.value = value;
        this.name = name;
        zValue = new Complex(value);
    }

    public Const(double value) {
        this(Double.toString(value), value);
    }

    public double value(double x) {
        return value;
    }

    public Complex value(Complex z) {
        return zValue;
    }

    @Override
    public String toString() {
        return name;
    }

}
