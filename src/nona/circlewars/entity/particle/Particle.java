package nona.circlewars.entity.particle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import nona.circlewars.Game;
import nona.circlewars.entity.Entity;
import nona.circlewars.level.Level;

public class Particle extends Entity {
		
	protected int size = Game.data.SIZE_PARTICLE;
	protected int trailSize = Game.data.SIZE_PARTICLE;
	
	protected Random random = new Random();
	
	protected int life;
	protected int counter;
		
	protected double xx;
	protected double yy;
	protected double xvel;
	protected double yvel;
	
	protected Color color;
	
	public Particle(Level level, int x, int y, int life, Color color) {
		super(level);
		
		this.x = (int) (this.xx = x);
		this.y = (int) (this.yy = y);
		
		this.xvel = random.nextGaussian();
		this.yvel = random.nextGaussian();
		
		this.life = life;
		
		this.color = color;
	}
	
	public void update() {
		xx += xvel;
		yy += yvel;
		
		counter++;
		if(counter >= life) remove();
	}
	
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect((int) xx, (int) yy, size, size);
	}
	
}
