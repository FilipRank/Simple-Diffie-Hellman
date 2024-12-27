package util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Util {

    public static List<BigInteger> findAllPrimitiveRoots(BigInteger p) {
        List<BigInteger> primitiveRoots = new ArrayList<>();

        BigInteger lpr = findLowestPrimitiveRoot(p);
        primitiveRoots.add(lpr);

        BigInteger s = p.subtract(BigInteger.ONE);

        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(p) == -1; i = i.add(BigInteger.ONE)) {
            if (i.gcd(s).equals(BigInteger.ONE))
                primitiveRoots.add(lpr.modPow(i, p));
        }

        return primitiveRoots;
    }

    public static BigInteger findLowestPrimitiveRoot(BigInteger p) {
        BigInteger a = BigInteger.valueOf(2);

        while(!isPrimitiveRoot(a, p)) {
            a = a.add(BigInteger.ONE);
        }

        return a;
    }

    public static boolean isPrimitiveRoot(BigInteger a, BigInteger p) {
        BigInteger s = p.subtract(BigInteger.ONE);
        List<BigInteger> primeFactors = getPrimeFactors(s);

        for (BigInteger primeFactor : primeFactors) {
            if (a.modPow(s.divide(primeFactor), p).equals(BigInteger.ONE))
                return false;
        }

        return true;
    }

    public static List<BigInteger> getPrimeFactors(BigInteger s) {
        Set<BigInteger> primeFactors = new LinkedHashSet<>();

        // Check for divisibility by 2
        while (s.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            primeFactors.add(BigInteger.valueOf(2));
            s = s.divide(BigInteger.valueOf(2));
        }

        // Check for divisibility by odd numbers from 3 to sqrt(s)
        BigInteger i = BigInteger.valueOf(3);
        while (i.multiply(i).compareTo(s) <= 0) {
            while (s.mod(i).equals(BigInteger.ZERO)) {
                primeFactors.add(i);
                s = s.divide(i);
            }
            i = i.add(BigInteger.valueOf(2)); // Increment by 2 to check next odd number
        }

        // If s is still greater than 2, it must be prime
        if (s.compareTo(BigInteger.valueOf(2)) > 0) {
            primeFactors.add(s);
        }

        List<BigInteger> result = new ArrayList<>();
        result.addAll(primeFactors);
        return result;
    }

}
