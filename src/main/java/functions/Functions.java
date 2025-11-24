package functions;

public final class Functions {
    private Functions() {}

    public static TabulatedFunction tabulate(MathFunction f, double from, double to, int count, boolean useArray) {
        if (useArray) return new ArrayTabulatedFunction(f, from, to, count);
        else return new LinkedListTabulatedFunction(f, from, to, count);
    }
}
