package nona.circlewars.gamestate;

import java.awt.Graphics2D;

public abstract class GameState {

	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	protected abstract void init();
	protected abstract void update();
	protected abstract void render(Graphics2D g);
	protected abstract void keyPressed(int k);
	protected abstract void keyReleased(int k);
	
	public void setState(int state, boolean b) {
		gsm.setState(state, b);
	}
	
}
