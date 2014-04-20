package nona.circlewars.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import nona.circlewars.Game;

public class Timer {
	
	private Font font = new Font("Arial", Font.BOLD, 50);
	private FontMetrics metrics = Game.getGraphics2D().getFontMetrics(font);
		
	private boolean paused;
	
	private int seconds;
	private int counter;
	
	private Color color = Color.WHITE;
	
	public Timer(int seconds) {
		this.seconds = seconds;
	}
	
	public void update() {
		if(!paused) {
			counter++;
			if(counter % Game.FPS == 0) {
				counter = 0;
				seconds--;
				if(seconds == 0) pause();
			}
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(color);
		g.setFont(font);
		String s = Integer.toString(seconds);
		g.drawString(s, Util.centerTextX(s, metrics.getStringBounds(s, g)), Util.centerTextY(s, metrics.getStringBounds(s, g)));
	}
	
	public void render(Graphics2D g, int y) {
		g.setColor(color);
		g.setFont(font);
		String s = Integer.toString(seconds);
		g.drawString(s, Util.centerTextX(s, metrics.getStringBounds(s, g)), y);
	}
	
	public void setRenderingColor(Color c) {
		color = c;
	}
	
	public int getSecondsLeft() {
		return seconds;
	}
	
	public void pause() {
		paused = true;
	}
	
	public void unpause() {
		paused = false;
	}
	
	public boolean paused() {
		return paused;
	}
	
	public Color getColor() {
		return color;
	}
	
}
