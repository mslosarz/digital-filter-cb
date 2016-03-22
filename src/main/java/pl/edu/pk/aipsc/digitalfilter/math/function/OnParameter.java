package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public class OnParameter extends AbstractOperation {

    private Function f1;
    private Function f2;

    private OnParameter(Function f1, Function onParameter) {
        this.f1 = f1;
        this.f2 = onParameter;
    }

    public Complex value(Complex z) {
        return f1.value(f2.value(z));
    }

    public Function getF1() {
        return f1;
    }

    public Function getF2() {
        return f2;
    }

    @Override
    public String toString() {
        return f1.toString() + " [x => " + f2.toString() + "]";
    }

    public static OnParameter on(Function f1, Function f2) {
        return new OnParameter(f1, f2);
    }


}
