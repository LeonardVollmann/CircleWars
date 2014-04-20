package nona.circlewars.entity.particle;

import java.awt.Color;
import java.awt.Graphics2D;

import nona.circlewars.level.Level;

public class FadingParticle extends Particle {

	private static final int ANIMATION_TIME = 60;
	
	private boolean animate;
	private int animationStep;
	private int animationCounter;
	
	public FadingParticle(Level level, int x, int y, int life, Color color) {
		super(level, x, y, life, color);
		
		this.x = (int) (this.xx = x);
		this.y = (int) (this.yy = y);
		
		this.xvel = random.nextGaussian();
		this.yvel = random.nextGaussian();
		
		this.life = life;

		animationStep = 255 / ANIMATION_TIME;
	}

	@Override
	public void update() {
		xx += xvel;
		yy += yvel;
		
		counter++;
		if(counter >= life) animate = true;
		
		if(animate) {
			animationCounter++;
			if(animationCounter * animationStep > 255) {
				remove();
				return;
			}
			color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255 - (animationCounter * animationStep));
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect((int) xx, (int) yy, size, size);
	}
	
}
