package org.newdawn.spaceinvaders.entities;

import org.newdawn.spaceinvaders.Game;

public class BossShot extends Entity {

	private double moveSpeed = -250;
	/** The game in which this entity exists */
	private Game game;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;

	public BossShot(Game game, String sprite, int x,int y) {
		super(sprite, x, y);

		this.game = game;
		dy = -moveSpeed;

		int dir = (int) (Math.random() * 100) + 1;

		if (dir <= 20) {
			dx = +105;
		}else if(dir>20 && dir<=50){
			dx = -50;
		}else if(dir>50 && dir<=75){
			dx = +78;
		}else if(dir>75 && dir<=100){
			dx = -10;
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
