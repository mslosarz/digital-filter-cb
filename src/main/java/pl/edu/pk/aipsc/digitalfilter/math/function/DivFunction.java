package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public class DivFunction extends AbstractOperation implements Function {

    private Function f1;
    private Function f2;

    private DivFunction(Function f1, Function f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public Complex value(Complex z) {
        return f1.value(z).divide(f2.value(z));
    }

    @Override
    public String toString() {
        return "(" + f1.toString() + " / " + f2.toString() + ")";
    }

    public static DivFunction div(Function f1, Function f2) {
        return new DivFunction(f1, f2);
    }

}
