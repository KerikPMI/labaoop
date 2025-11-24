package functions;

public interface TabulatedFunctionFactory {
    ArrayTabulatedFunction create(double[] xValues, double[] yValues);
    ArrayTabulatedFunction create(MathFunction src, double xFrom, double xTo, int count);
}
