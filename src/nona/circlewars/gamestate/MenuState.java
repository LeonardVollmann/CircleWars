package nona.circlewars.gamestate;

import java.awt.Graphics2D;

import nona.circlewars.Game;
import nona.circlewars.util.Menu;

public class MenuState extends GameState {

	private Menu menu;
	
	private String title = "Circle Wars";
	private String[] options = {"Start Game",
											"Highscores",
											"Options",
											"Quit Game"};
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	protected void init() {
		menu = new Menu(title, options);
	}

	protected void update() {
		menu.update();
	}

	protected void render(Graphics2D g) {
		menu.render(g);
	}

	protected void keyReleased(int k) {}
	protected void keyPressed(int k) {
		int action = menu.keyPressed(k);
		
		if(action == -1) return;
		else if(action == 0) gsm.setState(GameStateManager.STATE_LEVEL, true);
		else if(action == 1) {
			Game.loadGameData();
			gsm.setState(GameStateManager.STATE_HIGHSCORES, true);
		} else if(action == 2) {
			gsm.setState(GameStateManager.STATE_OPTIONS, true);
		} else if(action == 3) {
			Game.saveGameData();
			System.exit(0);
		}
	}
	
}
