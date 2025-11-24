package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentityFunctionTest {
    @Test
    void identityWorks() {
        MathFunction f = new IdentityFunction();
        assertEquals(5.0, f.apply(5.0), 1e-9);
        assertEquals(-3.14, f.apply(-3.14), 1e-9);
        assertEquals(0.0, f.apply(0.0), 1e-9);
    }
}
