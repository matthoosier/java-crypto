import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Gcd {

	public static class ExtendedResult {
		public final BigInteger gcd;
		public final BigInteger a;
		public final BigInteger b;
		public final BigInteger x;
		public final BigInteger y;

		public ExtendedResult(BigInteger a, BigInteger b, BigInteger x,
				BigInteger y) {
			this.a = a;
			this.b = b;
			this.x = x;
			this.y = y;

			gcd = a.multiply(x).add(b.multiply(y));
		}
	}

	static ExtendedResult extendedEulerGcd(BigInteger a, BigInteger b) {

		BigInteger origA = a;
		BigInteger origB = b;

		BigInteger x = BigInteger.ZERO;
		BigInteger y = BigInteger.ONE;
		BigInteger lastX = BigInteger.ONE;
		BigInteger lastY = BigInteger.ZERO;

		while (b.compareTo(BigInteger.ZERO) != 0) {
			BigInteger quotient = a.divide(b);

			BigInteger aPrime = b;
			BigInteger bPrime = a.mod(b);

			a = aPrime;
			b = bPrime;

			BigInteger xPrime = lastX.subtract(quotient.multiply(x));
			lastX = x;
			x = xPrime;

			BigInteger yPrime = lastY.subtract(quotient.multiply(y));
			lastY = y;
			y = yPrime;
		}

		return new ExtendedResult(origA, origB, lastX, lastY);
	}

	static BigInteger eulerGcd(BigInteger a, BigInteger b) {

		System.out.println("gcd(" + a + ", " + b + ")...");
		int compareResult = a.compareTo(b);

		if (a.compareTo(BigInteger.ZERO) == 0) {
			return b;
		} else if (b.compareTo(BigInteger.ZERO) == 0) {
			return a;
		} else if (compareResult == 0) {
			// Numbers are the same. Any number is its own GCD if matched with
			// itself.
			return a;
		} else {
			// They're unequal and the result is not trivial.

			@SuppressWarnings("unused")
			BigInteger larger;

			BigInteger smaller;

			if (compareResult < 0) {
				// a is less than b
				larger = b;
				smaller = a;
			} else {
				// b is less than a
				larger = a;
				smaller = b;
			}

			BigInteger remainder = a.mod(b);

			return eulerGcd(smaller, remainder);
		}
	}

	public static BigInteger findMultiplicativeInverse(BigInteger a,
			BigInteger m) {

		if (a.compareTo(BigInteger.ZERO) < 0) {
			throw new ArithmeticException("No multiplicative inverse of " + a
					+ " mod " + m);
		}

		// The Extended Euler GCD algorithm when invoked on parameters "a" and
		// "b",
		// produces two integer results ("x" and "y") such that
		//
		// ax + by == gcd(a, b)
		//
		// and (most importantly for our purposes here) if a and b are coprime,
		// then
		//
		// x == (a ^ -1) mod b
		//
		// That is, x is the multiplicative inverse of a in modulus b.
		//
		BigInteger result;

		Gcd.ExtendedResult extendedResult = Gcd.extendedEulerGcd(a, m);

		// Enforce that they're coprime
		if (!extendedResult.gcd.equals(BigInteger.ONE)) {
			throw new ArithmeticException("Inputs not coprime");
		}

		result = extendedResult.x.mod(m);
		
		// Sanity check
		assert (result.equals(a.modInverse(m)));

		return result;
	}

	private static BigInteger readBigInteger(String prompt, BufferedReader in)
			throws IOException {
		System.out.print(prompt);
		System.out.flush();
		return new BigInteger(in.readLine());
	}

	public static void main(String[] args) throws IOException {

		BufferedReader systemInReader = new BufferedReader(
				new InputStreamReader(System.in));

		BigInteger a = readBigInteger("Input a: ", systemInReader);
		BigInteger b = readBigInteger("Input b: ", systemInReader);

		System.out.println("gcd(" + a + ", " + b + ") = " + eulerGcd(a, b));
	}
}
