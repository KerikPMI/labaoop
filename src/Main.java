import functions.IdentityFunction;

public class Main {
    public static void main(String[] args) {
        IdentityFunction f = new IdentityFunction();

        System.out.println(f.apply(5));
        System.out.println(f.apply(-3.14));
        System.out.println(f.apply(0));

    }
}
