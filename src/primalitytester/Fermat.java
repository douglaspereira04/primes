package primalitytester;

import java.math.BigInteger;
import prng.PRNG;

public class Fermat implements PrimalityTester {
	protected int k;
	protected PRNG prg;

	
	/**
	 * Testador de primalidade de Fermat com k rounds
	 * @param k inteiro que indica o número de bases à 
	 * à testar (proporcional à precisão dos testes)
	 * @param prg {@link PRNG} gerador de números aleatórios
	 * para gerar as bases a
	 */
	public Fermat(int k, PRNG prg) {
		this.k = k;
		this.prg = prg;

	}

	/**
	 * Testa se um valor provavelmente é um primo
	 * @param value {@link BigInteger} com o valor as ser testado
	 * @return boolean indicando se provavelmente é primo
	 */
	@Override
	public boolean isPrime(BigInteger value) {
		BigInteger valueMinusOne,valueMinusFour;
		valueMinusOne = value.subtract(PrimalityTester.ONE);
		valueMinusFour = value.subtract(PrimalityTester.FOUR);

		if(value.compareTo(PrimalityTester.FOUR) == -1) {
			if(value.compareTo(PrimalityTester.ONE) == 1) {
				return true;
			}
			return false;
		}

		//k
		for (int i = 0; i < k; i++) {
			BigInteger a = prg.next().abs().mod(valueMinusFour).add(PrimalityTester.TWO);
			
			//potência: no máximo n
			if (!a.modPow(valueMinusOne, value).equals(PrimalityTester.ONE))
				return false;
		}

		return true;
	}

}
