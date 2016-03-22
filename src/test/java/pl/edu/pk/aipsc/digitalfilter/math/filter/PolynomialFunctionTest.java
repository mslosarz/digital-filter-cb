package pl.edu.pk.aipsc.digitalfilter.math.filter;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

import pl.edu.pk.aipsc.digitalfilter.math.function.PolynomialFunction;

public class PolynomialFunctionTest {

    @Test
    public void polynomialAxplusB() {
        PolynomialFunction pf = PolynomialFunction.createPolynomialFromDouble();

        pf.multiplyBy(1, -2);

        List<Complex> coefficients = pf.getCoefficients();
        assertEquals(2, coefficients.size());
        assertEquals(new Complex(1.0), coefficients.get(0));
        assertEquals(new Complex(-2.0), coefficients.get(1));
    }

    @Test
    public void polynomialAxplusBToString() {
        PolynomialFunction pf = PolynomialFunction.createPolynomialFromDouble();

        pf.multiplyBy(1, -2);

        assertEquals("(1.0*x^1) + (-2.0*x^0)", pf.toString());

    }

    @Test
    public void polynomialXminus1MulXminux1() {
        PolynomialFunction pf = PolynomialFunction.createPolynomialFromDouble();

        pf.multiplyBy(1, -1);
        pf.multiplyBy(1, -1);

        assertEquals("(1.0*x^2) + (-2.0*x^1) + (1.0*x^0)", pf.toString());

    }

    @Test
    public void polynomialXminus1MulXminux1MulXminus1() {
        PolynomialFunction pf = PolynomialFunction.createPolynomialFromDouble();

        pf.multiplyBy(1, -1);
        pf.multiplyBy(1, -1);
        pf.multiplyBy(1, -1); // (x-1)*(x-1)*(x-1)

        assertEquals("(1.0*x^3) + (-3.0*x^2) + (3.0*x^1) + (-1.0*x^0)", pf.toString());

    }

}
