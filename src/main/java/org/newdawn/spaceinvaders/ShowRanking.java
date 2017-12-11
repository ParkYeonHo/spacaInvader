package org.newdawn.spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import org.newdawn.spaceinvaders.db.Record;
import org.newdawn.spaceinvaders.model.RecordInfo;

public class ShowRanking {

	private JPanel contentPane;
	private JPanel mid = new JPanel();
	private LinkedList list;

	private JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, ranking, back;
	private JLabel t1, t2, t3, t4, t5, t6, t7, t8;
	private Font lblFont;
	private RecordInfo info;

	public ShowRanking() throws ClassNotFoundException, SQLException {

	
		list = new Record().getRecord();
		
		lblFont = new Font("HY나무L", Font.BOLD, 24);

		t1 = new JLabel(new ImageIcon("src/main/resources/images/t1.png"));

		t2 = new JLabel(new ImageIcon("src/main/resources/images/t2.png"));
		t3 = new JLabel(new ImageIcon("src/main/resources/images/t2.png"));

		t4 = new JLabel(new ImageIcon("src/main/resources/images/t3.png"));
		t5 = new JLabel(new ImageIcon("src/main/resources/images/t3.png"));

		t6 = new JLabel(new ImageIcon("src/main/resources/images/t4.png"));
		t7 = new JLabel(new ImageIcon("src/main/resources/images/t4.png"));
		
		final JFrame frame = new JFrame("Space Invader 101");

		try {
			frame.setContentPane(new JPanel() {
				BufferedImage image = ImageIO.read(new File(
						"C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/backgroundImage/rankingBackground.png"));

				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(image, 0, 0, 800, 600, this);
				}
			});
		} catch (IOException e) {

		}

		ranking = new JLabel("RANKING");
		ranking.setBounds(300, 20, 400, 100);
		ranking.setForeground(Color.WHITE);
		ranking.setFont(new Font("나눔손글씨 펜", Font.BOLD, 70));
		frame.add(ranking);

		back = new JLabel("BACK");
		back.setBounds(370, 470, 400, 100);
		back.setForeground(Color.WHITE);
		back.setFont(new Font("나눔손글씨 펜", Font.BOLD, 50));
		frame.add(back);

		t1.setBounds(40, 100, 400, 100);
		frame.add(t1);
		info = (RecordInfo) list.get(0);
		
		lbl1 = new JLabel("1st      " + info.getName() + "       " + info.getRecord() + "초");
		
		lbl1.setBounds(280, 100, 400, 100);
		lbl1.setForeground(Color.WHITE);
		lbl1.setFont(lblFont);
		frame.add(lbl1);

		t2.setBounds(40, 150, 400, 100);
		frame.add(t2);
		info = (RecordInfo) list.get(1);
		lbl2 = new JLabel("2nd    " + info.getName() + "       " + info.getRecord() + "초");
		lbl2.setBounds(280, 150, 400, 100);
		lbl2.setForeground(Color.WHITE);
		lbl2.setFont(lblFont);
		frame.add(lbl2);

		t3.setBounds(40, 200, 400, 100);
		frame.add(t3);
		info = (RecordInfo) list.get(2);
		lbl3 = new JLabel("3rd     " + info.getName() + "       " + info.getRecord() + "초");
		lbl3.setBounds(280, 200, 400, 100);
		lbl3.setForeground(Color.WHITE);
		lbl3.setFont(lblFont);
		frame.add(lbl3);

		t4.setBounds(40, 250, 400, 100);
		frame.add(t4);
		info = (RecordInfo) list.get(3);
		lbl4 = new JLabel("4th     " + info.getName() + "       " + info.getRecord() + "초");
		lbl4.setBounds(280, 250, 400, 100);
		lbl4.setForeground(Color.WHITE);
		lbl4.setFont(lblFont);
		frame.add(lbl4);

		t5.setBounds(40, 300, 400, 100);
		frame.add(t5);
		info = (RecordInfo) list.get(4);
		lbl5 = new JLabel("5th     " + info.getName() + "       " + info.getRecord() + "초");
		lbl5.setBounds(280, 300, 400, 100);
		lbl5.setForeground(Color.WHITE);
		lbl5.setFont(lblFont);
		frame.add(lbl5);

		t6.setBounds(40, 350, 400, 100);
		frame.add(t6);
		info = (RecordInfo) list.get(5);
		lbl6 = new JLabel("6th     " + info.getName() + "       " + info.getRecord() + "초");
		lbl6.setBounds(280, 350, 400, 100);
		lbl6.setForeground(Color.WHITE);
		lbl6.setFont(lblFont);
		frame.add(lbl6);

		t7.setBounds(40, 400, 400, 100);
		frame.add(t7);
		info = (RecordInfo) list.get(6);
		lbl7 = new JLabel("7th     " + info.getName() + "       " + info.getRecord() + "초");
		lbl7.setBounds(280, 400, 400, 100);
		lbl7.setForeground(Color.WHITE);
		lbl7.setFont(lblFont);
		frame.add(lbl7);


		info = (RecordInfo) list.get(7);
		

		frame.setTitle("Ranking");
		frame.setSize(800, 600);
		frame.setLayout(new GridLayout(10, 1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				Main.frame.setVisible(true);
			}
		});
	}
}
