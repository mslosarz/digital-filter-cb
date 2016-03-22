package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;


public class SumFunction extends AbstractOperation implements Function {

    private Function f1;
    private Function f2;

    private SumFunction(Function f1, Function f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public Complex value(Complex z) {
        return f1.value(z).add(f2.value(z));
    }

    public static SumFunction sum(Function f1, Function f2) {
        return new SumFunction(f1, f2);
    }

    public Function getF1() {
        return f1;
    }

    public Function getF2() {
        return f2;
    }

    @Override
    public String toString() {
        return "(" + f1.toString() + " + " + f2.toString() + ")";
    }

}
