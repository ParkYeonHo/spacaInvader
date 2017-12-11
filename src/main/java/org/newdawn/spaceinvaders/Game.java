package org.newdawn.spaceinvaders;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.newdawn.spaceinvaders.db.Record;
import org.newdawn.spaceinvaders.entities.AlienEntity;
import org.newdawn.spaceinvaders.entities.Entity;
import org.newdawn.spaceinvaders.entities.ItemEntity;
import org.newdawn.spaceinvaders.entities.ShipEntity;
import org.newdawn.spaceinvaders.entities.ShotEntity;
import org.newdawn.spaceinvaders.entities.AlienShot;
import org.newdawn.spaceinvaders.entities.BossEntity;
import org.newdawn.spaceinvaders.entities.BossShot;

/**
 * The main hook of our game. This class with both act as a manager for the
 * display and central mediator for the game logic.
 * 
 * Display management will consist of a loop that cycles round all entities in
 * the game asking them to move and then drawing them in the appropriate place.
 * With the help of an inner class it will also allow the player to control the
 * main ship.
 * 
 * As a mediator it will be informed when entities within our game detect events
 * (e.g. alient killed, played died) and will take appropriate game actions.
 * 
 * @author Kevin Glass
 */
@SuppressWarnings("serial")
public class Game extends Canvas {
	/** The stragey that allows us to use accelerate page flipping */
	private BufferStrategy strategy;
	/**
	 * True if the game is currently "running", i.e. the game loop is looping
	 */
	private boolean gameRunning = true;
	/** The list of all the entities that exist in our game */
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	/** The list of entities that need to be removed from the game this loop */
	private ArrayList<Entity> removeList = new ArrayList<Entity>();
	/** The entity representing the player */
	private Entity ship;
	/** The speed at which the player's ship should move (pixels/sec) */
	private double moveSpeed = 300;
	/** The time at which last fired a shot */
	private long lastFire = 0;
	/** The interval between our players shot (ms) */
	private long firingInterval = 500;
	/** The number of aliens left on the screen */
	private int alienCount;

	/** The message to display which waiting for a key press */
	private String message = "";
	/** True if we're holding up game play until a key has been pressed */
	private boolean waitingForKeyPress = true;
	/** True if the left cursor key is currently pressed */
	private boolean leftPressed = false;
	/** True if the right cursor key is currently pressed */
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean itemPressed = false;
	/** True if we are firing */
	private boolean firePressed = false;
	public static LinkedList<String> itemList = new LinkedList<String>();
	public int shipLife = 3;
	public int bossHp = 20;
	private long startTime, finishTime, recordTime;
	private long before, after, gap;
	private Record db = new Record();
	private double record;
	/**
	 * 
	 * True if game logic needs to be applied this loop, normally as a result of
	 * a game event
	 */
	private boolean logicRequiredThisLoop = false;

	public static JFrame container;

	private boolean isPaused = false; // 멈춤기능에 사용되는 flag

	public static String shipUrl = "sprites/default.png";

	/**
	 * Construct our game and set it running.
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public Game() throws FileNotFoundException, IOException {

		Sound.bgm("src/main/resources/sound/game.wav");
		// create a frame to contain our game
		container = new JFrame("Space Invaders 101");

		// get hold the content of the frame and set up the resolution of the
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setLayout(null);
		// setup our canvas size and put it into the content of the frame
		setBounds(0, 0, 800, 600);
		panel.add(this);

		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);

		// finally make the window visible
		container.pack();
		container.setResizable(true);
		
		
		container.setVisible(false);
		new Main();

		
		
		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed

		addKeyListener(new KeyInputHandler());

		// request the focus so key events come to us

		requestFocus();
		setFocusable(true);

		// create the buffering strategy which will allow AWT

		createBufferStrategy(2);
		strategy = getBufferStrategy();

		// initialise the entities in our game so there's something
		// to see at startup

		initEntities();
	}

	/**
	 * Start a fresh game, this should clear out any old data and create a new
	 * set.
	 */
	private void startGame() {
		itemList.clear();
		shipLife = 3;
		// clear out any existing entities and intialise a new set
		entities.clear();
		initEntities();

		// blank out any keyboard settings we might currently have
		leftPressed = false;
		rightPressed = false;
		firePressed = false;
		upPressed = false;
		downPressed = false;
	}

