package nona.circlewars.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nona.circlewars.Game;
import nona.circlewars.GameData.ShootingMode;
import nona.circlewars.util.Util;

public class OptionsState extends GameState {

	private String title = "Options";
	private Integer currentOption = 0;
	private String[] options =  new String[] {"Shooting mode",
																"Player Color",
																"Projectile Color",
														   		"Laserbeam Color",
																"Plasmabolt Color",
																"Particles",
																"Particle Size"};
	
	private String[] endings = new String[options.length];
	
	private ArrayList<Color> availableColors = new ArrayList<Color>();
	private ArrayList<String> availableColorStrings = new ArrayList<String>();
	
	private ArrayList<ShootingMode> availableShootingModes = new ArrayList<ShootingMode>();
	private ArrayList<String> availableShootingModeStrings = new ArrayList<String>();
	
	private Map<Integer, Integer> sizes = new HashMap<Integer, Integer>();
	private Map<Integer, Color> colors = new HashMap<Integer, Color>();
	private Map<Integer, ShootingMode> modes = new HashMap<Integer, ShootingMode>();
	private Map<Integer, Boolean> booleans = new HashMap<Integer, Boolean>();
	
	public OptionsState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		availableColors.add(Color.BLACK);
		availableColors.add(Color.BLUE);
		availableColors.add(Color.CYAN);
		availableColors.add(Color.DARK_GRAY);
		availableColors.add(Color.GRAY);
		availableColors.add(Color.GREEN);
		availableColors.add(Color.LIGHT_GRAY);
		availableColors.add(Color.MAGENTA);
		availableColors.add(Color.ORANGE);
		availableColors.add(Color.PINK);
		availableColors.add(Color.RED);
		availableColors.add(Color.WHITE);
		availableColors.add(Color.YELLOW);
		availableColorStrings.add("Black");
		availableColorStrings.add("Blue");
		availableColorStrings.add("Cyan");
		availableColorStrings.add("Dark Gray");
		availableColorStrings.add("Gray");
		availableColorStrings.add("Green");
		availableColorStrings.add("Light Gray");
		availableColorStrings.add("Magenta");
		availableColorStrings.add("Orange");
		availableColorStrings.add("Pink");
		availableColorStrings.add("Red");
		availableColorStrings.add("White");
		availableColorStrings.add("Yellow");
		
		availableShootingModes.add(ShootingMode.CANNON);
		availableShootingModes.add(ShootingMode.LASER);
		availableShootingModes.add(ShootingMode.PLASMA);
		availableShootingModeStrings.add("Cannon");
		availableShootingModeStrings.add("Laserblaster");
		availableShootingModeStrings.add("Plasma");
		
		sizes.put(6, Game.data.SIZE_PARTICLE);
		
		colors.put(1, Game.data.COLOR_PLAYER);
		colors.put(2, Game.data.COLOR_PROJECTILE_CANNON);
		colors.put(3, Game.data.COLOR_PROJECTILE_LASER);
		colors.put(4, Game.data.COLOR_PROJECTILE_PLASMA);
		
		modes.put(0, Game.data.MODE_SHOOTING);
		
		booleans.put(5, Game.data.PARTICLES);
		
