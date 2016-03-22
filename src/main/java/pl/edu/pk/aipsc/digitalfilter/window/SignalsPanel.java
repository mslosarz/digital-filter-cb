package pl.edu.pk.aipsc.digitalfilter.window;

import static pl.edu.pk.aipsc.digitalfilter.context.Context.Context;

import java.awt.GridLayout;

import javax.swing.JPanel;

import org.math.plot.Plot2DPanel;

import pl.edu.pk.aipsc.digitalfilter.context.Observer;
import pl.edu.pk.aipsc.digitalfilter.math.filter.Filter;
import pl.edu.pk.aipsc.digitalfilter.math.function.ComplexParams;
import pl.edu.pk.aipsc.digitalfilter.math.function.Linear;

public class SignalsPanel extends JPanel implements Observer {

    private Plot2DPanel outputSignal = new Plot2DPanel();

    /**
     * Create the panel.
     */
    public SignalsPanel() {
        setLayout(new GridLayout(0, 1, 0, 0));

        createOutputFilterPanel();

        ConfigPanel configPanel = new ConfigPanel(this);
        add(configPanel);

        update();

    }

    private void createOutputFilterPanel() {
        outputSignal.setAxisLabel(0, "t");
        outputSignal.setAxisLabel(1, "y");
        outputSignal.setName("Output Signal");
        add(outputSignal);
    }


    public void update() {
        Filter filter = Context.getSignalFilter();
        ComplexParams ox = new Linear().calculate(0, filter.getSamplingFreq() / 2, filter.getSamplingFreq()
                / (double) filter.getBinaryResolution());

        ComplexParams oy = filter.filter(Context.getSignal());
        outputSignal.removeAllPlots();
        outputSignal.addLinePlot(Context.getSignal().toString(), ox.toRealParams().getParams(), oy.toRealParams().getParams());

        repaint();
    }

}
