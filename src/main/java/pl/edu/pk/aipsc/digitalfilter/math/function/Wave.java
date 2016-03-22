package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public class Wave {

    private double T;
    private Function function;

    public Wave(int fp, Function function) {
        this.T = 1.0 / fp;
        this.function = function;
    }

    public ComplexParams generate(int specimenNumber) {
        ComplexParams p = ComplexParams.create(specimenNumber);
        double start = 0;
        for (int i = 0; i < specimenNumber; i++) {
            p.params[i] = function.value(new Complex(start));
            start += T;
        }
        return p;
    }
}
