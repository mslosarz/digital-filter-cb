package pl.edu.pk.aipsc.digitalfilter.context;

import pl.edu.pk.aipsc.digitalfilter.math.filter.Filter;
import pl.edu.pk.aipsc.digitalfilter.math.filter.SignalFilter;
import pl.edu.pk.aipsc.digitalfilter.math.function.AbstractOperation;
import pl.edu.pk.aipsc.digitalfilter.math.function.Const;
import pl.edu.pk.aipsc.digitalfilter.math.function.Linear;
import pl.edu.pk.aipsc.digitalfilter.math.function.Sin;

public enum Context {
    Context;

    private Context() {
        characteristicFilter = new SignalFilter(4096, 1000);
        signalFilter = new SignalFilter(8192, 2000);
        prepareSignal();
    }

    private void prepareSignal() { // o javo daj nam wreszcie wyrażenia lambda
        signal = new Const(10).mul(new Sin().onParam(new Linear()
                .mul(Const.PI2)));
        signal = signal.add(new Sin().onParam(new Linear().mul(Const.PI2).mul(
                new Const(200))));
        signal = signal.add(new Sin().onParam(new Linear().mul(Const.PI2).mul(
                new Const(600))));
    }

    private AbstractOperation signal;
    private Filter characteristicFilter;
    private Filter signalFilter;

    public Filter getCharacteristicFilter() {
        return characteristicFilter;
    }

    public Filter getSignalFilter() {
        return signalFilter;
    }

    public AbstractOperation getSignal() {
        return signal;
    }

}
