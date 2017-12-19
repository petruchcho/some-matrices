package calculations;

import calculations.core.Formulas;
import calculations.core.ParticularCases;
import calculations.core.Result;
import calculations.parallel.MatrixParallelCalculationImpl;
import calculations.sequential.MatrixCalculationImpl;
import calculations.sequential.MatrixResult;
import org.ejml.simple.SimpleMatrix;

public class Api {

    private static final boolean USE_PARALLEL = false;

    public static Result completeResult(int formula, double gamma, double betta, int order) {
        Result result = new Result();

        calcConsecutive(formula, gamma, betta, order, result);

        if (USE_PARALLEL) {
            calcParallel(formula, gamma, betta, order, result);
        }

        return result;
    }

    private static Result calcConsecutive(int formula, double gamma, double betta, int order, Result result) {
        long time = System.currentTimeMillis();
        MatrixCalculationImpl mc = new MatrixCalculationImpl(formula, order);
        MatrixResult[] matrixResult = mc.calculateMatrix(betta, gamma);
        result.timeMatrixCons = System.currentTimeMillis() - time;

        time = System.currentTimeMillis();
        SimpleMatrix simpleMatrix = new SimpleMatrix(MatrixResult.convertToRectMatrix(matrixResult));
        SimpleMatrix transposedMatrix = mc.transposePMatrix(simpleMatrix);
        result.timeMatrixTCons = System.currentTimeMillis() - time;

        time = System.currentTimeMillis();
        mc.multiplyPMatrixByPTransposedAndInversed(simpleMatrix, transposedMatrix);
        result.timeMatrixInversCons = System.currentTimeMillis() - time;

        time = System.currentTimeMillis();
        result.testMaxError = mc.calcMaxError(new ParticularCases(Formulas.C).getParticularCases(formula), matrixResult, betta, gamma);
        result.timeMatrixInversCons = System.currentTimeMillis() - time;
        return result;
    }

    private static Result calcParallel(int formula, double gamma, double betta, int order, Result result) {
        long time = System.currentTimeMillis();
        MatrixParallelCalculationImpl mc = new MatrixParallelCalculationImpl(formula, order);
        double[][] matrixResult = mc.toRectangleMatrix(mc.calculateMatrix(betta, gamma));
        result.timeMatrixParal = System.currentTimeMillis() - time;

        time = System.currentTimeMillis();
        mc.transposePMatrix(matrixResult);
        result.timeMatrixTParal = System.currentTimeMillis() - time;

        time = System.currentTimeMillis();
        matrixResult = mc.multiplyPMatrixByPTransposed(matrixResult);
        //TODO new SimpleMatrix(matrixResult).invert();
        result.timeMatrixInversParal = System.currentTimeMillis() - time;

        return result;
    }
}
