package nona.circlewars.util;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import nona.circlewars.Game;

public class Menu {

	protected String title;
	protected String[] options;
	int currentChoice = 0;
	
	protected FontMetrics titleMetrics = Game.getGraphics2D().getFontMetrics(Game.TITLE_FONT);
	protected FontMetrics metrics = Game.getGraphics2D().getFontMetrics(Game.FONT);
	
	public Menu(String title, String[] options) {
		this.title = title;
		this.options = options;
	}
	
	public void update() {}
	
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setFont(Game.TITLE_FONT);
		g.drawString(title, Util.centerTextX(title, titleMetrics.getStringBounds(title, g)), Util.getTitleY(title, titleMetrics));
		
		g.setFont(Game.FONT);
		
		int[] yPositions = Util.getYPositions(title, titleMetrics, options, metrics);
		
		if(options.length != 2) {
			for(int i = 0; i < options.length; i++) {
				String s;
				if(i == currentChoice) {
					s = "[ " + options[i] + " ]";
				} else {
					s = options[i];
				}
				
				g.drawString(s, Util.centerTextX(s, metrics.getStringBounds(s, g)), yPositions[i]);
			}
		} else {
			String s;
			if(currentChoice == 0) s = "[ " + options[0] + " ]";
			else s = options[0];
			g.drawString(s, Util.centerTextX(s, metrics.getStringBounds(s, g)), Game.HEIGHT / 2 - Util.getHeight(s, metrics));
			if(currentChoice == 1) s = "[ " + options[1] + " ]";
			else s = options[1];
			g.drawString(s, Util.centerTextX(s, metrics.getStringBounds(s, g)), Game.HEIGHT / 2 + Util.getHeight(s, metrics));
		}
	}
	
	public int keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) return currentChoice;
		else if(k == KeyEvent.VK_UP) currentChoice--;
		else if(k == KeyEvent.VK_DOWN) currentChoice++;
		
		if(currentChoice == -1) currentChoice = options.length - 1;
		else if(currentChoice == options.length) currentChoice = 0;
		
		return -1;
	}
	
	protected void updateMetrics() {
		metrics = Game.getGraphics2D().getFontMetrics(Game.FONT);
		titleMetrics = Game.getGraphics2D().getFontMetrics(Game.TITLE_FONT);
	}
	
}
