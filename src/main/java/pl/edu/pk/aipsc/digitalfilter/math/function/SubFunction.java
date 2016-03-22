package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public class SubFunction extends AbstractOperation implements Function {

    private Function f1;
    private Function f2;

    private SubFunction(Function f1, Function f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public Complex value(Complex z) {
        return f1.value(z).subtract(f2.value(z));
    }

    public Function getF1() {
        return f1;
    }

    public Function getF2() {
        return f2;
    }

    @Override
    public String toString() {
        return "(" + f1.toString() + " - " + f2.toString() + ")";
    }

    public static SubFunction sub(Function f1, Function f2) {
        return new SubFunction(f1, f2);
    }


}
