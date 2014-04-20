package nona.circlewars.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import nona.circlewars.Game;
import nona.circlewars.level.Level;
import nona.circlewars.util.Menu;
import nona.circlewars.util.Util;

public class LevelState extends GameState {
		
	private Level level;
	
	private boolean gameOver = false;
	private String title = "Game Over";
	private String[] options = { "New Game", "Main Menu" };
	private Menu gameOverMenu = new Menu(title, options);
	
	private boolean highscore;
	
	public LevelState(GameStateManager gsm) {
		super(gsm);
		level = new Level(this);
	}
	
	public void init() {
		level = new Level(this);
		gameOver = false;
		Game.score = 0;
		Game.stage = 0;
	}
	
	public void update() {
		if(!gameOver) {
			level.update();
		} else {
			level.updateGameOverScene();
			gameOverMenu.update();
		}
		
		highscore = Game.data.isHighscore(Game.score);
	}
		
	public void render(Graphics2D g) {
		if(!gameOver) {
			level.render(g);
			if(highscore) {
				g.setColor(Color.WHITE);
				g.setFont(Game.FONT);
				if(highscore) g.drawString("New Highscore!", 50, 75);
				g.setFont(Game.TITLE_FONT);
			}
		} else {
			level.renderGameOverScene(g);
			gameOverMenu.render(g);
			g.setFont(Game.FONT);
			if(highscore) g.drawString("New Highscore!", Util.centerTextX("New Highscore!", Game.METRICS_FONT.getStringBounds("New Highscore!", g)), 100);
			g.setFont(Game.TITLE_FONT);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(Game.TITLE_FONT);
		g.drawString("Score: " + Game.score, 50, 50);
		g.drawString("Stage: " + Game.stage, Game.WIDTH - 50 - Util.getWidth("STAGE: " + Game.stage, g.getFontMetrics(Game.TITLE_FONT)), 50);
	}
	
	public void keyPressed(int k) {
		if(!gameOver) {
			if(k == KeyEvent.VK_ESCAPE) gsm.setState(GameStateManager.STATE_PAUSED, true);
			level.keyPressed(k);
		} else {				
			int action = gameOverMenu.keyPressed(k);
			if(action == 0) init();
			else if(action == 1) gsm.setState(GameStateManager.STATE_MENU, true);
		}
	}
	
	public void keyReleased(int k) {
		level.keyReleased(k);
	}
	
	public void score() {
		Game.score += Game.stage;
	}
	
	public void nextStage() {
		Game.stage++;
		if(Game.stage == 16) Game.stage = 1;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
		if(gameOver) {				
			Game.data.addHighscore(Game.score);
		}
	}
	
}
