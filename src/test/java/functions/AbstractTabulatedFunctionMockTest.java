package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractTabulatedFunctionMockTest {

    static class MockTab extends AbstractTabulatedFunction {
        private final double[] xs;
        private final double[] ys;

        MockTab(double[] xs, double[] ys) { this.xs = xs; this.ys = ys; }

        @Override public int getCount() { return xs.length; }
        @Override public double getX(int index) { return xs[index]; }
        @Override public double getY(int index) { return ys[index]; }
        @Override public void setY(int index, double value) { ys[index] = value; }
        @Override public int indexOfX(double x) {
            for (int i = 0; i < xs.length; i++) if (Double.compare(xs[i], x) == 0) return i;
            return -1;
        }
        @Override public int indexOfY(double y) {
            for (int i = 0; i < ys.length; i++) if (Double.compare(ys[i], y) == 0) return i;
            return -1;
        }
        @Override public double leftBound() { return xs[0]; }
        @Override public double rightBound() { return xs[xs.length - 1]; }

        @Override protected int floorIndexOfX(double x) {
            if (x <= xs[0]) return 0;
            if (x >= xs[xs.length - 1]) return xs.length;
            for (int i = 0; i < xs.length - 1; i++) if (xs[i] < x && x <= xs[i + 1]) return i;
            return xs.length - 1;
        }

        @Override protected double extrapolateLeft(double x) {
            return interpolate(x, xs[0], xs[1], ys[0], ys[1]);
        }

        @Override protected double extrapolateRight(double x) {
            int n = xs.length;
            return interpolate(x, xs[n - 2], xs[n - 1], ys[n - 2], ys[n - 1]);
        }
    }

    @Test
    void mockInterpolateApply() {
        double[] xs = {1.0, 3.0};
        double[] ys = {2.0, 6.0};
        MockTab m = new MockTab(xs, ys);
        assertEquals(4.0, m.apply(2.0), 1e-9);
        assertEquals(0.0, m.apply(0.0), 1e-9);
        assertEquals(2.0, m.apply(1.0), 1e-9);
    }
}
