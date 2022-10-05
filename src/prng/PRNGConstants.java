package prng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.FileSystems;

public class PRNGConstants {
	
	/**
	 * https://primes.utm.edu/curios/index.php?start=143&stop=700
	 * Retorna valor para x0, p e q.
	 * Os valores estão salvos no arquivos em prng/constants/
	 * com nome de acordo com o tamanho de geração.
	 * x0 é primo ou um primo vezes uma potencia de 2;
	 * p e q são primos congruentes à 3 mod 4;
	 * Para um valor de size, p*q é coprimo à x0.
	 * É claro que eu poderia ter salvo M diretamente,
	 * mas quis salvar p e q para apresentar M exatamente
	 * como o produto de primos congruentes à 3 (mod 4)
	 * @return vetor de dois BigIntegers contendo p e q
	 */
	public static BigInteger[] getX0PQ(int size) {
		BigInteger x0,p,q;
		
		File file = null;
		BufferedReader br = null;
		URL url = null;
		
		try {
			url = PRNGConstants.class.getResource("constants"+FileSystems.getDefault().getSeparator()+size);
			file = new File(url.getPath());
			br = new BufferedReader(new FileReader(file));
			x0 = new BigInteger(br.readLine());
			p = new BigInteger(br.readLine());
			q = new BigInteger(br.readLine());

			br.close();
			return new BigInteger[] {x0,p,q};
		} catch (Exception e) {
		  e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}