package pl.edu.pk.aipsc.digitalfilter.math.filter;

import pl.edu.pk.aipsc.digitalfilter.math.function.ComplexParams;
import pl.edu.pk.aipsc.digitalfilter.math.function.Function;

public interface Filter {

    ComplexParams filter(Function f);

    int getSamplingFreq();

    int getBinaryResolution();

    double getFreqResolution();

    public ComplexParams freqChar();

    void addZeros(Double... zerosFrequency);

    void addPoles(Double... polesFrequency);

    void resetFilter();

    void removePoles(Double... frequency);

    void removeZeros(Double... frequency);

}