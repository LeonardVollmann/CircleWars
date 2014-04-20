package nona.circlewars.animation;

import java.awt.Color;
import java.awt.Graphics2D;

public class DestroyAnimation extends Animation {

	public static final int STEP = 1;
	
	private int color;
	private final Color[] colors = {Color.GREEN,
												 Color.BLUE,
												 Color.RED,
												 Color.MAGENTA,
												 Color.CYAN,
												 Color.PINK,
												 Color.YELLOW};
	
	private boolean shouldDelete = false;
	private int size;
	private int numCycles;
	private int cycle = 1;
	
	public DestroyAnimation(int x, int y, int size) {
		super(x, y);
		
		this.size = size;
		
		this.numCycles = size / STEP;
	}
	
	@Override
	public void update() {
		if(cycle > numCycles) {
			shouldDelete = true;
			return;
		}
		
		size -= STEP;
		
		cycle++;
		
		color++;
		if(color == colors.length) color = 0;
	}
	
	@Override
	public void render(Graphics2D g) {
		if(shouldDelete) return;
		
		g.setColor(colors[color]);
		g.drawOval(x + (cycle * STEP) / 2, y + (cycle * STEP) / 2, size, size);
	}
	
	public boolean shouldDelete() {
		return shouldDelete;
	}
	
}
