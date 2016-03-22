package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public class Sin extends AbstractOperation {

    public double value(double x) {
        return Math.sin(x);
    }

    public Complex value(Complex z) {
        return z.sin();
    }

    @Override
    public String toString() {
        return "sin(x)";
    }

}
