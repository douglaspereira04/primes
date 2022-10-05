package main;

import java.io.FileNotFoundException;
import java.math.BigInteger;

import analysis.Analysis;
import display.Table;
import primalitytester.Fermat;
import primalitytester.MillerRabin;
import primalitytester.PrimalityTester;
import prng.BBS;
import prng.LCG;
import prng.PRNG;
import prng.PRNGConstants;

public class Main {

	/**
	 * Tamanhos dos valores gerados
	 */
	public static int[] SIZE = { 40, 56, 80, 128, 224, 256, 512, 1024, 2048, 4096 };

	
	/**
	 * Executa os testes
	 * @param args vetor de {@link String} que na posição 0 deve ter
	 * o número de valores que devem ser gerados por cada gerador 
	 * de números pseudo-aleatórios, na posição 1 deve ter o número
	 * de prováveis primos aprovados por cada teste de primalidade
	 * e na posição 2 tem o valor de estágios para os testes de 
	 * primalidade
	 * 
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		int generationAmount = Integer.valueOf(args[0]);
		int primeAmount = Integer.valueOf(args[1]);
		int primalityPrecision = Integer.valueOf(args[2]);
		
		BigInteger x0;
		BigInteger a, c, m;
		BigInteger M, p, q;	

		PRNG lcg;
		PRNG bbs;
		PrimalityTester millerRabin;
		PrimalityTester fermat;

		/*
		 * Mesmos parâmetros usados em java.util.Random, exceto por m, que varia de
		 * acordo com o tamanho do número que queremos gerar, fazendo-o sempre potência
		 * de 2 para não ferir os requisitos do teorema de Hull–Dobell
		 */
		a = new BigInteger("25214903917");
		c = new BigInteger("11");

		Analysis[] generationAnalyses = new Analysis[(SIZE.length)*2];
		Analysis[] primalityAnalyses = new Analysis[(SIZE.length)*2];
		
		for (int i = 0; i < SIZE.length; i++) {
			System.out.println("Generation test: " + SIZE[i]);
			
			BigInteger[] x0pq = PRNGConstants.getX0PQ(SIZE[i]);
			x0 = x0pq[0];
			p = x0pq[1];
			q = x0pq[2];
			M = p.multiply(q);
			bbs = new BBS(x0, M, SIZE[i]);
			
			m = new BigInteger("2").pow(SIZE[i]);
			lcg = new LCG(x0, a, c, m);

			millerRabin = new MillerRabin(primalityPrecision, lcg);
			fermat = new Fermat(primalityPrecision, lcg);

			Analysis lcgAnalysis = Analysis.generationAnalysis(lcg.getClass().getSimpleName(), SIZE[i], lcg, generationAmount);
			Analysis bbsAnalysis = Analysis.generationAnalysis(bbs.getClass().getSimpleName(), SIZE[i], bbs, generationAmount);
			Analysis millerRabinAnalysis = Analysis.primalityTestAnalysis(millerRabin.getClass().getSimpleName(), SIZE[i], millerRabin, lcg, primeAmount);
			Analysis fermatAnalysis = Analysis.primalityTestAnalysis(fermat.getClass().getSimpleName(), SIZE[i], fermat, lcg, primeAmount);

			System.out.println(lcg.getClass().getSimpleName());
			System.out.println(lcgAnalysis.toString());
			System.out.println(bbs.getClass().getSimpleName());
			System.out.println(bbsAnalysis.toString());
			System.out.println(millerRabin.getClass().getSimpleName());
			System.out.println(millerRabinAnalysis.toString());
			System.out.println(fermat.getClass().getSimpleName());
			System.out.println(fermatAnalysis.toString());
			System.out.println("--------------------------------------------");
			
			generationAnalyses[2*i] = lcgAnalysis;
			generationAnalyses[(2*i)+1] = bbsAnalysis;
			primalityAnalyses[2*i] = millerRabinAnalysis;
			primalityAnalyses[(2*i)+1] = fermatAnalysis;

		}
		
		Table.generalStatisticsTable(generationAnalyses, "Geração de números pseudo aleatórios");
		Table.generalStatisticsTable(primalityAnalyses, "Geração de números provavelmente primos");
		
		Table.valuesTable(generationAnalyses, "Geração de números pseudo aleatórios");
		Table.valuesTable(primalityAnalyses, "Geração de números provavelmente primos");
	}

	public static void warm() {
		BigInteger x0;
		BigInteger M, p, q;	
		PRNG bbs;
		
		BigInteger[] x0pq = PRNGConstants.getX0PQ(40);
		x0 = x0pq[0];
		p = x0pq[1];
		q = x0pq[2];
		M = p.multiply(q);
		bbs = new BBS(x0, M, 40);
		Analysis bbsAnalysis = Analysis.generationAnalysis(bbs.getClass().getSimpleName(), 40, bbs, 10000);
	}

}

