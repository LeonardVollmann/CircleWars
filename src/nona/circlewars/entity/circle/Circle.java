package nona.circlewars.entity.circle;

import nona.circlewars.entity.Entity;
import nona.circlewars.level.Level;


public abstract class Circle extends Entity {
	
	public Circle(Level level) {
		super(level);
	}

	protected int x;
	protected int y;
	
	protected int centerX;
	protected int centerY;
	
	protected int size;
	
	public void calculateCenter() {
		centerX = x + (size / 2);
		centerY = y + (size / 2);
	}
	
	public int getCenterX() {
		return centerX;
	}
	
	public int getCenterY() {
		return centerY;
	}
	
	public int getSize() {
		return size;
	}
	
}