	/**
	 * Initialise the starting state of the entities (ship and aliens). Each
	 * entitiy will be added to the overall list of entities in the game.
	 */

	private void initEntities() {

		// create the player ship and place it roughly in the center of the
		// screen
		ship = new ShipEntity(this, shipUrl, 370, 550);
		entities.add(ship);
		// create a block of aliens (5 rows, by 12 aliens, spaced evenly)
		alienCount = 0;
		for (int row = 0; row < 1; row++) {
			for (int x = 0; x < 12; x++) {
				Entity alien = new AlienEntity(this, "sprites/alien.gif", 100 + (x * 50), (50) + row * 30);
				entities.add(alien);
				alienCount++;
			}
		}
	}

	/**
	 * Notification from a game entity that the logic of the game should be run
	 * at the next opportunity (normally as a result of some game event)
	 */
	public void updateLogic() {
		logicRequiredThisLoop = true;
	}

	/**
	 * Remove an entity from the game. The entity removed will no longer move or
	 * be drawn.
	 * 
	 * @param entity
	 *            The entity that should be removed
	 */
	public void removeEntity(Entity entity) {
		removeList.add(entity);
	}

	/**
	 * Notification that the player has died.
	 */
	public void notifyDeath() {
		message = "Oh no! They got you, try again?";
		waitingForKeyPress = true;
	}

	/**
	 * Notification that the player has won since all the aliens are dead.
	 */

	public void boss() {

		int createX = Window.WIDTH / 2;
		int createY = Window.HEIGHT / 3;
		BossEntity boss = new BossEntity(this, "sprites/boss.gif", createX, 60);
		entities.add(boss);

	}

	public void notifyWin() {

		finishTime = System.currentTimeMillis();
		recordTime = (finishTime - startTime) - gap;
		record = Math.round(recordTime / 100) / 10d;

		message = "Well done! You Win!";

		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter Your name: ");
		JTextField txt = new JTextField(10);
		panel.add(lbl);
		panel.add(txt);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "The Title", JOptionPane.NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (selectedOption == 0) {
			try {
				db.insert(txt.getText(), record);
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		waitingForKeyPress = true;
	}

	/**
	 * Notification that an alien has been killed
	 */
	public void notifyAlienKilled(Entity other) {
		// reduce the alient count, if there are none left, the player has won!
		alienCount--;

		if (alienCount == 0) {
			boss();
		}

		 
		// if (tempPer<20) {
		// AlienShot item2 = new AlienShot(this, "images/fireItem.png",
		// other.getX(), other.getY());
		// entities.add(item2);
		// }

		int tempPer = (int) (Math.random() * 100);

		if (tempPer < 30) {
			ItemEntity getItem = new ItemEntity(this, "items/getItem.png", other.getX(), other.getY());
			entities.add(getItem);
		}

		// if there are still some aliens left then they all need to get faster,
		// so
		// speed up all the existing aliens
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = (Entity) entities.get(i);

			if (entity instanceof AlienEntity) {
				// speed up by 2%
				entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.02);
			}

		}

	}

	/**
	 * Attempt to fire a shot from the player. Its called "try" since we must
	 * first check that the player can fire at this point, i.e. has he/she
	 * waited long enough between shots
	 */
	public void tryToFire() {

		Sound.Play("src/main/resources/sound/shot.wav");

		// check that we have waiting long enough to fire
		if (System.currentTimeMillis() - lastFire < firingInterval) {
			return;
		}

		// if we waited long enough, create the shot entity, and record the
		// time.
		lastFire = System.currentTimeMillis();
		ShotEntity shot = new ShotEntity(this, "sprites/shot.gif", ship.getX() + 10, ship.getY() - 30);
		entities.add(shot);

		if (itemPressed) {
			this.removeEntity(shot);
		}

	}

	/**
	 * The main game loop. This loop is running during all game play as is
	 * responsible for the following activities:
	 * <p>
	 * - Working out the speed of the game loop to update moves - Moving the
	 * game entities - Drawing the screen contents (entities, text) - Updating
	 * game events - Checking Input
	 * <p>
	 */

