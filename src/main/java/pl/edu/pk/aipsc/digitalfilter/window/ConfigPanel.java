package pl.edu.pk.aipsc.digitalfilter.window;

import static pl.edu.pk.aipsc.digitalfilter.context.Context.Context;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import pl.edu.pk.aipsc.digitalfilter.context.Observer;

public class ConfigPanel extends JPanel {

    /**
     * Create the panel.
     */
    public ConfigPanel(Observer observer) {
        setLayout(new GridLayout(1, 0, 0, 0));

        ComplexPanel complexPanel = new ComplexPanel(Context.getSignalFilter());
        complexPanel.addObserver(observer);
        add(complexPanel);

        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        JTextPane textPane = new JTextPane();
        textPane.setText(Context.getSignal().toString());
        panel.add(textPane);

    }

}
