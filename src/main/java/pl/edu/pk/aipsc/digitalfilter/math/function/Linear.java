package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public class Linear extends AbstractOperation {

    public double value(double x) {
        return x;
    }

    public Complex value(Complex z) {
        return z;
    }

    @Override
    public String toString() {
        return "x";
    }


}
