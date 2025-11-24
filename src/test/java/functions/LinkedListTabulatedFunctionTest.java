package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedListTabulatedFunctionTest {

    @Test
    void basicAndInterpolation() {
        double[] xs = {0.0, 2.0, 4.0};
        double[] ys = {0.0, 4.0, 16.0};
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);

        assertEquals(3, f.getCount());
        assertEquals(0.0, f.apply(0.0), 1e-9);
        assertEquals(10.0, f.apply(3.0), 1e-9);

        f.insert(1.0, 1.0);
        assertEquals(4, f.getCount());
        assertEquals(1.0, f.getX(1), 1e-9);
        assertEquals(1.0, f.getY(1), 1e-9);

        int idx = f.indexOfX(1.0);
        f.remove(idx);
        assertEquals(3, f.getCount());
    }
}
