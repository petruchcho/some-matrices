package calculations.parallel;

import calculations.core.Formulas;

public class MatrixParallelCalculationImpl {

    private int formula;
    private int orderOfFunction;
    private double minError;
    private MRRunner runner;

    public MatrixParallelCalculationImpl(int formula, int order) {
        this.formula = formula;
        this.orderOfFunction = order;
        this.minError = 0.001;
        this.runner = new MRRunner();
    }

    public double[][] calculateMatrix(double betta, double gamma) {
        double[][] matrix;
        runner.initJacobiCalc(formula, orderOfFunction);

        for (int k = 1; k <= orderOfFunction; k++) {
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

            runner.addInputParamsForKFuncOrder(formula, k, N, gamma, betta, deltaTao);
        }

        matrix = runner.calcJacobiFunction();

        return matrix;
    }

    public double[][] transposePMatrix(double[][] matrix) {
        return runner.transposeMatrix(matrix);
    }

    public double[][] multiplyPMatrixByPTransposed(double[][] matrix) {
        return runner.multiplyMatrixByTransposed(matrix);
    }

    public double[][] toRectangleMatrix(double[][] inMatrix) {
        double[][] matrix = new double[inMatrix.length][];
        int maxElementsCount = inMatrix[inMatrix.length - 1].length;

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new double[maxElementsCount];
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = j < inMatrix[i].length ? inMatrix[i][j] : 0.0;
            }
        }

        return matrix;
    }
}

