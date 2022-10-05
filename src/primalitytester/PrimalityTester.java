package primalitytester;

import java.math.BigInteger;

/**
 * Interface para classes que testam
 * a primalidade de números
 * @author douglas
 *
 */
public interface PrimalityTester {
	public static BigInteger ZERO = BigInteger.valueOf(0);
	public static BigInteger ONE = BigInteger.valueOf(1);
	public static BigInteger TWO = BigInteger.valueOf(2);
	public static BigInteger FOUR = BigInteger.valueOf(4);
	/**
	 * Testa se um valor é (ou provavelmente é) um primo
	 * @param value {@link BigInteger} com o valor as ser testado
	 * @return boolean indicando se é (ou provavelmente é) primo
	 */
	public boolean isPrime(BigInteger value);
}
