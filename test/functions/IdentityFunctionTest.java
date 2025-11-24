package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentityFunctionTest {

    @Test
    void returnsSameForPositive() {
        IdentityFunction f = new IdentityFunction();
        assertEquals(5.0, f.apply(5.0), 1e-9);
    }

    @Test
    void returnsSameForNegative() {
        IdentityFunction f = new IdentityFunction();
        assertEquals(-3.14, f.apply(-3.14), 1e-9);
    }

    @Test
    void returnsSameForZero() {
        IdentityFunction f = new IdentityFunction();
        assertEquals(0.0, f.apply(0.0), 1e-9);
    }
}
