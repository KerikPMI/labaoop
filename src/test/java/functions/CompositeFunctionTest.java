package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompositeFunctionTest {

    @Test
    void composeChain() {
        MathFunction f = new SqrFunction(); // x^2
        MathFunction g = x -> x + 1;       // x+1
        MathFunction h = new ConstantFunction(7.0); // 7

        MathFunction comp = f.andThen(g).andThen(h);
        assertEquals(7.0, comp.apply(2.0), 1e-9);
    }

    @Test
    void compositeDirect() {
        CompositeFunction comp = new CompositeFunction(new IdentityFunction(), new SqrFunction());
        assertEquals(4.0, comp.apply(2.0), 1e-9);
    }
}
