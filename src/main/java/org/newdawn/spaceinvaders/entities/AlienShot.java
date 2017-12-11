package org.newdawn.spaceinvaders.entities;

import org.newdawn.spaceinvaders.Game;

public class AlienShot extends Entity {

	private double moveSpeed = -300;
	/** The game in which this entity exists */
	private Game game;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;

	public AlienShot(Game game, String sprite, int x,int y) {
		super(sprite, x, y);

		this.game = game;
		dy = -moveSpeed;

		int dir = (int) (Math.random() * 100) + 1;

		if (dir <= 50) {
			dx = +195;
		} else {
			dx = -176;
		}
	}

	@Override
	public void collidedWith(Entity other) {

		if (other instanceof ShipEntity) {
			game.removeEntity(this);
			--game.shipLife;
			if (game.shipLife == 0) {
				game.notifyDeath();
			}
		}
	}
}
