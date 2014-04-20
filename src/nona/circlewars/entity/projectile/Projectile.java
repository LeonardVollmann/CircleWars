package nona.circlewars.entity.projectile;

import java.awt.Color;
import java.awt.Graphics2D;

import nona.circlewars.Game;
import nona.circlewars.entity.circle.Circle;
import nona.circlewars.entity.particle.ParticleSpawner;
import nona.circlewars.level.Level;

public class Projectile extends Circle{

	public static final int SIZE = 10;
	
	protected static final int CANNON_SPEED = 15;
	protected static final int PLASMA_SPEED = 20;
	protected static final int LASER_SPEED = 30;
	
	protected Color color = Game.data.COLOR_PROJECTILE_CANNON;
	
	protected int direction;
	public static final int UP = 0;
	public static final int  DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
			
	public Projectile(Level level, int x, int y, int direction) {
		super(level);
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public void update() {
		if(direction == UP) {
			y -= CANNON_SPEED;
		} else if(direction == DOWN) {
			y += CANNON_SPEED;
		} else if(direction == RIGHT) {
			x += CANNON_SPEED;
		} else if(direction == LEFT) {
			x -= CANNON_SPEED;
		}
		
		calculateCenter();
	}
	
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
	}
	
	public void impact() {
		level.add(new ParticleSpawner(level, centerX, centerY, 25, 20, ParticleSpawner.Type.PARTICLE_FADING, color));
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
