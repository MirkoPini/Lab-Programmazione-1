public class FunctionMath {
    public static int abs(int x){
        return Math.abs(x);
    }

    public static double abs(double x){
        return Math.abs(x);
    }

    public static boolean isPrime(int n){
        if (n <= 1) {
            return false;
        }

        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static double hypotenuse(double a, double b){
        return Math.hypot(a, b);
    }
}
