import java.math.BigInteger;

public class Power {

	public static BigInteger modPow(BigInteger b, BigInteger e, BigInteger m) {
		BigInteger result;
		
		result = Power.modPowExponentSquaring(b, e, m);
		
		// Sanity check
		assert (result.equals(b.modPow(e, m)));
		
		return result;
	}

	@SuppressWarnings("unused")
	private static BigInteger modPowIterative(BigInteger b, BigInteger e, BigInteger m) {
	
		// Iterative multiplication, modding at each step.
		BigInteger result = BigInteger.ONE;
	
		for (BigInteger i = BigInteger.ONE; i.compareTo(e) <= 0; i = i
				.add(BigInteger.ONE)) {
			result = result.multiply(b).mod(m);
		}
	
		// Sanity check
		assert (result.equals(b.modPow(e, m)));
		return result;
	}

	private static BigInteger modPowExponentSquaring(BigInteger b, BigInteger e, BigInteger m) {
	
		BigInteger result = BigInteger.ONE;
		BigInteger x = b;
		BigInteger n = e;
		
		while (!n.equals(BigInteger.ZERO)) {
			if (n.testBit(0)) {
				result = result.multiply(x).mod(m);
				n = n.subtract(BigInteger.ONE);
			}
			x = x.multiply(x).mod(m);
			
			// This division by two always comes out evenly because the conditional
			// block above moves out any odd value.
			n = n.shiftRight(1);
		}
		
		result = result.mod(m);
		
		// Sanity check
		return result;
	}

}
