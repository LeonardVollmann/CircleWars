package nona.circlewars.entity.particle;

import java.awt.Color;
import java.awt.Graphics2D;

import nona.circlewars.Game;
import nona.circlewars.entity.Entity;
import nona.circlewars.level.Level;

public class ParticleSpawner extends Entity {
		
	public enum Type {
		PARTICLE_NORMAL,
		PARTICLE_FADING,
		PARTICLE_TRAIL,
	};
	
	public ParticleSpawner(Level level, int x, int y, int amount, int life, Type type, Color color) {
		super(level);
			
		if(Game.data.PARTICLES) {
			for(int i = 0; i < amount; i++) {
				if(type == Type.PARTICLE_NORMAL) level.add(new Particle(level, x, y, life, color));
				else if(type == Type.PARTICLE_FADING) level.add(new FadingParticle(level, x, y, life, color));
				else if(type == Type.PARTICLE_TRAIL) level.add(new TrailParticle(level, x, y, life, color));
			}
		}
		
		remove();
	}
	
	@Override
	public void update() {
	}
	
	@Override
	public void render(Graphics2D g) {
	}
	
}
