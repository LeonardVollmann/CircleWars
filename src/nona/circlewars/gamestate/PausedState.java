package nona.circlewars.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import nona.circlewars.Game;
import nona.circlewars.util.Menu;

public class PausedState extends GameState {

private Menu menu;
	
	private String title = "Game Paused";
	private String[] options = {"Resume",
											"Restart",
											"Main Menu"};
	
	public PausedState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		menu = new Menu(title, options);
	}

	public void update() {
		menu.update();
	}

	public void render(Graphics2D g) {
		menu.render(g);
	}

	public void keyReleased(int k) {}
	public void keyPressed(int k) {
		int action = menu.keyPressed(k);
		
		if(action == 0 || k == KeyEvent.VK_ESCAPE) gsm.setState(GameStateManager.STATE_LEVEL, false);
		else if(action == 1) gsm.setState(GameStateManager.STATE_LEVEL, true);
		else if(action == 2) {
			Game.data.addHighscore(Game.score);
			gsm.setState(GameStateManager.STATE_MENU, true);
		}
	}
	

}
