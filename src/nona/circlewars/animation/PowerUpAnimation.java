package nona.circlewars.animation;

import java.awt.Color;
import java.awt.Graphics2D;

public class PowerUpAnimation extends Animation {

	private static final int STEP = 2;
	
	private int cycle;
	
	private int initialSize;
	private int size;
	private int maxSize;
	private int cycles;
	
	private Color color;
	
	private boolean growing;
	
	public PowerUpAnimation(int x, int y, int size, Color color) {		
		super(x, y);
		
		this.color = color;
		this.size = this.initialSize = size;
		this.maxSize = this.size * 6;
		cycles = maxSize / STEP;
	}
	
	@Override
	public void update() {
		if(growing) {
			if(cycle > cycles) {
				growing = false;
				return;
			}
			size = initialSize + (cycle * STEP);
			cycle++;
		} else {
			if(cycle < 1) {
				growing = true;
				return;
			}
			size = initialSize + (cycle * STEP);
			cycle--;
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(color);
			
		g.drawOval(x - (cycle * STEP) / 2, y - (cycle * STEP) / 2, size, size);
	}


}