	public void gameLoop() {
		
		while (true) {

			long lastLoopTime = System.currentTimeMillis();
			while (gameRunning) {

				if (alienCount == 0) {
					boss();
					alienCount--;
				}

				if (bossHp <= 0) {
					notifyWin();
					bossHp = 20;
				}
			
				Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

				if (isPaused) {
					g.setColor(Color.WHITE);
					g.drawString("Pause !!", 380, 300);
					g.dispose();
					strategy.show();

					break;
				}

				Toolkit tk = Toolkit.getDefaultToolkit();
				Image img = tk.getImage("src/main/resources"
						+ "/backgroundImage/gameBackground.jpg");
                g.drawImage(img, 0, 0, 800, 600, this);

				// work out how long its been since the last update, this
				// will be used to calculate how far the entities should
				// move this loop
				long delta = System.currentTimeMillis() - lastLoopTime;

				lastLoopTime = System.currentTimeMillis();

				// Get hold of a graphics context for the accelerated
				// surface and blank it out

				// cycle round asking each entity to move itself
				if (!waitingForKeyPress) {

					if (itemList.size() > 0) {
						for (int i = 0; i < itemList.size(); i++) {

							g.drawImage(tk.getImage(itemList.get(i)), 610 + (i * 40), 520, this);

						}
					}

					Image heart = tk.getImage("src/main/resources/images/heart.png");

					for (int i = 0; i < shipLife; i++) {
						g.drawImage(heart, 20 + (i * 50), 520, this);

					}

					for (int i = 0; i < entities.size(); i++) {
						Entity entity = (Entity) entities.get(i);

						int tempPer = (int) (Math.random() * 1000);

						if (entity instanceof AlienEntity) {
							if (tempPer < 1.5) {
								AlienShot item2 = new AlienShot(this, "sprites/AlienShot.gif", entity.getX(),
										entity.getY());
								entities.add(item2);
							}
						}

						if (entity instanceof BossEntity) {
							if (tempPer < 50) {
								BossShot item2 = new BossShot(this, "sprites/BossShot.png", entity.getX() + 130,
										entity.getY());
								entities.add(item2);
							}
						}

						if (itemPressed) {
							Sound.Play("src/main/resources/sound/missile.wav");
							if (itemList.size() < 0) {
								return;
							}

							String item = itemList.getFirst();

							if (item.equals("src/main/resources/items/missileItem.png")) {

								ShotEntity shot = new ShotEntity(this, "items/missileItem.png", entity.getX() - 70,
										entity.getY() + 70);
								ShotEntity shot1 = new ShotEntity(this, "items/missileItem.png", entity.getX(),
										entity.getY());
								ShotEntity shot2 = new ShotEntity(this, "items/missileItem.png", entity.getX() + 100,
										entity.getY() - 55);

								entities.add(shot);
								entities.add(shot1);
								entities.add(shot2);

								itemList.removeFirst();

							} else if (item.equals("src/main/resources/items/bomb.png")) {
								ShotEntity shot = new ShotEntity(this, "items/bomb.png", ship.getX() + 10,
										ship.getY() - 30);

								entities.add(shot);
								itemList.removeFirst();

							}
							itemPressed = false;
						}

						entity.move(delta);
					}

				}

				// cycle round drawing all the entities we have in the game
				for (int i = 0; i < entities.size(); i++) {
					Entity entity = (Entity) entities.get(i);

					entity.draw(g);
				}

				// brute force collisions, compare every entity against
				// every other entity. If any of them collide notify
				// both entities that the collision has occured
				for (int p = 0; p < entities.size(); p++) {
					for (int s = p + 1; s < entities.size(); s++) {
						Entity me = (Entity) entities.get(p);
						Entity him = (Entity) entities.get(s);

						if (me.collidesWith(him)) {
							me.collidedWith(him);
							him.collidedWith(me);
						}
					}
				}

				// remove any entity that has been marked for clear up
				entities.removeAll(removeList);
				removeList.clear();

				// if a game event has indicated that game logic should
				// be resolved, cycle round every entity requesting that
				// their personal logic should be considered.
				if (logicRequiredThisLoop) {
					for (int i = 0; i < entities.size(); i++) {
						Entity entity = (Entity) entities.get(i);
						entity.doLogic();
					}

					logicRequiredThisLoop = false;
				}

				// if we're waiting for an "any key" press then draw the
				// current message
				if (waitingForKeyPress) {
					g.setColor(Color.white);
					g.drawString(message, (800 - g.getFontMetrics().stringWidth(message)) / 2, 250);
					g.drawString("Press any key", (800 - g.getFontMetrics().stringWidth("Press any key")) / 2, 300);
				}

				// finally, we've completed drawing so clear up the graphics
				// and flip the buffer over
				g.dispose();
				strategy.show();

				// resolve the movement of the ship. First assume the ship
				// isn't moving. If either cursor key is pressed then
				// update the movement appropraitely

				ship.setHorizontalMovement(0);
				ship.setVerticalMovement(0);

				if ((leftPressed) && (!rightPressed)) {
					ship.setHorizontalMovement(-moveSpeed);
				} else if ((rightPressed) && (!leftPressed)) {
					ship.setHorizontalMovement(moveSpeed);
				}

				if ((upPressed) && (!downPressed)) {
					ship.setVerticalMovement(-moveSpeed);
				} else if ((downPressed) && (!upPressed)) {
					ship.setVerticalMovement(moveSpeed);
				}

				// if we're pressing fire, attempt to fire
				if (firePressed) {

					tryToFire();
				}

				// finally pause for a bit. Note: this should run us at about
				// 100 fps but on windows this might vary each loop due to
				// a bad implementation of timer
				try {
					Thread.sleep(10);
				} catch (Exception e) {
				}

			}
		}
	}

