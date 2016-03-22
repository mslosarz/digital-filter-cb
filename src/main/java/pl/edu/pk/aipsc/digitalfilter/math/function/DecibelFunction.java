package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public class DecibelFunction extends AbstractOperation {

    public Complex value(Complex z) {
        Double real = 20 * Math.log10(z.abs());
        return new Complex(real);
    }

}
