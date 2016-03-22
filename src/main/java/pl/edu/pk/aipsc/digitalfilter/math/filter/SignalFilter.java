package pl.edu.pk.aipsc.digitalfilter.math.filter;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.apache.commons.math3.util.ArithmeticUtils;

import pl.edu.pk.aipsc.digitalfilter.math.function.AbstractOperation;
import pl.edu.pk.aipsc.digitalfilter.math.function.ComplexFunction;
import pl.edu.pk.aipsc.digitalfilter.math.function.ComplexParams;
import pl.edu.pk.aipsc.digitalfilter.math.function.Const;
import pl.edu.pk.aipsc.digitalfilter.math.function.Function;
import pl.edu.pk.aipsc.digitalfilter.math.function.Wave;

public class SignalFilter implements Filter {

    private int samplingFreq;
    private int binaryResolution;
    private double freqResolution;
    private AbstractOperation filterFunction = new Const(1);
    private List<Double> poles = new LinkedList<Double>();
    private List<Double> zeros = new LinkedList<Double>();

    /**
     * Klasa filtru
     *
     * @param binaryResolution próbkowanie na bitach (musi być potęgą 2)
     * @param samplingFreq     częstotliwość próbkowania
     */
    public SignalFilter(int binaryResolution, int samplingFreq) {
        if (ArithmeticUtils.isPowerOfTwo(binaryResolution) == false) {
            throw new IllegalArgumentException(
                    "binary resolution must be power of 2");
        }
        this.binaryResolution = binaryResolution;
        this.samplingFreq = samplingFreq;
        freqResolution = samplingFreq / (double) binaryResolution;
    }

    public void addZeros(Double... zerosFrequency) {
        zeros.addAll(Arrays.asList(zerosFrequency));
        for (Double zero : zerosFrequency) {
            double fi = 2 * Math.PI * zero / samplingFreq;
            Complex z = new Complex(-Math.cos(fi), -Math.sin(fi));
            filterFunction = filterFunction.mul(new ComplexFunction(z, new Complex(1)));
            filterFunction = filterFunction.mul(new ComplexFunction(z.conjugate(), new Complex(1)));
        }
    }

    public void addPoles(Double... polesFrequency) {
        poles.addAll(Arrays.asList(polesFrequency));
        for (Double pole : polesFrequency) {
            double fi = 2 * Math.PI * pole / samplingFreq;
            Complex z = new Complex(-Math.cos(fi), -Math.sin(fi));
            z = z.multiply(0.95);
            filterFunction = filterFunction.div(new ComplexFunction(z, new Complex(1)).mul(new ComplexFunction(z.conjugate(), new Complex(1))));
        }
    }

    public ComplexParams filter(Function f) {
        Wave wy = new Wave(samplingFreq, f);
        ComplexParams py = wy.generate(binaryResolution); // odpowiedź

        FastFourierTransformer fastFourierTransformer = new FastFourierTransformer(
                DftNormalization.STANDARD);
        ComplexParams fft = ComplexParams.create(fastFourierTransformer
                .transform(py.getParams(), TransformType.FORWARD));

        Complex[] output = fft.getParams();

        double resolution = 0;
        for (int i = 0; i < output.length; i++, resolution += freqResolution) {
            double omega = 2 * Math.PI * resolution / samplingFreq;
            Complex z = new Complex(Math.cos(omega), Math.sin(omega));
            output[i] = output[i].multiply(filterFunction.value(z));
        }

        ComplexParams ifft = ComplexParams.create(fastFourierTransformer
                .transform(output, TransformType.INVERSE));
        return ifft;
    }

    public ComplexParams freqChar() {
        ComplexParams result = ComplexParams.create(binaryResolution / 2);
        Complex[] output = result.getParams();
        double resolution = 0;
        for (int i = 0; i < output.length; i++, resolution += freqResolution) {
            double omega = 2 * Math.PI * resolution / samplingFreq;
            Complex z = new Complex(Math.cos(omega), Math.sin(omega));
            output[i] = filterFunction.value(z);
        }
        return ComplexParams.create(output);
    }

    public int getSamplingFreq() {
        return samplingFreq;
    }

    public int getBinaryResolution() {
        return binaryResolution;
    }

    public double getFreqResolution() {
        return freqResolution;
    }

    @Override
    public String toString() {
        return String.format("Fp: %dHz, Res: %.3fHz", samplingFreq, freqResolution);
    }

    public void resetFilter() {
        filterFunction = new Const(1);
        poles.clear();
        zeros.clear();
    }

    public void removePoles(Double... frequency) {
        removePoints(frequency);
    }

    private void removePoints(Double... frequency) {
        List<Double> polesCopy = new LinkedList<Double>(poles);
        List<Double> zerosCopy = new LinkedList<Double>(zeros);
        List<Double> freqs = Arrays.asList(frequency);
        polesCopy.removeAll(freqs);
        zerosCopy.removeAll(freqs);
        resetFilter();
        addPoles(polesCopy.toArray(new Double[polesCopy.size()]));
        addZeros(zerosCopy.toArray(new Double[zerosCopy.size()]));
    }

    public void removeZeros(Double... frequency) {
        removePoints(frequency);
    }

}
