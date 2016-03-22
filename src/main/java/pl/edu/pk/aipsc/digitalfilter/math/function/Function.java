package pl.edu.pk.aipsc.digitalfilter.math.function;

import org.apache.commons.math3.complex.Complex;


public interface Function {

    ComplexParams f(ComplexParams p);

    ComplexParams calculate(Complex start, Complex end, Complex dx);

    ComplexParams calculate(double start, double end, double dx);

    Complex value(Complex z);

}
