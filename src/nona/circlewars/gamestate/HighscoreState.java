package nona.circlewars.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import nona.circlewars.Game;
import nona.circlewars.util.Util;

public class HighscoreState extends GameState {

	private int[] scores = new int[10];
	private String[] strings = new String[11];
	
	private String title = "Highscores";
	
	public HighscoreState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		Game.loadGameData();
		scores = Game.data.HIGHSCORES;
		for(int i = 0; i < scores.length; i++) {
			strings[i] = String.format("%2d.: %7d", i+1, scores[i]);
		}
		strings[10] = "[ Main Menu ]";
	}

	@Override
	protected void update() {
	}

	@Override
	protected void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setFont(Game.TITLE_FONT);
		g.drawString(title, Util.centerTextX(title, Game.METRICS_TITLEFONT.getStringBounds(title, g)), Util.getTitleY(title, Game.METRICS_TITLEFONT));
		
		g.setFont(Game.FONT);
		
		int[] yPositions = Util.getYPositions(title, Game.METRICS_TITLEFONT, strings, Game.METRICS_FONT);
		
		for(int i = 0; i < strings.length; i++) {
			String s = strings[i];
			if(s == null) System.out.println("s = null");
			g.drawString(s, Util.centerTextX(s, Game.METRICS_FONT.getStringBounds(s, g)), yPositions[i]);
		}
	}

	@Override
	protected void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) gsm.setState(GameStateManager.STATE_MENU, true);
	}

	@Override
	protected void keyReleased(int k) {
	}

}
