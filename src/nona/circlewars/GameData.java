package nona.circlewars;

import java.awt.Color;
import java.io.Serializable;

public class GameData implements Serializable{
	private static final long serialVersionUID = 2993272982039790835L;
	
	public enum ShootingMode {
		CANNON,
		LASER,
		PLASMA,
	};
	
	public int SIZE_PLAYER = 40;
	public int SIZE_PROJECTILE;
	public int SIZE_PARTICLE = 2;
	public int SIZE_TRAIL = 20;
	
	public Color COLOR_PLAYER = Color.WHITE;
	public Color COLOR_ENEMY_3 = Color.RED;
	public Color COLOR_ENEMY_2 = Color.BLUE;
	public Color COLOR_ENEMY_1 = Color.GREEN;
	public Color COLOR_PROJECTILE_CANNON = Color.GRAY;
	public Color COLOR_PROJECTILE_PLASMA = Color.MAGENTA;
	public Color COLOR_PROJECTILE_LASER = Color.RED;
	
	public boolean PARTICLES = true;
	
	public ShootingMode MODE_SHOOTING = ShootingMode.PLASMA;
	
	public int[] HIGHSCORES = new int[10];
	
	public GameData() {
		Game.saveGameData();
	}
	
	public boolean isHighscore(int score) {
		return score > HIGHSCORES[9];
	}

	public void addHighscore(int score) {
		if(isHighscore(score)) {
			HIGHSCORES[9] = score;
			sortHighscores();
			Game.saveGameData();
		}
	}
	 
	private void sortHighscores() {
		int tempScore;
		boolean swapped = true;
		while(swapped) {
			swapped = false;
			for(int i = 1; i < HIGHSCORES.length; i++) {
				if(HIGHSCORES[i -1] < HIGHSCORES[i]) {
					tempScore = HIGHSCORES[i - 1];
					HIGHSCORES[i - 1] = HIGHSCORES[i];
					HIGHSCORES[i] = tempScore;
					swapped = true;
				}
			}
		}
	}

}
