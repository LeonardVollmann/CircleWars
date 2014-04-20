package nona.circlewars.gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {
	
	public static final int STATE_LEVEL = 0;
	public static final int STATE_MENU = 1;
	public static final int STATE_PAUSED = 2;
	public static final int STATE_HIGHSCORES = 3;
	public static final int STATE_OPTIONS = 4;
	
	private int currentState;
	
	private ArrayList<GameState> gameStates;
	
	private LevelState levelState;
	private MenuState menuState;
	private PausedState pausedState;
	private HighscoreState highscoreState;
	private OptionsState optionsState;
	
	public GameStateManager() {
		gameStates = new ArrayList<GameState>();
		
		levelState = new LevelState(this);
		menuState = new MenuState(this);
		pausedState = new PausedState(this);
		highscoreState = new HighscoreState(this);
		optionsState = new OptionsState(this);
		
		gameStates.add(levelState);
		gameStates.add(menuState);
		gameStates.add(pausedState);
		gameStates.add(highscoreState);
		gameStates.add(optionsState);
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void render(Graphics2D g) {
		gameStates.get(currentState).render(g);
	}
	
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
	
	public void setState(int state, boolean b) {
		currentState = state;
		if(b) gameStates.get(currentState).init();
	}

	
	public LevelState getLevelState() {
		return levelState;
	}
	
}
