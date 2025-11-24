package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayTabulatedFunctionTest {

    @Test
    void interpolationAndExtrapolation() {
        double[] xs = {0.0, 1.0, 2.0};
        double[] ys = {0.0, 2.0, 8.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);

        assertEquals(0.0, f.apply(0.0), 1e-9);
        assertEquals(2.0, f.apply(1.0), 1e-9);
        assertEquals(1.0, f.apply(0.5), 1e-9);
        assertEquals(5.0, f.apply(1.5), 1e-9);

        double leftExpected = 0.0 + (2.0 - 0.0) * ((-1.0 - 0.0) / (1.0 - 0.0));
        assertEquals(leftExpected, f.apply(-1.0), 1e-9);

        double rightExpected = 2.0 + (8.0 - 2.0) * ((3.0 - 1.0) / (2.0 - 1.0));
        assertEquals(rightExpected, f.apply(3.0), 1e-9);
    }

    @Test
    void insertAndRemove() {
        double[] xs = {0.0, 2.0};
        double[] ys = {0.0, 4.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);

        f.insert(1.0, 2.0);
        assertEquals(3, f.getCount());
        assertEquals(1.0, f.getX(1), 1e-9);
        assertEquals(2.0, f.getY(1), 1e-9);

        f.remove(1);
        assertEquals(2, f.getCount());
        assertEquals(4.0, f.getY(1), 1e-9);
    }
}
