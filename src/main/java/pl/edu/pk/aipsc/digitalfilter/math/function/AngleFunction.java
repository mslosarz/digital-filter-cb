package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public class AngleFunction extends AbstractOperation {

    public Complex value(Complex z) {
        return new Complex(-z.getArgument());
    }

}
