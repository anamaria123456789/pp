package pp.lab;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Polyglot3 {
    private static double binomialProbability(int n, int x) {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        Value result;

        String pythonCode = String.format(
                "from math import comb\n" +
                        "sum(comb(%d, k) * (0.5**%d) for k in range(%d + 1))", n, n, x
        );

        double probability;

        result = polyglot.eval("python", pythonCode);
        probability = result.asDouble();
        polyglot.close();

        return probability;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n;
        int x;

        System.out.print("Enter the number of coin tosses: ");
        n = Integer.parseInt(bufferedReader.readLine());

        System.out.print("Enter the value of x (1 ≤ x ≤ " + n + "): ");
        x = Integer.parseInt(bufferedReader.readLine());

        if (x < 1 || x > n) {
            System.out.println("Invalid input! x must be between 1 and " + n);
        } else {
            double probability = binomialProbability(n, x);
            System.out.printf("Probability of at most %d tails in %d tosses: %.5f%n", x, n, probability);
        }
    }
}