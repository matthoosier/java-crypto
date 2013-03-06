import java.math.BigInteger;
import java.util.Random;

public class Key {

	public final BigInteger p;
	public final BigInteger q;
	public final BigInteger n;
	public final BigInteger phi;
	public final BigInteger e;
	public final BigInteger d;

	public Key(BigInteger p, BigInteger q, BigInteger n, BigInteger phi,
			BigInteger e, BigInteger d) {
		this.p = p;
		this.q = q;
		this.n = n;
		this.phi = phi;
		this.e = e;
		this.d = d;
	}

	/**
	 * Computes an RSA public/private keypair whose modulus is at least as large
	 * as 2^(bitLength + 1) - 1
	 * 
	 * This implies that messages whose bitwise representation is no longer than
	 * bitLength bits, can be encoded with this keypair and suffer no
	 * truncation.
	 */
	public static Key create(int bitLength) {

		Random rnd = new Random();

		/* Randomly generated */
		BigInteger p = BigInteger.probablePrime(bitLength, rnd);
		BigInteger q = BigInteger.probablePrime(bitLength, rnd);

		/* Computed */
		BigInteger n = p.multiply(q);
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(
				q.subtract(BigInteger.ONE));

		/* Chosen; not necessary to be unique to the key. */
		BigInteger e = BigInteger.valueOf(17);
		e = new BigInteger("10001", 16);

		assert (e.isProbablePrime(200));
		assert (e.compareTo(phi) < 0);

		/* Computed */
		BigInteger d = Gcd.findMultiplicativeInverse(e, phi);

		return new Key(p, q, n, phi, e, d);
	}

}
