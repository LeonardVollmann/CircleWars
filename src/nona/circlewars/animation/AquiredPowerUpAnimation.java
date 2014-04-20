package nona.circlewars.animation;

import java.awt.Color;
import java.awt.Graphics2D;

public class AquiredPowerUpAnimation extends Animation {

	private static final int STEP = 2;
	private static final int MAX_SIZE = 75;
	private static final int CYCLES = MAX_SIZE / STEP;
	
	private int cycle = 1;
	private boolean growing = true;
	
	private int size;
	private int initialSize;
	
	private Color color;
	
	private boolean done;
	
	public AquiredPowerUpAnimation(int x, int y, int size, Color color) {
		super(x, y);
		
		this.size = this.initialSize = size;
		this.color = color;
	}

	public void update(int x, int y) {
		this.x = x;
		this.y = y;
			
		size = initialSize + (cycle * STEP);
		
		if(growing) {
			cycle++;
			if(cycle > CYCLES) {
				growing = false;
				cycle--;
			}
		} else {
			cycle--;
			if(cycle == 0) {
				done = true;
			}
		}
	}
	
	@Override
	public void update() {
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(color);
		
		g.drawOval(x - (cycle * STEP) / 2, y - (cycle * STEP) / 2, size, size);
	}
	
	public boolean done() {
		return done;
	}
	
	public Color getColor() {
		return color;
	}

}
