package org.newdawn.spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.newdawn.spaceinvaders.db.Record;

public class Ranking {
	private JFrame winframe;
	public JTextField nameArea;
	public double time;
	private Record db;

	public Ranking() {
		
		JPanel winPanel = new JPanel();

		winframe = new JFrame();
		winframe.setSize(800, 625);

		// g = (Graphics2D) strategy.getDrawGraphics();
		// g.setColor(Color.BLACK);
		// g.fillRect(0, 0, 800, 625);

		JPanel mid = new JPanel();
		JPanel bot = new JPanel();
		GridLayout gr = new GridLayout(1, 3);
		gr.setHgap(50);
		gr.setVgap(100);
		mid.setLayout(gr);
		bot.setLayout(gr);

		JLabel mid1 = new JLabel(" TIME ");
		JLabel mid2 = new JLabel(" :: ");

		mid.add(mid1);
		mid.add(mid2);

		JLabel ex = new JLabel(" YOU WIN ");
		Font font = new Font("Monospaced", Font.BOLD, 50);
		Font font1 = new Font("Monospaced", Font.BOLD, 35);
		ex.setFont(font);
		ex.setForeground(Color.WHITE);
		ex.setHorizontalAlignment(JLabel.CENTER);

		mid1.setFont(font1);
		mid1.setForeground(Color.WHITE);
		mid1.setHorizontalAlignment(JLabel.CENTER);
		mid2.setFont(font1);
		mid2.setForeground(Color.WHITE);
		mid2.setHorizontalAlignment(JLabel.CENTER);

		mid1.setOpaque(true);
		mid1.setBackground(Color.BLACK);
		mid2.setOpaque(true);
		mid2.setBackground(Color.BLACK);

		mid.setOpaque(true);
		mid.setBackground(Color.BLACK);

		nameArea = new JTextField();
		nameArea.setText(" write your name ");

		JButton RankAcs = new JButton(" OKAY ");
		JButton cancle = new JButton(" CANCLE ");

		// System.out.println(nameArea.getText());

		// this.startTime = System.currentTimeMillis() - this.startTime;

		mid.add(nameArea);
		bot.add(RankAcs);
		bot.add(cancle);

		winPanel.setLayout(new GridLayout(3, 1));

		winPanel.setOpaque(true);
		winPanel.setBackground(Color.BLACK);
		ex.setOpaque(true);
		ex.setBackground(Color.BLACK);
		bot.setOpaque(true);
		bot.setBackground(Color.BLACK);
		RankAcs.setOpaque(true);
		RankAcs.setBackground(Color.BLACK);
		cancle.setOpaque(true);
		cancle.setBackground(Color.BLACK);

		winPanel.add(ex);
		winPanel.add(mid);
		winPanel.add(bot);
		// winPanel.add(cancle);
		winPanel.setVisible(true);
		winframe.add(winPanel);
		winframe.setVisible(true);
	}

}