	/**
	 * A class to handle keyboard input from the user. The class handles both
	 * dynamic input during game play, i.e. left/right and shoot, and more
	 * static type input (i.e. press any key to continue)
	 * 
	 * This has been implemented as an inner class more through habbit then
	 * anything else. Its perfectly normal to implement this as seperate class
	 * if slight less convienient.
	 * 
	 * @author Kevin Glass
	 */
	private class KeyInputHandler extends KeyAdapter {
		/**
		 * The number of key presses we've had while waiting for an "any key"
		 * press
		 */
		private int pressCount = 1;

		/**
		 * Notification from AWT that a key has been pressed. Note that a key
		 * being pressed is equal to being pushed down but *NOT* released. Thats
		 * where keyTyped() comes in.
		 *
		 * @param e
		 *            The details of the key that was pressed
		 */

		public void keyPressed(KeyEvent e) {

			// if we're waiting for an "any key" typed then we don't
			// want to do anything with just a "press"
			if (waitingForKeyPress) {

				entities.add(ship);
				startTime = System.currentTimeMillis();

				return;
			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {

				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {

				firePressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {

				upPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {

				downPressed = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_Z) {

				itemPressed = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_P) {

				if (isPaused == false) {

					before = System.currentTimeMillis();
					isPaused = true;

				} else if (isPaused == true) {
					after = System.currentTimeMillis();
					gap = gap + (after - before);

					isPaused = false;

				}
			}

		}

		/**
		 * Notification from AWT that a key has been released.
		 *
		 * @param e
		 *            The details of the key that was released
		 */
		public void keyReleased(KeyEvent e) {

			// if we're waiting for an "any key" typed then we don't
			// want to do anything with just a "released"
			if (waitingForKeyPress) {
				return;
			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_Z) {
				itemPressed = false;
			}

		}

		/**
		 * Notification from AWT that a key has been typed. Note that typing a
		 * key means to both press and then release it.
		 *
		 * @param e
		 *            The details of the key that was typed.
		 */
		public void keyTyped(KeyEvent e) {

			// if we're waiting for a "any key" type then
			// check if we've recieved any recently. We may
			// have had a keyType() event from the user releasing
			// the shoot or move keys, hence the use of the "pressCount"
			// counter.

			if (waitingForKeyPress) {
				if (pressCount == 1) {
					// since we've now recieved our key typed
					// event we can mark it as such and start
					// our new game
					waitingForKeyPress = false;
					startGame();
					pressCount = 0;
				} else {
					pressCount++;
				}
			}

			// if we hit escape, then quit the game
			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}

	/**
	 * The entry point into the game. We'll simply create an instance of class
	 * which will start the display and game loop.
	 * 
	 * @param argv
	 *            The arguments that are passed into our game
	 */

}
