package pl.edu.pk.aipsc.digitalfilter.math.function;

import java.util.Arrays;

import org.apache.commons.math3.complex.Complex;

public class ComplexParams {

    final Complex[] params;

    private ComplexParams(Complex[] params) {
        this.params = params;
    }

    public Complex[] getParams() {
        return params;
    }

    public Params toRealParams() {
        Params p = Params.create(params.length);
        for (int i = 0; i < params.length; i++) {
            p.params[i] = params[i].getReal();
        }
        return p;
    }

    public Params toImagParams() {
        Params p = Params.create(params.length);
        for (int i = 0; i < params.length; i++) {
            p.params[i] = params[i].getImaginary();
        }
        return p;
    }

    public Params toAbsParams() {
        Params p = Params.create(params.length);
        for (int i = 0; i < params.length; i++) {
            p.params[i] = params[i].abs();
        }
        return p;
    }

    public static ComplexParams create(Complex... params) {
        return new ComplexParams(params);
    }

    public static ComplexParams createFromDouble(double... params) {
        Complex[] c = new Complex[params.length];
        for (int i = 0; i < c.length; i++) {
            c[i] = new Complex(params[i]);
        }
        return new ComplexParams(c);
    }

    public static ComplexParams create(int lenght) {
        return new ComplexParams(new Complex[lenght]);
    }

    @Override
    public String toString() {
        return Arrays.toString(params);
    }

}
