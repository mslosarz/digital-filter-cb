package pl.edu.pk.aipsc.digitalfilter.window;

import static pl.edu.pk.aipsc.digitalfilter.context.Context.Context;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.math.plot.Plot2DPanel;

import pl.edu.pk.aipsc.digitalfilter.context.Observer;
import pl.edu.pk.aipsc.digitalfilter.math.filter.Filter;
import pl.edu.pk.aipsc.digitalfilter.math.filter.PreDefinedFilters;
import pl.edu.pk.aipsc.digitalfilter.math.function.AngleFunction;
import pl.edu.pk.aipsc.digitalfilter.math.function.ComplexParams;
import pl.edu.pk.aipsc.digitalfilter.math.function.DecibelFunction;

public class CharacteristicsPanel extends JPanel implements Observer {

    private Plot2DPanel freq = new Plot2DPanel();
    private Plot2DPanel angle = new Plot2DPanel();
    private JPanel definedFunction = new JPanel();
    private ComplexPanel filterPanel;

    private Filter filter = Context.getCharacteristicFilter();
    private final JComboBox preDefined = new JComboBox();
    private final JButton choosePreDefined = new JButton("Choose");

    public CharacteristicsPanel() {
        setLayout(new GridLayout(2, 1, 0, 0));

        prepareFreqPanel();

        prepareAnglePanel();

        prepareDefinedFilterPanel();

        filterPanel = new ComplexPanel(Context.getCharacteristicFilter());
        filterPanel.addObserver(this);
        add(filterPanel);

    }

    private void prepareDefinedFilterPanel() {
        add(definedFunction);
        preDefined.setModel(new DefaultComboBoxModel(PreDefinedFilters.values()));
        definedFunction.add(preDefined);
        choosePreDefined.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                filterPanel.addPoles(((PreDefinedFilters) preDefined.getSelectedItem()).getPoles());
                update();
            }
        });
        definedFunction.add(choosePreDefined);
        update();
    }

    private void prepareAnglePanel() {
        angle.setAxisLabel(0, "Ω");
        angle.setAxisLabel(1, "angle H(e^iΩ)");
        angle.setName("Angle Characteristics");
        add(angle);
        update();
    }

    private void prepareFreqPanel() {
        freq.setAxisLabel(0, "Ω");
        freq.setAxisLabel(1, "20*log(|H(e^iΩ)|) dB");
        freq.setName("Frequency Characteristics");
        add(freq);
        update();
    }

    public void update() {
        freq.removeAllPlots();
        angle.removeAllPlots();
        ComplexParams params = filter.freqChar();
        double res = 0;
        double[] ox = new double[params.getParams().length];
        for (int i = 0; i < ox.length; i++, res += filter.getFreqResolution()) {
            ox[i] = res;
        }
        freq.addLinePlot("", ox, new DecibelFunction().f(params).toRealParams()
                .getParams());
        angle.addLinePlot("", ox, new AngleFunction().f(params).toRealParams()
                .getParams());
        repaint();
    }

}
