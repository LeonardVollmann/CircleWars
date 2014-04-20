package nona.circlewars.entity.particle;

import java.awt.Color;
import java.awt.Graphics2D;

import nona.circlewars.level.Level;

public class TrailParticle extends Particle {

	public TrailParticle(Level level, int x, int y, int life, Color color) {
		super(level, x, y, life, color);
		xvel = random.nextGaussian() / 2;
		yvel = random.nextGaussian() / 2;
	}
	
	public void update() {
		super.update();
	}
	
	public void render(Graphics2D g) {
		super.render(g);
	}

}
