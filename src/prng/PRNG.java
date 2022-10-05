package prng;

import java.math.BigInteger;

/**
 * Interface para geradores de números pseudo-aleatórios
 * @author douglas
 *
 */
public interface PRNG {

	public static BigInteger TWO = BigInteger.valueOf(2);
	/**
	 * Obtém o próximo valor da sequência pseudo-aleatória.
	 * @return {@link BigInteger} com o próximo valor da sequência pseudo-aleatória.
	 */
	public BigInteger next();
}
