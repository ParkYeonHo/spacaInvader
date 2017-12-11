package org.newdawn.spaceinvaders;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Main {

	private JPanel contentPane;
	private JButton startBtn, showRankingBtn, exitBtn;
	public static JFrame frame;
	public static String shipUrl;
	public String ship;

	public Main() throws FileNotFoundException, IOException {

		frame = new JFrame("Space Invader 101");

		try {
			frame.setContentPane(new JPanel() {
				BufferedImage image = ImageIO.read(new File(
						"C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/backgroundImage/mainBackground.png"));

				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(image, 0, 0, 800, 600, this);
				}
			});
		} catch (IOException e) {

		}
		startBtn = new JButton();
		startBtn.setBounds(369, 324, 111, 40);
		startBtn.setOpaque(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setBorderPainted(false);
		frame.getContentPane().add(startBtn);

		showRankingBtn = new JButton();
		showRankingBtn.setBounds(369, 393, 111, 34);
		showRankingBtn.setOpaque(false);
		showRankingBtn.setContentAreaFilled(false);
		showRankingBtn.setBorderPainted(false);
		frame.getContentPane().add(showRankingBtn);

		exitBtn = new JButton();
		exitBtn.setBounds(369, 462, 111, 34);
		exitBtn.setOpaque(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);
		frame.getContentPane().add(exitBtn);

		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		startBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				frame.setVisible(false);
				new ShipSelect();

			}

		});

		showRankingBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					ShowRanking ranking = new ShowRanking();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.setVisible(false);
			}

		});

		exitBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				System.exit(0);

			}

		});

	}
}
