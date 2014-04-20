package nona.circlewars.entity.circle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import nona.circlewars.Game;
import nona.circlewars.animation.HitAnimation;
import nona.circlewars.entity.powerup.PowerUp;
import nona.circlewars.level.Level;

public class Enemy extends Circle {
	
	public static final int MAX_SIZE = 60;
	public static final int MAX_SPEED = 8;
	public static final int MIN_SPEED = 3;
	
	private Random random = new Random();
	
	private int xvel;
	private int yvel;
	
	private int health;
	
	private HitAnimation animation;
	
	private boolean shouldcollide = true;
		
	private boolean toDestroy;
	
	private Color color;
	
	public Enemy(Level level, int size) {
		super(level);
		
		this.size = size;
		
		if(random.nextBoolean()) {
			this.x = random.nextInt(Game.WIDTH - 1) + 1;
			this.y = 0;
		} else {
			this.x = 0;
			this.y = random.nextInt(Game.HEIGHT - 1) + 1;
		}
		
		this.xvel = random.nextInt(MAX_SPEED - MIN_SPEED) + MIN_SPEED;
		this.yvel = random.nextInt(MAX_SPEED - MIN_SPEED) + MIN_SPEED;
				
		if(random.nextBoolean()) xvel *= -1;
		if(random.nextBoolean()) yvel *= -1;
		
		if(size >= (MAX_SIZE / 3) * 2) health = 3;
		else if(size >= MAX_SIZE / 3 && size < (MAX_SIZE / 3) * 2) health = 2;
		else if(size <= MAX_SIZE / 3) health = 1;
		
		animation = HitAnimation.createGhost();
	}
	
	public void update() {
		if(animation.toRemove()) animation = HitAnimation.createGhost();
		animation.update(x, y);
		
		if(health == 3)  {
			color = Game.data.COLOR_ENEMY_3;
			animation.setColor(Game.data.COLOR_ENEMY_3);
		} else if(health == 2) {
			animation.setColor(Game.data.COLOR_ENEMY_2);
			color = Game.data.COLOR_ENEMY_2;
		} else if(health == 1) {
			animation.setColor(Game.data.COLOR_ENEMY_1);
			color = Game.data.COLOR_ENEMY_1;
		}
		
		if(x + size >= Game.WIDTH) {
			x = Game.WIDTH - size;
			xvel *= -1;
		} else if(x <= 0) {
			x = 0;
			xvel *= -1;
		}
		
		if(y + size >= Game.HEIGHT) {
			y = Game.HEIGHT - size;
			yvel *= -1;
		} else if(y < 0) {
			y = 0;
			yvel *= -1;
		}
		
		x += xvel;
		y += yvel;
		
		calculateCenter();
	}
	
	public void render(Graphics2D g) {
		g.setColor(color);	
		g.drawOval(x, y, size, size); 
		
		animation.render(g);
	}
	
	public void damage() {
		health--;
		if(health > 0)	this.animation = new HitAnimation(x, y, size);
		else {
			toDestroy = true;
		}
	}
	
	public void spawnPowerUp() {
		if(random.nextInt(30) == 0 && level.getPlayer().getHealth() < 4) level.add(new PowerUp(level, centerX, centerY, PowerUp.Type.HEALTH));
		else if(random.nextInt(25) == 0 && !level.getPlayer().doubleFiring()) level.add(new PowerUp(level, centerX, centerY, PowerUp.Type.DOUBLE_FIRE));
	}
	
	public boolean shouldcollide() {
		return shouldcollide;
	}

	public int getHealth() {
		return health;
	}
	
	public boolean shouldDestroy() {
		return toDestroy;
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
