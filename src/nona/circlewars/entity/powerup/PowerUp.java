package nona.circlewars.entity.powerup;

import java.awt.Color;
import java.awt.Graphics2D;

import nona.circlewars.animation.Animation;
import nona.circlewars.animation.PowerUpAnimation;
import nona.circlewars.entity.Entity;
import nona.circlewars.level.Level;

public class PowerUp extends Entity {

	public enum Type {
		HEALTH,
		DOUBLE_FIRE,
	};
	
	protected static Color color;
	protected int size = 15;
	
	protected Type type;
	
	protected Animation animation;
	
	public PowerUp(Level level, int x, int y, Type type) {
		super(level);
		
		this.x = x;
		this.y = y;
		
		this.type = type;
		
		if(type == Type.HEALTH) {
			color = Color.RED;
		} else if(type == Type.DOUBLE_FIRE) {
			color = Color.BLUE;
		}
		animation = new PowerUpAnimation(x, y, size, color);
	}
	
	@Override
	public void update() {
		animation.update();
	}
	
	@Override
	public void render(Graphics2D g) {
		if(type == Type.HEALTH) g.setColor(color);
		animation.render(g);
	}
	
	public int getSize() {
		return size;
	}
	
	public Type getType() {
		return type;
	}
	
	public Color getColor() {
		return color;
	}
	
}
