package nona.circlewars.entity.projectile;

import java.awt.Graphics2D;

import nona.circlewars.Game;
import nona.circlewars.entity.particle.ParticleSpawner;
import nona.circlewars.level.Level;

public class PlasmaProjectile extends Projectile {
			
	public PlasmaProjectile(Level level, int x, int y, int direction) {
		super(level, x, y, direction);
		color = Game.data.COLOR_PROJECTILE_PLASMA;
	}

	@Override
	public void update() {
		if(direction == UP) {
			y -= PLASMA_SPEED;
		} else if(direction == DOWN) {
			y += PLASMA_SPEED;
		} else if(direction == RIGHT) {
			x += PLASMA_SPEED;
		} else if(direction == LEFT) {
			x -= PLASMA_SPEED;
		}
		
		new ParticleSpawner(level, x, y, 5, 5, ParticleSpawner.Type.PARTICLE_NORMAL, color);
		calculateCenter();
	}
	
	@Override
	public void render(Graphics2D g) {
	}
	
}
