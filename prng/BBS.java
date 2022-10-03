package prng;

import java.math.BigInteger;

/**
 * Classe que implementa o gerador de números
 * pseudo-aleatórios BlumBlumShub
 * @author douglas
 *
 */
public class BBS implements PRNG{
	
	protected BigInteger xn, M;
	protected int size;

	/**
	 * Construtor.
	 * @param x0 {@link BigInteger} que define o número usado como estado inicial.
	 * @param M {@link BigInteger} inteiro que define o módulo.
	 */
	public BBS(BigInteger x0, BigInteger M, int size) {
		this.xn = x0;
		this.M = M;
		this.size = size;
	}
	
	@Override
	public BigInteger next() {
		xn = xn.modPow(PRNG.TWO, M);

		/*
		 * corrige a quantidade de bits, 
		 * mantendo os bits mais significativos
		 */
		int diff = xn.bitLength() - size;
		if(diff > 0) {
			return xn.shiftRight(diff);
		}
		
		return xn;
	}
	
}
