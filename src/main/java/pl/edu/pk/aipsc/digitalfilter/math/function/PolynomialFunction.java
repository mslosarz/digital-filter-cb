package pl.edu.pk.aipsc.digitalfilter.math.function;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;

// jaka java jest ułomna jeżeli chodzi o operatory
public class PolynomialFunction {

    private List<Complex> coefficients = new LinkedList<Complex>();

    private PolynomialFunction(Complex[] coefficients) {
        this.coefficients.addAll(Arrays.asList(coefficients));
    }

    public List<Complex> getCoefficients() {
        return coefficients;
    }

    /**
     * Pomnóż przez wyrażenie (ax + b)
     *
     * @param a
     * @param b
     */
    public void multiplyBy(Complex a, Complex b) {
        if (isEmpty()) {
            addCoefficeints(a, b);
            return;
        }
        makeMultiplication(a, b);
    }

    public void multiplyBy(double a, double b) {
        multiplyBy(new Complex(a), new Complex(b));
    }

    private void makeMultiplication(Complex a, Complex b) {
        LinkedList<Complex> tmp = new LinkedList<Complex>(coefficients);

        coefficients.add(tmp.getLast().multiply(b));
        coefficients.set(0, tmp.getFirst().multiply(a));

        for (int i = 1; i < tmp.size(); i++) {
            coefficients.set(i,
                    tmp.get(i - 1).multiply(b).add(tmp.get(i).multiply(a)));
        }
    }

    private void addCoefficeints(Complex a, Complex b) {
        coefficients.add(a);
        coefficients.add(b);
    }

    private boolean isEmpty() {
        return coefficients.size() == 0;
    }

    /**
     * @param coefficients kolejne współczynniki wielomianu a0x^n + a1x^(n-1) .. + an
     * @return funkcja wielomianowa
     */
    public static PolynomialFunction createPolynomialFromDouble(double... coefficients) {
        Complex[] cmp = new Complex[coefficients.length];
        for (int i = 0; i < cmp.length; i++) {
            cmp[i] = new Complex(coefficients[i]);
        }
        return new PolynomialFunction(cmp);
    }

    public static PolynomialFunction createPolynomialFromComplex(Complex... coefficients) {
        return new PolynomialFunction(coefficients);
    }

    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder();
        int pow = coefficients.size() - 1;
        for (int i = 0; i < coefficients.size(); i++, pow--) {
            bld.append("(" + coefficients.get(i).getReal() + "*x^" + pow + ")");
            if (i + 1 < coefficients.size()) {
                bld.append(" + ");
            }
        }
        return bld.toString();
    }

}
