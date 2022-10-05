package analysis;

import java.math.BigInteger;
import java.util.Arrays;
import primalitytester.PrimalityTester;
import prng.PRNG;

/**
 * Classe para realizar análise sobre
 * os resultados de geração de número pseudo-aleatórios
 * e números provavelmente primos
 * @author douglas
 *
 */
public class Analysis {

	protected String algorithm;
	protected int size;
	protected double mean, stddev, min, max;
	protected double[] timeElapsed;
	protected BigInteger[] generatedValues;
	
	/**
	 * Carrega média, desvio padrão, valor mínimo,
	 * valor máximo de um conjunto de valores
	 * @param mean média
	 * @param stddev desvio padrão
	 * @param min valor mínimo
	 * @param max valor máximo
	 * @param values conjunto de valores
	 */
	public Analysis(String algorithm, int size, double mean, double stddev, double min, double max, double[] timeElapsed, BigInteger[] generatedValues) {
		super();
		this.algorithm = algorithm;
		this.size = size;
		this.mean = mean;
		this.stddev = stddev;
		this.min = min;
		this.max = max;
		this.timeElapsed = timeElapsed;
		this.generatedValues = generatedValues;
	}

	/**
	 * Dada uma lista de valores em ponto flutuante,
	 * obtém e média, desvio padrão, menor e maior valor
	 * em um objeto do tipo analysis
	 * @param values
	 * @return
	 */
	public static Analysis analyse(String algorithm, int size, double[] timeElapsed, BigInteger[] generatedValues) {
		
		double[] values = Arrays.copyOf(timeElapsed, timeElapsed.length);
		double sum = 0;
		double mean, variance, stddev, min = Double.MAX_VALUE, max = 0;
		int samples = values.length;

		Arrays.sort(values);

		/*
		 * Média, mínimo e máximo de valores não outliers "Retira" outiliers da lista
		 * marcando-os com -1 já que são valores de tempo decorrido sempre positivos
		 */
		for (int i = 0; i < values.length; i++) {
				sum += values[i];
				if (min > values[i]) {
					min = values[i];
				}
				if (max < values[i]) {
					max = values[i];
				}
		}
		mean = (double) sum / samples;

		sum = 0;
		/*
		 * Variância e desvio padrão desconsiderando outliers
		 */
		for (int i = 0; i < values.length; i++) {
			if (values[i] > -1) {
				sum += Math.pow(values[i] - mean, 2);
			}
		}
		variance = sum / samples;
		stddev = Math.sqrt(variance);

		return new Analysis(algorithm, size, mean, stddev, min, max, timeElapsed, generatedValues);
	}

	/**
	 * Gera n números pseudo-aleatórios com o gerador prng, contabilizando e
	 * retornando os tempos de execução para geração
	 * @param {@link PRNG} prng para geração
	 * @param n      inteiro que indica o número de valores à gerar
	 */
	public static Analysis generationAnalysis(String algorithm, int size, PRNG prng, int n) {
		long start;
		double[] timeElapsed = new double[n];
		BigInteger[] randomNumbers = new BigInteger[n];

		for (int i = 0; i < n; i++) {
			start = System.nanoTime();

			randomNumbers[i] = prng.next();

			timeElapsed[i] = (System.nanoTime() - start) / 1000.0;
		}

		return Analysis.analyse(algorithm, size, timeElapsed, randomNumbers);
	}
	


	/**
	 * Por n vezes, 
	 * gera números pseudo-aleatórios com o gerador prng
	 * até que se satisfaça a condição de testador pt
	 * contabilizando e retornando os n tempos de execução 
	 * para encontrar os n prováveis primos
	 * 
	 * @param {@link PrimalityTester} pt para teste dos números gerados
	 * @param {@link PRNG} prng para geração
	 * @param n inteiro que indica o número de prováveis primos que devem
	 * ser encontrados
	 */
	public static Analysis primalityTestAnalysis(String algorithm, int size, PrimalityTester pt, PRNG prng, int n) {

		long start;
		double[] timeElapsed = new double[n];
		BigInteger[] randomNumbers = new BigInteger[n];

		for (int i = 0; i < n; i++) {
			start = System.nanoTime();

			boolean notPrime = true;
			while (notPrime) {
				randomNumbers[i] = prng.next();
				if (randomNumbers[i].testBit(0)) {
					notPrime = !pt.isPrime(randomNumbers[i]);
				}
			}
			
			timeElapsed[i] = (System.nanoTime() - start) / 1000.0;
		}

		return Analysis.analyse(algorithm, size, timeElapsed, randomNumbers);
	}
	
	@Override
	public String toString() {
		return "Média: " + mean + "; Desvio padrão: " + stddev + "; Min: " + min + "; Max: " + max;
	}
	
	public String getAlgorithm() {
		return algorithm;
	}

	public int getSize() {
		return size;
	}

	public double getMean() {
		return mean;
	}

	public double getStddev() {
		return stddev;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double[] getTimeElapsed() {
		return timeElapsed;
	}

	public BigInteger[] getGeneratedValues() {
		return generatedValues;
	}

	protected static int midIndex(int l, int r) {
		int n = r - l + 1;
		n = (n + 1) / 2 - 1;
		return n + l;
	}
}
