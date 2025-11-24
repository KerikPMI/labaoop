package functions;

public class ConstantFunction implements MathFunction {
    private final double c;

    public ConstantFunction(double c) {
        this.c = c;
    }

    public double getConstant() {
        return c;
    }

    @Override
    public double apply(double x) {
        return c;
    }
}
