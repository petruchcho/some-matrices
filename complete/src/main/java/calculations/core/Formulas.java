package calculations.core;

public class Formulas {

    public static final int P = 0, DERIVATE = 1, INTEGRAL = 2;
    public static final double C = 2;

    private Formulas() {
    }

    public static double derivate(int k, double betta, double tao, double gamma12) {
        double sum = 0;
        double sign = 1;
        for (int s = 0; s <= k; s++) {
            sum += sign * C(k, s) * doubleC(k + s + betta, s) * pow(2 * s + 1, 1) * Math.exp(-(2 * s + 1) * C * gamma12 * tao / 2);

            sign *= -1;
        }
        return pow(-C * gamma12 / 2, 1) * sum;
    }

    public static double p(int k, double betta1, double tao, double gamma1) {
        double res = 0;
        int sign = 1;
        for (int s = 0; s <= k; s++) {
            res += C(k, s) * doubleC(k + s + betta1, s) * sign * Math.exp(-(2 * s + 1) * 2 * gamma1 * tao / 2);

            sign *= -1;
        }
        return res;
    }

    public static double integral(int k, double betta13, double tao, double gamma13) {
        int n = 1;
        double res = 0;
        double sign = 1;
        for (int s = 0; s <= k; s++) {
            double binoms = C(k, s) * doubleC(k + s + betta13, s);
            double exps = sign * Math.exp(-(2 * s + 1) * C * gamma13 * tao / 2);
            double rowSum = 0;
            for (int j = 0; j <= n; j++) {
                rowSum += f(n) * pow(tao, n - j) / (f(n - j) * pow(C * gamma13 * (2 * s + 1) / 2, j + 1));
            }
            res += binoms * exps * rowSum;
            sign *= -1;
        }
        return res;
    }

    //formula 1.65
    public static double derivativeMax(int k, double betta, double gamma) {
        return derivativeCommon(2, k, betta, 0, gamma);
    }

    private static double derivativeCommon(int n, int k, double betta, double tao, double gamma) {
        double sum = 0;
        double sign = 1;
        for (int s = 0; s <= k; s++) {
            sum += sign * c(k, s) * c(k + s + betta, s) * power(2 * s + 1, n) * Math.exp(-(2 * s + 1) * C * gamma * tao / 2);
            sign *= -1;
        }
        return power(-C * gamma / 2, n) * sum;
    }

    private static double c(double n, double k) {
        return Gamma.gamma(n + 1) / Gamma.gamma(k + 1) / Gamma.gamma(n - k + 1);
    }

    private static double power(double x, int n) {
        return Math.pow(x, n);
    }

    private static double[][] cacheC = new double[100][100];

    private static double C(int n, int k) {
        if (cacheC[n][k] != 0) return cacheC[n][k];
        return cacheC[n][k] = f(n) / f(k) / f(n - k);
    }

    private static double doubleC(double n, double k) {
        return Gamma.gamma(n + 1) / Gamma.gamma(k + 1) / Gamma.gamma(n - k + 1);
        //return Gamma.gamma(n) / Gamma.gamma(k) / Gamma.gamma(n - k);
    }

    private static double f(int n) {
        if (n == 0) return 1;
        return f(n - 1) * n;
    }

    private static double pow(double x, int n) {
        if (n == 0) return 1;
        if (n == 1) return x;
        if (n == 2) return x * x;
        return Math.pow(x, n);
    }

    public static double getFormula(int formula, int k, double betta, double tao, double gamma) {
        if (formula == Formulas.P) return Formulas.p(k, betta, tao, gamma);
        if (formula == Formulas.DERIVATE) return Formulas.derivate(k, betta, tao, gamma);
        if (formula == Formulas.INTEGRAL) return Formulas.integral(k, betta, tao, gamma);
        throw new RuntimeException();
    }
}
