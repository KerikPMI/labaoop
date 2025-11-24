package functions;

import java.util.Arrays;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable {

    private double[] xValues;
    private double[] yValues;
    private int count;

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null) throw new IllegalArgumentException("Null arrays");
        if (xValues.length != yValues.length) throw new IllegalArgumentException("Lengths differ");
        if (xValues.length == 0) throw new IllegalArgumentException("Empty arrays not allowed");

        for (int i = 1; i < xValues.length; i++) {
            if (xValues[i] <= xValues[i - 1]) throw new IllegalArgumentException("x must be strictly increasing");
        }

        this.count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, count);
        this.yValues = Arrays.copyOf(yValues, count);
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (source == null) throw new IllegalArgumentException("source is null");
        if (count <= 0) throw new IllegalArgumentException("count <= 0");
        if (xFrom > xTo) {
            double t = xFrom; xFrom = xTo; xTo = t;
        }
        this.count = count;
        xValues = new double[count];
        yValues = new double[count];

        if (count == 1) {
            xValues[0] = xFrom;
            yValues[0] = source.apply(xFrom);
        } else {
            double step = (xTo - xFrom) / (count - 1);
            for (int i = 0; i < count; i++) {
                double x = xFrom + step * i;
                xValues[i] = x;
                yValues[i] = source.apply(x);
            }
        }
    }

    @Override
    public int getCount() { return count; }

    @Override
    public double getX(int index) { checkIndex(index); return xValues[index]; }

    @Override
    public double getY(int index) { checkIndex(index); return yValues[index]; }

    @Override
    public void setY(int index, double value) { checkIndex(index); yValues[index] = value; }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; i++) if (Double.compare(xValues[i], x) == 0) return i;
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; i++) if (Double.compare(yValues[i], y) == 0) return i;
        return -1;
    }

    @Override
    public double leftBound() { return xValues[0]; }

    @Override
    public double rightBound() { return xValues[count - 1]; }

    private void checkIndex(int index) {
        if (index < 0 || index >= count) throw new IndexOutOfBoundsException("index=" + index);
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x <= xValues[0]) return 0;
        if (x >= xValues[count - 1]) return count;
        int lo = 0, hi = count - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (xValues[mid] < x) lo = mid + 1;
            else hi = mid - 1;
        }
        return Math.max(0, hi);
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) return yValues[0];
        return interpolate(x, xValues[0], xValues[1], yValues[0], yValues[1]);
    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) return yValues[0];
        return interpolate(x, xValues[count - 2], xValues[count - 1], yValues[count - 2], yValues[count - 1]);
    }

    @Override
    public void insert(double x, double y) {
        int idx = indexOfX(x);
        if (idx != -1) { setY(idx, y); return; }

        int pos = 0;
        while (pos < count && xValues[pos] < x) pos++;

        double[] nx = new double[count + 1];
        double[] ny = new double[count + 1];

        System.arraycopy(xValues, 0, nx, 0, pos);
        System.arraycopy(yValues, 0, ny, 0, pos);

        nx[pos] = x; ny[pos] = y;

        if (pos < count) {
            System.arraycopy(xValues, pos, nx, pos + 1, count - pos);
            System.arraycopy(yValues, pos, ny, pos + 1, count - pos);
        }

        xValues = nx; yValues = ny; count++;
    }

    @Override
    public void remove(int index) {
        checkIndex(index);
        if (count == 1) {
            xValues = new double[0];
            yValues = new double[0];
            count = 0;
            return;
        }
        double[] nx = new double[count - 1];
        double[] ny = new double[count - 1];

        System.arraycopy(xValues, 0, nx, 0, index);
        System.arraycopy(yValues, 0, ny, 0, index);
        if (index < count - 1) {
            System.arraycopy(xValues, index + 1, nx, index, count - index - 1);
            System.arraycopy(yValues, index + 1, ny, index, count - index - 1);
        }
        xValues = nx; yValues = ny; count--;
    }
}
