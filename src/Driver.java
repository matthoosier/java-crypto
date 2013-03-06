import java.math.BigInteger;

public class Driver {

	public static void main(String[] args) {

		Key key = Key.create(128);

		System.out.println("p: " + key.p);
		System.out.println("q: " + key.q);
		System.out.println("(p - 1)(q - 1): " + key.phi);
		
		System.out.println("Private key: (n = " + key.n.toString(16) + ", e = " + key.e.toString(16) + ")");
		System.out.println("Private key: (n = " + key.n.toString(16) + ", d = " + key.d.toString(16) + ")");

		/* Run some sample data through it. */
		BigInteger message = BigInteger.valueOf(65);
		message = new BigInteger("The lazy black dogs".getBytes());
		BigInteger c = encipher(message, key.e, key.n);
		BigInteger m = decipher(c, key.d, key.n);

		System.out.println("Original message: " + message.toString(16));
		System.out.println("Ciphertext: " + c.toString(16));
		System.out.println("Recovered message: " + m.toString(16));
		System.out.println("String form: " + new String(m.toByteArray()));
	}

	static BigInteger encipher(BigInteger cleartext, BigInteger e,
			BigInteger modulus) {
		
		if (cleartext.compareTo(modulus) >= 0) {
			throw new ArithmeticException("Clear-text must not be numerically larger than modulus");
		}
		
		return Power.modPow(cleartext, e, modulus);
	}

	static BigInteger decipher(BigInteger cyphertext, BigInteger d, BigInteger n) {
		return Power.modPow(cyphertext, d, n);
	}

}
