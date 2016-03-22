package pl.edu.pk.aipsc.digitalfilter.window;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.math3.complex.Complex;

import pl.edu.pk.aipsc.digitalfilter.context.Observer;
import pl.edu.pk.aipsc.digitalfilter.math.filter.Filter;
import pl.edu.pk.aipsc.digitalfilter.window.ComplexDrawingAera.PointHolder;

public class ComplexPanel extends JPanel {

    private final ComplexDrawingAera drawingAera;
    private final ComplexPanel instance;

    /**
     * Create the panel.
     */
    public ComplexPanel(Filter filter) {
        instance = this;
        setLayout(new BorderLayout(0, 0));

        drawingAera = new ComplexDrawingAera(20, filter);
        add(drawingAera);

        JPanel toolPanel = new JPanel();
        add(toolPanel, BorderLayout.SOUTH);
        toolPanel.setLayout(new GridLayout(0, 2, 0, 0));

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingAera.clear();
            }
        });
        toolPanel.add(clearButton);

        JButton showButton = new JButton("Points");
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(instance,
                        formatPoints(drawingAera.getPoints()));
            }
        });
        toolPanel.add(showButton);

    }

    protected String formatPoints(List<PointHolder> points) {
        StringBuilder bld = new StringBuilder();
        int i = 0;
        for (PointHolder p : points) {
            if (i % 2 == 0) {
                bld.append(p);
            }
            i++;
        }
        return bld.toString();
    }

    public void addObserver(Observer observer) {
        drawingAera.addObserver(observer);
    }

    public void addPoles(Complex... poles) {
        for (Complex pole : poles) {
            drawingAera.addPole(pole);
        }
    }

}
