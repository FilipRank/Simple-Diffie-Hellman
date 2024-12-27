package main;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class Main {

    public Main(BigInteger p, BigInteger g, BigInteger a, BigInteger b) {

        System.out.println("Alisa: Šaljem parametre: ");
        System.out.println("p = " + p);
        System.out.println("g = " + g);
        BigInteger A = g.modPow(a, p);
        System.out.println("A = " + A);
        System.out.println();
        System.out.println("Bob: Primio sam parametre p, g i A");
        System.out.println("Bob: Saljem B.");
        BigInteger B = g.modPow(b, p);
        System.out.println("B = " + B);
        System.out.println("Alisa: Primila sam B.");
        BigInteger Bkey = A.modPow(b, p);
        BigInteger Akey = B.modPow(a, p);
        System.out.println("Bob: Dobio sam ovaj simetricni kljuc: " + Bkey);
        System.out.println("Alisa: Dobila sam ovaj simetricni kljuc: " + Akey);


    }

    // public static void main(String[] args) {
    //
    //     SecureRandom secureRandom = new SecureRandom();
    //
    //     BigInteger p = BigInteger.probablePrime(4, secureRandom);
    //     BigInteger g = findLowestPrimitiveRoot(p);
    //
    //
    //
    //     System.out.println("Prosti moduo p: " + p);
    //     System.out.println("Lista svih primitivnih korena od p: " +
    //             findAllPrimitiveRoots(p));
    //
    //     System.out.println();
    //     System.out.println("Dobijeni nizovi za g^i mod p, gde i = 0 -> 12 :");
    //     System.out.println();
    //     System.out.println("Gde je g primitivni koren, to jest, g = " + g);
    //     for (int i = 1; i < p.intValue(); i++) {
    //         System.out.print(g.modPow(BigInteger.valueOf(i), p) + " ");
    //     }
    //     System.out.println();
    //     System.out.println();
    //     System.out.println("Gde g nije primitivni koren, to jest, g = " + 5);
    //     for (int i = 1; i < p.intValue(); i++) {
    //         System.out.print(BigInteger.valueOf(5).modPow(BigInteger.valueOf(i), p) + " ");
    //     }
    //
    //
    // }

    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        final int PRIVATE_KEY_BIT_LENGTH = 4;
        long startTime = System.currentTimeMillis();

        // Generate random prime p
        BigInteger p = BigInteger.probablePrime(PRIVATE_KEY_BIT_LENGTH, secureRandom);
        // Lowest primitive root g for p
        // BigInteger g = findLowestPrimitiveRoot(p);
        BigInteger g = BigInteger.valueOf(2);

        // Generate random private keys a and b
        BigInteger a = new BigInteger(PRIVATE_KEY_BIT_LENGTH, secureRandom);
        BigInteger b = new BigInteger(PRIVATE_KEY_BIT_LENGTH, secureRandom);
        //
        // // Alice calculates public key A
        // BigInteger A = g.modPow(a, p);
        //
        // // Bob calculates public key B
        // BigInteger B = g.modPow(b, p);
        //
        // // Alice calculates symmetric key Ka given B
        // BigInteger Ka = B.modPow(a, p);
        //
        // // Bob calculates symmetric key Kb given A
        // BigInteger Kb = A.modPow(b, p);
        //
        // System.out.println("Alice's symmetric key: " + Ka);
        // System.out.println("Bob's symmetric key: " + Kb);
        // System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");

    }

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
