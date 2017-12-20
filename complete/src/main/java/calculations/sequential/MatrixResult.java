package calculations.sequential;

public class MatrixResult {

    public MatrixResult(double[] result, double step) {
        this.result = result;
        this.step = step;
    }

    private double[] result;
    private double step;

    public double[] getResult() {
        return result;
    }

    public double getStep() {
        return step;
    }

    double calculateMaxDiff(double[] other) {
        if (other.length != result.length) return Double.MAX_VALUE;
        double maxDiff = 0;
        for (int i = 0; i < other.length; i++) {
            maxDiff = Math.max(maxDiff, Math.abs(result[i] - other[i]));
        }
        return maxDiff;
    }

    public static double[][] convertToRectMatrix(MatrixResult[] matrixResults) {
        int maxSecondDimension = 0;
        for (MatrixResult matrixResult : matrixResults) {
            maxSecondDimension = Math.max(maxSecondDimension, matrixResult.getResult().length);
        }
        double[][] result = new double[matrixResults.length][maxSecondDimension];
        for (int i = 0; i < matrixResults.length; i++) {
            System.arraycopy(matrixResults[i].getResult(), 0, result[i], 0, matrixResults[i].getResult().length);
        }
        return result;
    }
}