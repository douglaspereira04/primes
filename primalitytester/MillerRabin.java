package primalitytester;

import java.math.BigInteger;

import prng.PRNG;

public class MillerRabin implements PrimalityTester{
	
	protected int k;
	protected PRNG prg;
	
	/**
	 * Testador de primalidade MillerRabin com k rounds
	 * @param k inteiro que indica o número de bases à 
	 * à testar (proporcional à precisão dos testes)
	 * @param prg {@link PRNG} gerador de números aleatórios
	 * para gerar as bases a
	 */
	public MillerRabin(int k, PRNG prg) {
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
		BigInteger d, valueMinusOne,valueMinusFour;
		int s = 1;
		d = PrimalityTester.ZERO;
		valueMinusOne = value.subtract(PrimalityTester.ONE);
		valueMinusFour = value.subtract(PrimalityTester.FOUR);
		
		//complexidade 
		while(!d.testBit(0)) {
			d = value.subtract(PrimalityTester.ONE).divide(PrimalityTester.TWO.pow(s));
            s++;
		}
		
		//k
		WitnessLoop: for (int i = 0; i < k; i++) {
			BigInteger a = prg.next().mod(valueMinusFour).add(PrimalityTester.TWO);
			BigInteger x = a.modPow(d, value);
			
			
			if(x.equals(PrimalityTester.ONE) || x.equals(valueMinusOne)) {
				continue WitnessLoop;
				
			}
			for (int j = 0; j < s-1; j++) {
				x = x.modPow(PrimalityTester.TWO, value);
				if(x.equals(valueMinusOne)) {
					continue WitnessLoop;
				}
			}
			return false;
		}
		return true;
	}

}
