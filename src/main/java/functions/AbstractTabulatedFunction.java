package functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {

    protected abstract int floorIndexOfX(double x);

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        if (rightX == leftX) return leftY;
        return leftY + (rightY - leftY) * ((x - leftX) / (rightX - leftX));
    }

    protected abstract double extrapolateLeft(double x);
    protected abstract double extrapolateRight(double x);

    @Override
    public double apply(double x) {
        int n = getCount();
        if (n == 0) throw new IllegalStateException("Tabulated function has no points");
        if (n == 1) return getY(0);

        if (x < leftBound()) return extrapolateLeft(x);
        if (x > rightBound()) return extrapolateRight(x);

        int idx = indexOfX(x);
        if (idx != -1) return getY(idx);

        int floor = floorIndexOfX(x);

        if (floor < 0) floor = 0;
        if (floor >= n - 1) floor = n - 2;

        return interpolate(x, getX(floor), getX(floor + 1), getY(floor), getY(floor + 1));
    }
}
