package nona.circlewars.animation;

import java.awt.Color;
import java.awt.Graphics2D;

public class HitAnimation {
	
	
	private static final int STEP = 2;
	private static int LIMIT_SIZE = 50;
	private static int CYCLES = LIMIT_SIZE / STEP;
	
	private boolean toRemove = false;
	private boolean visible = true;
		
	private Color color;
	
	private int cycle = 0;
	private int initialSize;
	private int size;
	
	private int x;
	private int y;
		
	public HitAnimation(int x, int y, int size) {
		this.x = x;
		this.y = y;
		
		this.initialSize = size;
	}
	
	public void update(int x, int y) {
		if(visible) {
			if(cycle > CYCLES) {
				toRemove = true;
				return;
			}
				
			this.x = x;
			this.y = y;
				
			size = initialSize + (cycle * STEP);
			
			cycle++;
		}
	}
	
	public void render(Graphics2D g) {
		if(visible) {
			g.setColor(color);
			
			g.drawOval(x - (cycle * STEP) / 2, y - (cycle * STEP) / 2, size, size);
		}
	}
	
	public boolean toRemove() {
		return toRemove;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public static HitAnimation createGhost() {
		HitAnimation a = new HitAnimation(0, 0, 0);
		a.setVisible(false);
		return a;
	}
	
}
