package nona.circlewars.entity.projectile;

import java.awt.Color;
import java.awt.Graphics2D;

import nona.circlewars.Game;
import nona.circlewars.level.Level;

public class LaserProjectile extends Projectile {
	
	private static final int LENGTH = 50;
	
	public static Color color = Game.data.COLOR_PROJECTILE_LASER;
	
	public LaserProjectile(Level level, int x, int y, int direction) {
		super(level, x, y, direction);
	}
	
	public void update() {
		if(direction == UP) {
			y -= LASER_SPEED;
		} else if(direction == DOWN) {
			y += LASER_SPEED;
		} else if(direction == RIGHT) {
			x += LASER_SPEED;
		} else if(direction == LEFT) {
			x -= LASER_SPEED;
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(color);
		
		if(direction == UP) g.drawLine(centerX, centerY, centerX, centerY + LENGTH);
		else if(direction == DOWN) g.drawLine(centerX, centerY, centerX, centerY - LENGTH);
		else if(direction == LEFT) g.drawLine(centerX, centerY, centerX + LENGTH, centerY);
		else if(direction == RIGHT) g.drawLine(centerX, centerY, centerX - LENGTH, centerY);
	}
	
	public void impact() {
	}
		
	public Color getColor() {
		return color;
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}

}
