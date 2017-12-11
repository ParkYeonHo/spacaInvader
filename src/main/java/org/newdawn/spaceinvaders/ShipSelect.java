package org.newdawn.spaceinvaders;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class ShipSelect {

	public static JFrame container;
	private JLabel ship, back, next, message;
	private ImageIcon selectedIcon;
	private ImageIcon shipImgIcon1, shipImgIcon2, shipImgIcon3, imgIcon;
	private String shipImgIcon1Url, shipImgIcon2Url, shipImgIcon3Url;
	private List shipAry;
	private int shipCount = 0;
	static Game game;
	private String shipUrl;

	public ShipSelect() {

		container = new JFrame("Space Invader 101");

		try {
			container.setContentPane(new JPanel() {
				BufferedImage image = ImageIO.read(new File(
						"C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/backgroundImage/shipSelectBackground.jpg"));

				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(image, 0, 0, 800, 600, this);
				}
			});
		} catch (IOException e) {

		}
		container.setSize(800, 600);
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container.getContentPane().setLayout(null);
		container.setVisible(true);

		shipAry = new LinkedList();

		shipImgIcon1Url = "C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/sprites/ship1.png";
		shipImgIcon2Url = "C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/sprites/ship2.png";
		shipImgIcon3Url = "C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/sprites/ship3.png";

		shipAry.add(shipImgIcon1Url);
		shipAry.add(shipImgIcon2Url);
		shipAry.add(shipImgIcon3Url);

		ship = new JLabel();
		ship.setIcon(shipImgIcon1);
		ship.setBounds(250, 80, 300, 341);

		ship.setIcon(new ImageIcon(
				new ImageIcon(shipImgIcon1Url).getImage().getScaledInstance(300, 350, Image.SCALE_DEFAULT)));
		container.getContentPane().add(ship);

		imgIcon = new ImageIcon(
				new ImageIcon("C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/images/back.png")
						.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		back = new JLabel();
		back.setIcon(imgIcon);
		back.setBounds(43, 210, 100, 100);
		container.getContentPane().add(back);

		imgIcon = new ImageIcon(
				new ImageIcon("C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/images/next.png")
						.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		next = new JLabel();
		next.setIcon(imgIcon);
		next.setBounds(646, 210, 100, 100);
		container.getContentPane().add(next);

		message = new JLabel("플레이 하고싶은 전투기를 선택하세요 !!");
		message.setForeground(Color.WHITE);
		message.setBounds(172, 453, 468, 54);
		message.setFont(new Font("HY강M", Font.BOLD, 25));
		container.getContentPane().add(message);

		next.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				++shipCount;
				if (shipCount == shipAry.size()) {
					shipCount = 0;
				}

				ship.setIcon(new ImageIcon(new ImageIcon((String) shipAry.get(shipCount)).getImage()
						.getScaledInstance(300, 350, Image.SCALE_DEFAULT)));
				container.getContentPane().add(ship);

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

		});

		back.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				--shipCount;

				if (shipCount < 0) {
					shipCount = 2;
				}

				ship.setIcon(new ImageIcon(new ImageIcon((String) shipAry.get(shipCount)).getImage()
						.getScaledInstance(300, 350, Image.SCALE_DEFAULT)));
				container.getContentPane().add(ship);

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

		});

		ship.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {

				if (shipAry.get(shipCount).equals(
						"C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/sprites/ship1.png")) {
					shipUrl = "sprites/gameShip1.png";
				} else if (shipAry.get(shipCount).equals(
						"C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/sprites/ship2.png")) {
					shipUrl = "sprites/gameShip2.png";
				} else if (shipAry.get(shipCount).equals(
						"C:/Users/USER/workspace/spaceinvaders-101-java/src/main/resources/sprites/ship3.png")) {
					shipUrl = "sprites/gameShip3.png";
				}

				container.setVisible(false);
				game.shipUrl = shipUrl;
				game.container.setVisible(true);

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				ship.setBorder(new BevelBorder(BevelBorder.RAISED));
			}

			public void mouseExited(MouseEvent e) {
				ship.setBorder(null);

			}

		});

	}

}
