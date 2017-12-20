package calculations.core;

import static java.lang.Math.*;

public class ParticularCases {

    private double c;

    private Formula[] p;
    private Formula[] derivate;
    private Formula[] integral;

    public ParticularCases(double c) {
        this.c = c;
        p = new Formula[]{f_0, f_1, f_2, f_3, f_4, f_5};
        derivate = new Formula[]{der_0, der_1, der_2, der_3, der_4, der_5};
        integral = new Formula[]{integral_0};
    }


    public Formula[] getParticularCases(int type) {
        switch (type) {
            case 0:
                return p;
            case 1:
                return derivate;
            case 2:
                return integral;
        }
        throw new RuntimeException();
    }

    //Particular cases implementation for P
    private Formula f_0 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            return Math.exp(-c * gamma * tao / 2);
        }
    };
    private Formula f_1 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            return Math.exp(-c * gamma * tao / 2) * (1 - (betta + 2) * Math.exp(-c * gamma * tao));
        }
    };

    private Formula f_2 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            return Math.exp(-c * gamma * tao / 2) * (1 - 2 * (betta + 3) * Math.exp(-c * gamma * tao) + (betta + 3) * (betta + 4) * Math.exp(-2 * c * gamma * tao) / 2);
        }
    };

    private Formula f_3 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            return Math.exp(-c * gamma * tao / 2)
                    * (1 - 3 * (betta + 4) * Math.exp(-c * gamma * tao) + 3 * (betta + 4) * (betta + 5)
                    * Math.exp(-2 * gamma * c * tao) / 2 - (betta + 4) * (betta + 5) * (betta + 6) * Math.exp(-3 * c * gamma * tao) / 6);
        }
    };

    private Formula f_4 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            double cyt = c * gamma * tao;
            return exp(-cyt / 2)
                    * (1 - 4 * (betta + 5) * exp(-cyt) + 3 * (betta + 5) * (betta + 6) * exp(-2 * cyt)
                    - 2 * (betta + 5) * (betta + 6) * (betta + 7) * exp(-3 * cyt) / 3
                    + (betta + 5) * (betta + 6) * (betta + 7) * (betta + 8) * exp(-4 * cyt) / 24);
        }
    };

    private Formula f_5 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            double cyt = c * gamma * tao;
            return exp(-cyt / 2)
                    * (1 - 5 * (betta + 6) * exp(-cyt)
                    + 5 * (betta + 6) * (betta + 7) * exp(-2 * cyt)
                    - 5 * (betta + 6) * (betta + 7) * (betta + 8) * exp(-3 * cyt) / 3
                    + 5 * (betta + 6) * (betta + 7) * (betta + 8) * (betta + 9) * exp(-4 * cyt) / 24
                    - (betta + 6) * (betta + 7) * (betta + 8) * (betta + 9) * (betta + 10) * exp(-5 * cyt) / 120);
        }
    };

    //Particular cases implementation for deriviate

    private Formula der_0 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            return pow(-c * gamma / 2, 1) * exp(-c * gamma * tao / 2);
        }
    };

    private Formula der_1 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            double cyt = c * gamma * tao;
            return pow(-c * gamma / 2, 1) * exp(-cyt / 2) * (1 - 3 * (betta + 2) * Math.exp(-cyt));
        }
    };

    private Formula der_2 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            double cyt = c * gamma * tao;
            return pow(-c * gamma / 2, 1) * exp(-cyt / 2) *
                    (1 - 6 * (betta + 3) * exp(-cyt) + 5 * (betta + 3) * (betta + 4) * exp(-2 * cyt) / 2);
        }
    };

    private Formula der_3 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            double cyt = c * gamma * tao;
            return pow(-c * gamma / 2, 1) * exp(-cyt / 2)
                    * (1 - 9 * (betta + 4) * exp(-cyt)
                    + 15 * (betta + 4) * (betta + 5) * exp(-2 * cyt) / 2
                    - 7 * (betta + 4) * (betta + 5) * (betta + 6) * exp(-3 * cyt) / 6);

        }
    };

    private Formula der_4 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            double cyt = c * gamma * tao;
            return pow(-c * gamma / 2, 1) * exp(-cyt / 2) *
                    (1 - 12 * (betta + 5) * exp(-cyt)
                            + 15 * (betta + 5) * (betta + 6) * exp(-2 * cyt)
                            - 14 * (betta + 5) * (betta + 6) * (betta + 7) * exp(-3 * cyt) / 3
                            + 9 * (betta + 5) * (betta + 6) * (betta + 7) * (betta + 8) * exp(-4 * cyt) / 24);
        }
    };

    private Formula der_5 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            double cyt = c * gamma * tao;
            return pow(-c * gamma / 2, 1) * exp(-cyt / 2) *
                    (1 - 15 * (betta + 6) * exp(-cyt)
                            + 25 * (betta + 6) * (betta + 7) * exp(-2 * cyt)
                            - 35 * (betta + 6) * (betta + 7) * (betta + 8) * exp(-3 * cyt) / 3
                            + 45 * (betta + 6) * (betta + 7) * (betta + 8) * (betta + 9) * exp(-4 * cyt) / 24
                            - 11 * (betta + 6) * (betta + 7) * (betta + 8) * (betta + 9) * (betta + 10) * exp(-5 * cyt) / 120);
        }
    };

    //Integral particular cases

    private Formula integral_0 = new Formula() {
        @Override
        public double eval(int k, double betta, double tao, double gamma) {
            int n = 1;
            double pref = (-2 / gamma) * exp(-c * gamma * tao / 2);
            double sum = 0;
            for (int j = 0; j <= n; j++) {
                sum += pow(4 / c * gamma, j) * pow(tao, n - j) / Gamma.factorial(n - j);
            }
            return pref * sum;
        }
    };


}