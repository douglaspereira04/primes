package prng;

import java.math.BigInteger;

/**
 * Classe que implementa o gerador de números
 * pseudo-aleatórios LCG
 * @author douglas
 *
 */
public class LCG implements PRNG{
	
	protected BigInteger xn, a, c, m;
	
	/**
	 * Construtor.
	 * @param x0 {@link BigInteger} que define o número usado como seed.
	 * @param a {@link BigInteger} que define a constante multiplicadora.
	 * @param c {@link BigInteger} que define a constante de incremento.
	 * @param m {@link BigInteger} que define o módulo.
	 */
	public LCG(BigInteger x0, BigInteger a, BigInteger c, BigInteger m) {
		this.xn = x0;
		this.a = a;
		this.c = c;
		this.m = m;
	}

	@Override
	public BigInteger next() {
		xn = xn.multiply(a).add(c).mod(m);
		return xn;
	}
}
