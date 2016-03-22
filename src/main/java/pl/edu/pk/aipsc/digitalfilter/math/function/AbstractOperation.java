package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;

public abstract class AbstractOperation implements Operation, Function {

    public AbstractOperation add(Function f) {
        return SumFunction.sum(this, f);
    }

    public AbstractOperation div(Function f) {
        return DivFunction.div(this, f);
    }

    public AbstractOperation mul(Function f) {
        return MulFunction.mul(this, f);
    }

    public AbstractOperation sub(Function f) {
        return SubFunction.sub(this, f);
    }

    public AbstractOperation onParam(Function f) {
        return OnParameter.on(this, f);
    }

    public ComplexParams calculate(Complex start, Complex end, Complex dx) {
        int pdx = (int) (end.subtract(start).divide(dx).getReal());
        Complex[] result = new Complex[pdx];
        for (int i = 0; i < pdx; i++) {
            result[i] = value(start);
            start.add(dx);
        }
        return ComplexParams.create(result);
    }

    public ComplexParams calculate(double start, double end, double dx) {
        int pdx = (int) ((end - start) / dx);
        Complex[] result = new Complex[pdx];
        for (int i = 0; i < pdx; i++) {
            result[i] = new Complex((start));
            start += dx;
        }
        return ComplexParams.create(result);
    }

    public ComplexParams f(ComplexParams p) {
        ComplexParams result = ComplexParams.create(p.params.length);
        for (int i = 0; i < result.params.length; i++) {
            result.params[i] = value(p.params[i]);
        }
        return result;
    }

    public Params f(Params p) {
        ComplexParams result = ComplexParams.create(p.lenght);
        for (int i = 0; i < result.params.length; i++) {
            result.params[i] = value(new Complex(p.params[i]));
        }
        return result.toAbsParams();
    }

}
