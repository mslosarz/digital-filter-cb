package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public class ComplexFunction extends AbstractOperation {

    private Complex a;
    private Complex b;

    public ComplexFunction(Complex a, Complex b) {
        this.a = a;
        this.b = b;
    }

    public Complex value(Complex x) {
        return a.multiply(x).add(b);
    }

    @Override
    public String toString() {
        return "(" + a + "*x + " + b + ")";
    }

    public double value(double x) {
        return a.multiply(x).add(b).getReal();
    }
}
