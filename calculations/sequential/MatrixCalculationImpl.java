package calculations.sequential;

import calculations.core.Formula;
import calculations.core.Formulas;
import org.ejml.simple.SimpleMatrix;

public class MatrixCalculationImpl {

    private int formula;
    private int orderOfFunction;
    private double minError;

    public MatrixCalculationImpl(int formula, int order) {
        this.formula = formula;
        this.orderOfFunction = order;
        this.minError = 0.001;
    }

    public MatrixResult[] calculateMatrix(double betta, double gamma) {
        MatrixResult[] results = new MatrixResult[orderOfFunction + 1];
        for (int k = 0; k <= orderOfFunction; k++) {
            double deltaTao = Math.sqrt(8 * minError / Math.abs(Formulas.derivativeMax(k, betta, gamma)));

            double first = Formulas.getFormula(formula, k, betta, 0, gamma);
            double current = Formulas.getFormula(formula, k, betta, deltaTao, gamma);
            double nextTao = 2 * deltaTao;

            int countSmaller = 0;
            int smallerThreshold = 500;
            while (true) {
                double next = Formulas.getFormula(formula, k, betta, nextTao, gamma);
                if (Math.signum(current - first) != Math.signum(next - current)) {
                    if (Math.abs(current) < minError) break;
                }
                if (Math.abs(current) < minError) {
                    countSmaller++;
                    if (countSmaller >= smallerThreshold) break;
                } else {
                    countSmaller = 0;
                }

                first = current;
                current = next;
                nextTao += deltaTao;
            }
            int N = (int) Math.floor(nextTao / deltaTao + 0.5);
            double[] array = new double[N];
            for (int i = 0; i < N; i++) {
                double tao = deltaTao * i;
                array[i] = Formulas.getFormula(formula, k, betta, tao, gamma);
            }
            results[k] = new MatrixResult(array, deltaTao);
        }
        return results;
    }

    public SimpleMatrix transposePMatrix(SimpleMatrix simpleMatrix) {
        return simpleMatrix.transpose();
    }

    public SimpleMatrix multiplyPMatrixByPTransposedAndInversed(SimpleMatrix simpleMatrix, SimpleMatrix transposedMatrix) {
        SimpleMatrix mult = simpleMatrix.mult(transposedMatrix);
        return mult.invert();
    }

    public double calcMaxError(Formula[] formulas, MatrixResult[] numericalSolution, double betta, double gamma) {
        double maxError = 0;
        for (int i = 0; i < Math.min(numericalSolution.length, formulas.length); i++) {
            double[] realResult = new double[numericalSolution[i].getResult().length];
            for (int j = 0; j < realResult.length; j++) {
                double tao = j * numericalSolution[i].getStep();
                realResult[j] = formulas[i].eval(i, betta, tao, gamma);
            }
            double diff = numericalSolution[i].calculateMaxDiff(realResult);
            maxError = Math.max(maxError, diff);
        }
        return maxError;
    }
}

