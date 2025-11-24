package functions;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {

    @Override
    public ArrayTabulatedFunction create(double[] xValues, double[] yValues) {
        return new ArrayTabulatedFunction(xValues, yValues);
    }

    @Override
    public ArrayTabulatedFunction create(MathFunction src, double xFrom, double xTo, int count) {
        return new ArrayTabulatedFunction(src, xFrom, xTo, count);
    }
}
