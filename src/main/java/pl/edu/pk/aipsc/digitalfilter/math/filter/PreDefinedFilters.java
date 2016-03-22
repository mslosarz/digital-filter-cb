package pl.edu.pk.aipsc.digitalfilter.math.filter;

import static java.lang.Math.*;

import org.apache.commons.math3.complex.Complex;

public enum PreDefinedFilters {
    /**
     *
     */
    Czebyszew3 {
        @Override
        public Complex[] getPoles() {
            double angle = PI / 3;
            double angle2 = angle / 2;
            return new Complex[]{//
                    new Complex(cos(PI / 2 + angle2) * 17 / 20, sin(PI / 2 + angle2) * 17 / 20),//
                    new Complex(cos(PI) * 15 / 20, sin(PI)) //
            };
        }
    },
    Czebyszew4 {
        @Override
        public Complex[] getPoles() {
            double angle = PI / 4;
            double angle2 = angle / 2;
            return new Complex[]{//
                    new Complex(cos(PI + angle2) * 29 / 40, sin(PI + angle2) * 18 / 20),//
                    new Complex(cos(PI / 2 + angle2) * 13 / 20, sin(PI / 2 + angle2) * 19 / 20) //
            };
        }
    },
    Czebyszew5 {
        @Override
        public Complex[] getPoles() {
            double angle = PI / 5;
            double angle2 = angle / 2;
            return new Complex[]{//
                    new Complex(cos(PI + angle2) * 15 / 20, sin(PI + angle2) * 16 / 20),//
                    new Complex(cos(PI / 2 + angle2) * 6 / 20, sin(PI / 2 + angle2)), //
                    new Complex(cos(PI) * 15 / 20, sin(PI)) //
            };
        }
    },
    Czebyszew6 {
        @Override
        public Complex[] getPoles() {
            double angle = PI / 6;
            double angle2 = angle / 2;
            return new Complex[]{//
                    new Complex(cos(PI + angle2) * 31 / 40, sin(PI + angle2) * 12 / 20),//
                    new Complex(cos(PI / 2 + angle + angle2) * 16 / 20, sin(PI / 2 + angle + angle2) * 16 / 20), //
                    new Complex(cos(PI / 2 + angle2) * 15 / 20, sin(PI / 2 + angle2) * 19 / 20) //
            };
        }
    },
    Butterworth3 {
        @Override
        public Complex[] getPoles() {
            double angle = PI / 3;
            double angle2 = angle / 2;
            return new Complex[]{//
                    new Complex(cos(PI / 2 + angle2), sin(PI / 2 + angle2)),//
                    new Complex(cos(PI), sin(PI)) //
            };
        }
    },
    Butterworth4 {
        @Override
        public Complex[] getPoles() {
            double angle = PI / 4;
            double angle2 = angle / 2;
            return new Complex[]{//
                    new Complex(cos(PI + angle2), sin(PI + angle2)),//
                    new Complex(cos(PI / 2 + angle2), sin(PI / 2 + angle2)) //
            };
        }
    },
    Butterworth5 {
        @Override
        public Complex[] getPoles() {
            double angle = PI / 5;
            double angle2 = angle / 2;
            return new Complex[]{//
                    new Complex(cos(PI + angle2), sin(PI + angle2)),//
                    new Complex(cos(PI / 2 + angle2), sin(PI / 2 + angle2)), //
                    new Complex(cos(PI), sin(PI)) //
            };
        }
    },
    Butterworth6 {
        @Override
        public Complex[] getPoles() {
            double angle = PI / 6;
            double angle2 = angle / 2;
            return new Complex[]{//
                    new Complex(cos(PI + angle2), sin(PI + angle2)),//
                    new Complex(cos(PI / 2 + angle + angle2), sin(PI / 2 + angle + angle2)), //
                    new Complex(cos(PI / 2 + angle2), sin(PI / 2 + angle2)) //
            };
        }
    };

    public abstract Complex[] getPoles();

}
