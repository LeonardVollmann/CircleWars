package nona.circlewars.entity;

import java.awt.Graphics2D;

import nona.circlewars.level.Level;

public abstract class Entity {

	protected Level level;
	
	protected boolean remove;
	
	public Entity(Level level) {
		this.level = level;
	}
	
	protected int x;
	protected int y;
	
	public abstract void update();
	public abstract void render(Graphics2D g);
	
	public void remove() {
		remove = true;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean removed() {
		return remove;
	}
	
}
