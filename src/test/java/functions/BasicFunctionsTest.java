package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicFunctionsTest {

    @Test
    void constantZeroUnit() {
        MathFunction c = new ConstantFunction(2.5);
        assertEquals(2.5, c.apply(0.0), 1e-9);
        assertEquals(0.0, new ZeroFunction().apply(999.0), 1e-9);
        assertEquals(1.0, new UnitFunction().apply(-666.0), 1e-9);
    }

    @Test
    void sqrFunction() {
        MathFunction s = new SqrFunction();
        assertEquals(9.0, s.apply(3.0), 1e-9);
        assertEquals(4.0, s.apply(-2.0), 1e-9);
    }
}
