package display;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import analysis.Analysis;

/**
 * Classe para apresentar tabelas de valores
 * @author douglas
 *
 */
public class Table {


	public static void generalStatisticsTable(Analysis[] analyses, String title) {
		String[] columns = { "Algoritmo", "Tamanho", "Quantidade", "Média", "Mínimo", "Máximo", "Desvio padrão"};
		Object[][] data = new Object[analyses.length][columns.length];

		JFrame frame = new JFrame(title);
		JPanel panel;
		JTable table;
		JScrollPane scroll;
		
		for (int i = 0; i < analyses.length; i++) {
			data[i][0] = analyses[i].getAlgorithm();
			data[i][1] = analyses[i].getSize();
			data[i][2] = analyses[i].getTimeElapsed().length;
			data[i][3] = analyses[i].getMean();
			data[i][4] = analyses[i].getMin();
			data[i][5] = analyses[i].getMax();
			data[i][6] = analyses[i].getStddev();
		}
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		table = new JTable(data, columns);
		scroll = new JScrollPane(table);
		panel.add(scroll);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension(screenSize.width/2, screenSize.height/2);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(size);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	public static void valuesTable(Analysis[] analyses, String title) {
		String[] columns = { "Algoritmo", "rraTamanho", "Número", "Tempo para gerar" };
		List<Object[]> data = new ArrayList<>();
		
		JFrame frame = new JFrame(title);
		JPanel panel;
		JTable table;
		JScrollPane scroll;
		
		for (int i = 0; i < analyses.length; i++) {
			for (int j = 0; j < analyses[i].getTimeElapsed().length; j++) {
				Object[] row = new Object[columns.length];
				row[0] = analyses[i].getAlgorithm();
				row[1] = analyses[i].getSize();
				row[2] = analyses[i].getGeneratedValues()[j];
				row[3] = analyses[i].getTimeElapsed()[j];
				data.add(row);
			}
		}
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		table = new JTable(data.toArray(new Object[data.size()][]), columns);
		scroll = new JScrollPane(table);
		panel.add(scroll);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension(screenSize.width/2, screenSize.height/2);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(size);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
