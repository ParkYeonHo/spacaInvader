package org.newdawn.spaceinvaders.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import org.newdawn.spaceinvaders.Game;

public class ItemEntity extends Entity {

	private double moveSpeed = -500;
	/** The game in which this entity exists */
	private Game game;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;
	private Toolkit tk = Toolkit.getDefaultToolkit();

	public ItemEntity(Game game, String sprite, int x, int y) {
		super(sprite, x, y);
		this.game = game;
		dy = -moveSpeed;

	}

	@Override
	public void collidedWith(Entity other) {

		int randomNumber = (int) (Math.random() * 100);

		// if (other instanceof ShipEntity) {
		// game.removeEntity(this);
		// if (randomNumber > 50) {
		// Image doubleBulletItem =
		// tk.getImage("src/main/resources/items/doubleBullet.jpg");
		// Game.itemList.add(doubleBulletItem);
		//
		// } else {
		//
		// Image fireItem =
		// tk.getImage("src/main/resources/items/fireItemSmall.jpg");
		// Game.itemList.add(fireItem);
		//
		// }
		//
		// }

		if (other instanceof ShipEntity) {
			game.removeEntity(this);

			if (Game.itemList.size() >= 4) {
				return;
			}
			Game.itemList.add("src/main/resources/items/missileItem.png");
			
//			if (randomNumber > 50) {
//				if (Game.itemList.size() >= 4) {
//					return;
//				}
//				Game.itemList.add("src/main/resources/items/missileItem.png");
//
//			} else {
//				if (Game.itemList.size() >= 4) {
//					return;
//				}
//				Game.itemList.add("src/main/resources/items/bomb.png");
//
//			}

		}

	}
}
