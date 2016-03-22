package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;


public class MulFunction extends AbstractOperation implements Function {
    private Function f1;
    private Function f2;

    private MulFunction(Function f1, Function f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public Complex value(Complex z) {
        return f1.value(z).multiply(f2.value(z));
    }

    public MulFunction mul(Function f1) {
        return new MulFunction(this, f1);
    }

    public static MulFunction mul(Function f1, Function f2) {
        return new MulFunction(f1, f2);
    }

    @Override
    public String toString() {
        return "(" + f1.toString() + " * " + f2.toString() + ")";
    }
}
