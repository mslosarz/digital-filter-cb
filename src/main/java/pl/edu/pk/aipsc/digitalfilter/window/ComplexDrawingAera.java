package pl.edu.pk.aipsc.digitalfilter.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.math3.complex.Complex;

import pl.edu.pk.aipsc.digitalfilter.context.Observer;
import pl.edu.pk.aipsc.digitalfilter.math.filter.Filter;

class ComplexDrawingAera extends JPanel {

    private int border;
    private MouseEvent last;
    private Filter filter;
    private String staticInfo;
    private MouseState state = new MouseState();
    private List<PointHolder> points = new LinkedList<PointHolder>();
    private List<Observer> observers = new LinkedList<Observer>();

    public ComplexDrawingAera(int border, Filter filter) {
        setBackground(Color.WHITE);
        this.border = border;
        this.filter = filter;
        staticInfo = filter.toString();
        addMouseBehaviour();
    }

    public Filter getFilter() {
        return filter;
    }

    public List<PointHolder> getPoints() {
        return points;
    }

    public void clear() {
        points.clear();
        filter.resetFilter();
        notifyObservers();
        repaint();
    }

    private void addMouseBehaviour() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                state.startDrawing(e);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                state.stopDrawing(e);
                last = null;
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                state.updatePosition(e);
                removeNearestPoint(last);
                addComplexPoint(e);
                last = e;
                repaint();
            }
        });
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void addPole(Complex c) {
        double maxX = getWidth() / 2 - border;
        double maxY = getHeight() / 2 - border;
        int centralY = (int) (border + maxY);
        int centralX = (int) (border + maxX);
        Point draw = new Point((int) (centralX + c.getReal() * maxX), (int) (centralY + c.getImaginary() * maxY));
        double freq = (Math.acos(c.getReal() / c.abs()) * filter.getSamplingFreq()) / (2 * Math.PI);
        PointType type = PointType.Pole;
        type.add(filter, freq);
        points.add(new PointHolder(
                        draw,
                        type,
                        c,
                        freq
                )
        );
        points.add(new PointHolder(
                        new Point((int) draw.getX(), (int) (getHeight() - draw.getY())),
                        type,
                        c.conjugate(),
                        freq
                )
        );
        notifyObservers();

    }

    private void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawOval(border, border, getWidth() - 2 * border, getHeight() - 2 * border);
        drawAxis(g);
        drawStaticInfo(g);
        if (state.draw) {
            drawLine(g);
            drawInfo(g);
        }
        drawZerosAndPoles(g);
    }

    private void drawStaticInfo(Graphics g) {
        g.drawString(staticInfo, 10, 10);
    }

    private void drawAxis(Graphics g) {
        drawOX(g);
        drawOY(g);
    }


    private void drawZerosAndPoles(Graphics g) {
        for (PointHolder ph : points) {
            ph.getType().draw(g, ph.getPosition());
        }
    }

    private void drawInfo(Graphics g) {
        double maxX = getWidth() / 2 - border;
        double maxY = getHeight() / 2 - border;
        int centralY = (int) (border + maxY);
        int centralX = (int) (border + maxX);
        double x = (state.drawToX - centralX) / centralX;
        double y = (centralY - state.drawToY) / centralY;
        double z = Math.sqrt(x * x + y * y);
        // przebiegamy od 0 do fi
        g.drawString(String.format("freq: %.0f Hz, |z| = %.3f", (Math.acos(x / z) * filter.getSamplingFreq()) / (2 * Math.PI), z), 10, getHeight());
    }

    private void drawLine(Graphics g) {
        int yPos = border + (getHeight() - 2 * border) / 2;
        int xPos = border + (getWidth() - 2 * border) / 2;
        g.drawLine(xPos, yPos, state.drawToX, state.drawToY);
        g.drawLine(xPos, yPos, state.drawToX, getHeight() - state.drawToY);

    }

    private void drawOX(Graphics g) {
        int yPos = border + (getHeight() - 2 * border) / 2;
        g.drawLine(0, yPos, getWidth(), yPos);
        g.drawString("Re", getWidth() - border + 1, yPos);
    }

    private void drawOY(Graphics g) {
        int xPos = border + (getWidth() - 2 * border) / 2;
        g.drawLine(xPos, 0, xPos, getHeight());
        g.drawString("Im", xPos + 1, border - 1);
    }

    private void addComplexPoint(MouseEvent e) {
        double maxX = getWidth() / 2 - border;
        double maxY = getHeight() / 2 - border;
        int centralY = (int) (border + maxY);
        int centralX = (int) (border + maxX);
        double x = (state.drawToX - centralX) / centralX;
        double y = (centralY - state.drawToY) / centralY;
        double z = Math.sqrt(x * x + y * y);
        Complex value = new Complex(x, y);
        double freq = (Math.acos(x / z) * filter.getSamplingFreq()) / (2 * Math.PI);
        PointType type = getType(e);
        type.add(filter, freq);
        points.add(new PointHolder(
                        new Point(state.drawToX, state.drawToY),
                        type,
                        value,
                        freq
                )
        );
        points.add(new PointHolder(
                        new Point(state.drawToX, getHeight() - state.drawToY),
                        type,
                        value.conjugate(),
                        freq
                )
        );
        notifyObservers();
    }

    private void removeNearestPoint(MouseEvent e) {
        List<PointHolder> toRemoves = getPointToRemove(e);
        for (PointHolder toRemove : toRemoves) {
            PointHolder conjPoint = getConjPoint(toRemove);
            getType(e).remove(filter, toRemove.frequency);
            points.remove(toRemove);
            points.remove(conjPoint);
        }
    }


    private List<PointHolder> getPointToRemove(MouseEvent e) {
        List<PointHolder> pointsToRemove = new ArrayList<PointHolder>();
        if (e == null) {
            return pointsToRemove;
        }
        for (PointHolder point : points) {
            if (point.position.distance(e.getPoint()) < 5) {
                pointsToRemove.add(point);
            }
        }
        return pointsToRemove;
    }

    private PointHolder getConjPoint(PointHolder toRemove) {
        int x = toRemove.position.x;
        int y = getHeight() - toRemove.position.y;
        for (PointHolder point : points) {
            if (point.position.x == x && point.position.y == y) {
                return point;
            }
        }
        return null;
    }

    private PointType getType(MouseEvent e) {
        PointType type = PointType.Pole;
        if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
            type = PointType.Pole;
        } else if (e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {
            type = PointType.Zero;
        }
        return type;
    }

    private class MouseState {

        int drawToX;
        int drawToY;
        boolean draw;
        int yPos = border + (getHeight() - 2 * border) / 2;
        int xPos = border + (getWidth() - 2 * border) / 2;

        void startDrawing(MouseEvent e) {
            draw = true;
            updatePosition(e);
        }

        void stopDrawing(MouseEvent e) {
            draw = false;
            updatePosition(e);
        }

        void updatePosition(MouseEvent e) {
            calculateToXY(e);
        }

        void calculateToXY(MouseEvent e) {
            drawToX = e.getX() - yPos;
            drawToY = e.getY() - xPos;
        }

    }

    private enum PointType {
        Pole {
            @Override
            public void draw(Graphics g, Point p) {
                g.drawString("X", p.x - 3, p.y + 3);
            }

            @Override
            public void add(Filter filter, double freq) {
                filter.addPoles(freq);
            }

            @Override
            public void remove(Filter filter, double frequency) {
                filter.removePoles(frequency);
            }
        },
        Zero {
            @Override
            public void draw(Graphics g, Point p) {
                g.drawString("O", p.x - 4, p.y + 3);
            }

            @Override
            public void add(Filter filter, double freq) {
                filter.addZeros(freq);
            }

            @Override
            public void remove(Filter filter, double frequency) {
                filter.removeZeros(frequency);
            }
        };

        public abstract void draw(Graphics g, Point p);

        public abstract void remove(Filter filter, double frequency);

        public abstract void add(Filter filter, double freq);
    }

    public class PointHolder {
        private final Point position;
        private final PointType type;
        private final double frequency;
        private final Complex value;

        public PointHolder(Point position, PointType type, Complex value, double freqency) {
            this.position = position;
            this.type = type;
            this.value = value;
            this.frequency = freqency;
        }

        public Point getPosition() {
            return position;
        }

        public PointType getType() {
            return type;
        }

        public Complex getValue() {
            return value;
        }

        public double getFrequency() {
            return frequency;
        }

        @Override
        public String toString() {
            return String.format("%s->Freq: %.3fHz %s\n", type, frequency, value);
        }

    }
}