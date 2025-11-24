package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryTest {
    @Test
    void factoryCreates() {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        ArrayTabulatedFunction f = factory.create(new IdentityFunction(), 0.0, 2.0, 3);
        assertEquals(3, f.getCount());
        assertEquals(0.0, f.getX(0), 1e-9);
        assertEquals(2.0, f.getX(2), 1e-9);
    }
}