		for(int i = 0; i < options.length; i++) {
			 if(i == 0) endings[i] = availableShootingModeStrings.get(availableShootingModes.indexOf(modes.get(i)));
			 else if(i == 1 || i == 2 || i == 3 || i == 4) endings[i] = availableColorStrings.get(availableColors.indexOf(colors.get(i)));
			 else if(i == 5) if(booleans.get(i)) endings[i] = "On"; else endings[i] = "Off";
			 else if(i == 6) endings[i] = Integer.toString(sizes.get(i)); 
		}
	}

	@Override
	protected void update() {
	}

	@Override
	protected void render(Graphics2D g) {
		int[] yPositions = Util.getYPositions(title, Game.METRICS_TITLEFONT, options, Game.METRICS_FONT);
		
		g.setColor(Game.COLOR_TEXT);
		g.setFont(Game.TITLE_FONT);
		g.drawString(title, Util.centerTextX(title, Game.METRICS_TITLEFONT.getStringBounds(title, g)), Util.getTitleY(title, Game.METRICS_TITLEFONT));
		g.setFont(Game.FONT);
		String s = new String();
		for(int i = 0; i < options.length; i++) {
			s = options[i] + " ( " + endings[i] + " ) ";
			if(i == currentOption) s = "[ " + s + " ]";
			g.drawString(s, Util.centerTextX(s, Game.METRICS_FONT.getStringBounds(s, g)), yPositions[i]);
		}
	}

	private void incrementValue() {
		int index = 0;
		 if(currentOption == 0) {
			 index = availableShootingModes.indexOf(modes.get(currentOption)) + 1;
			 if(index == availableShootingModes.size()) index = 0;
			 endings[currentOption] = availableShootingModeStrings.get(index);
			 modes.put(currentOption, availableShootingModes.get(index));
		 }else if(currentOption == 1 || currentOption == 2 || currentOption == 3 || currentOption == 4) {
			 index = availableColors.indexOf(colors.get(currentOption)) + 1;
			 if(index == availableColors.size()) index = 0;
			 endings[currentOption] = availableColorStrings.get(index);
			 colors.put(currentOption, availableColors.get(index));
		 }else if(currentOption == 5) {
			 if(!booleans.get(currentOption)) endings[currentOption] = "On"; else endings[currentOption] = "Off";
			 booleans.put(currentOption, endings[currentOption] == "On");
		 }else if(currentOption == 6) {
			 index = sizes.get(currentOption) + 1;
			 endings[currentOption] = Integer.toString(index); 
			 sizes.put(currentOption, index);
		 }
	}
	
	private void decrementValue() {
		int index = 0;
		 if(currentOption == 0) {
			 index = availableShootingModes.indexOf(modes.get(currentOption)) - 1;
			 if(index == -1) index = availableShootingModes.size() - 1;
			 endings[currentOption] = availableShootingModeStrings.get(index);
			 modes.put(currentOption, availableShootingModes.get(index));
		 }else if(currentOption == 1 || currentOption == 2 || currentOption == 3 || currentOption == 4) {
			 index = availableColors.indexOf(colors.get(currentOption)) - 1;
			 if(index == -1) index = availableColors.size() - 1;
			 endings[currentOption] = availableColorStrings.get(index);
			 colors.put(currentOption, availableColors.get(index));
		 }else if(currentOption == 5) {
			 if(!booleans.get(currentOption)) endings[currentOption] = "On"; else endings[currentOption] = "Off";
			 booleans.put(currentOption, endings[currentOption] == "On");
		 }else if(currentOption == 6) {
			 index = sizes.get(currentOption) - 1;
			 if(index < 1) index = 1;
			 endings[currentOption] = Integer.toString(index); 
			 sizes.put(currentOption, index);
		 }
	}
	
	@Override
	protected void keyPressed(int k) {
		if(k == KeyEvent.VK_DOWN) {
			currentOption++;
			if(currentOption == options.length) currentOption = 0;
		} else if(k == KeyEvent.VK_UP) {
			currentOption--;
			if(currentOption < 0) currentOption = options.length - 1;
		} else if(k == KeyEvent.VK_LEFT) {
			decrementValue();
		} else if(k == KeyEvent.VK_RIGHT) {
			incrementValue();
		} else if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_ENTER) {
			Game.data.COLOR_PLAYER = colors.get(1);
			Game.data.COLOR_PROJECTILE_CANNON = colors.get(2);
			Game.data.COLOR_PROJECTILE_LASER = colors.get(3);
			Game.data.COLOR_PROJECTILE_PLASMA = colors.get(4);
			Game.data.PARTICLES = booleans.get(5);
			Game.data.SIZE_PARTICLE = sizes.get(6);
			Game.data.MODE_SHOOTING = modes.get(0);
			Game.saveGameData();
			gsm.setState(GameStateManager.STATE_MENU, true);
		}
	}

	@Override
	protected void keyReleased(int k) {	
	}

}
