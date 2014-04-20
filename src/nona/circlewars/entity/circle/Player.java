package nona.circlewars.entity.circle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import nona.circlewars.Game;
import nona.circlewars.GameData.ShootingMode;
import nona.circlewars.animation.AquiredPowerUpAnimation;
import nona.circlewars.animation.DestroyAnimation;
import nona.circlewars.entity.particle.ParticleSpawner;
import nona.circlewars.entity.powerup.PowerUp;
import nona.circlewars.entity.projectile.LaserProjectile;
import nona.circlewars.entity.projectile.PlasmaProjectile;
import nona.circlewars.entity.projectile.Projectile;
import nona.circlewars.level.Level;
import nona.circlewars.util.Timer;

public class Player extends Circle {

	public static final int SPEED = 10;
	public static final int BLINK_TIME = 60;
	
	private Timer timer;
	
	private Color trailColor = Game.data.COLOR_PLAYER;

	private boolean up = false;
	private boolean down = false;
	private boolean right = false;
	private boolean left = false;

	private int health = 3;
	private boolean justDamaged = false;
	private int blinkTimer = 0;
	
	private boolean doubleFiring;
	
	private AquiredPowerUpAnimation pAnimation;
	
	public Player(Level level, int size) {
		super(level);
		this.size = size;
	}

	@Override
	public void update() {
		if (up) {
			y -= SPEED;
		}
		if (down) {
			y += SPEED;
		}
		if (right) {
			x += SPEED;
		}
		if (left) {
			x -= SPEED;
		}

		if (x + size >= Game.WIDTH) {
			x = Game.WIDTH - size - 1;
		}
		if (x < 0) {
			x = 0;
		}
		if (y + size >= Game.HEIGHT) {
			y = Game.HEIGHT - size - 1;
		}
		if (y < 0) {
			y = 0;
		}

		if(justDamaged) {
			blinkTimer++;
			if (blinkTimer >= BLINK_TIME) {
				justDamaged = false;
				blinkTimer = 0;
			}
		}
		
		if(timer != null) {
			timer.update();
			if(timer.getSecondsLeft() <= 0) {
				if(Game.data.PARTICLES) level.add(new ParticleSpawner(level, centerX, centerY, 50, 50, ParticleSpawner.Type.PARTICLE_TRAIL, timer.getColor()));
				timer = null;
				doubleFiring = false;
			}
		}
		
		if(pAnimation != null) {
			pAnimation.update(x, y);
			if(pAnimation.done()) {
				pAnimation = null;
			}
		}
		
		calculateCenter();
		if(Game.data.MODE_SHOOTING == ShootingMode.CANNON) trailColor = Game.data.COLOR_PROJECTILE_CANNON;
		else if(Game.data.MODE_SHOOTING == ShootingMode.PLASMA) trailColor = Game.data.COLOR_PROJECTILE_PLASMA;
		else if(Game.data.MODE_SHOOTING == ShootingMode.LASER) trailColor = Game.data.COLOR_PROJECTILE_LASER;
		level.add(new ParticleSpawner(level, centerX, centerY, 50, 7, ParticleSpawner.Type.PARTICLE_TRAIL, trailColor));
		if(doubleFiring) level.add(new ParticleSpawner(level, centerX, centerY, 25, 7, ParticleSpawner.Type.PARTICLE_TRAIL, Color.BLUE));
	}

	@Override
	public void render(Graphics2D g) {
		if (!justDamaged) {
			g.setColor(Game.data.COLOR_PLAYER);
			g.drawOval(x, y, size, size);
		} else {
			if (blinkTimer % 5 == 0) {
				g.setColor(Game.data.COLOR_PLAYER);
				g.drawOval(x, y, size, size);
			}
		}
		
		for (int i = 0; i < health; i++) {
			g.setColor(Color.RED);
			g.drawRect(20 + 20 * i, Game.HEIGHT - 30, 10, 10);
		}
		
		if(pAnimation != null) {
			pAnimation.render(g);
		}
	}

	public void shoot(int dir) {
		if(!doubleFiring) {
			if(Game.data.MODE_SHOOTING == ShootingMode.CANNON) level.add(new Projectile(level, centerX - (Projectile.SIZE / 2), centerY - (Projectile.SIZE / 2), dir));
			else if(Game.data.MODE_SHOOTING == ShootingMode.PLASMA) level.add(new PlasmaProjectile(level, centerX - (Projectile.SIZE / 2), centerY - (Projectile.SIZE / 2), dir));
			else if(Game.data.MODE_SHOOTING == ShootingMode.LASER) level.add(new LaserProjectile(level, centerX, centerY, dir));
		} else {
			if(Game.data.MODE_SHOOTING == ShootingMode.CANNON) {
				if(dir == Projectile.UP || dir == Projectile.DOWN) {
					level.add(new Projectile(level, centerX - (Projectile.SIZE / 2) - Projectile.SIZE, centerY, dir));
					level.add(new Projectile(level, centerX - (Projectile.SIZE / 2) + Projectile.SIZE, centerY, dir));							 
				} else if(dir == Projectile.LEFT || dir == Projectile.RIGHT) {
					level.add(new Projectile(level, centerX, centerY - (Projectile.SIZE / 2) - Projectile.SIZE, dir));
					level.add(new Projectile(level, centerX, centerY - (Projectile.SIZE / 2) + Projectile.SIZE, dir));
				}
			} else if(Game.data.MODE_SHOOTING == ShootingMode.PLASMA) {
				if(dir == Projectile.UP || dir == Projectile.DOWN) {
					level.add(new PlasmaProjectile(level, centerX - (Projectile.SIZE / 2) - Projectile.SIZE, centerY, dir));
					level.add(new PlasmaProjectile(level, centerX - (Projectile.SIZE / 2) + Projectile.SIZE, centerY, dir));					
				} else if(dir == Projectile.LEFT || dir == Projectile.RIGHT) {
					level.add(new PlasmaProjectile(level, centerX, centerY - (Projectile.SIZE / 2) - Projectile.SIZE, dir));
					level.add(new PlasmaProjectile(level, centerX, centerY - (Projectile.SIZE / 2) + Projectile.SIZE, dir));		 
				}
			} else if(Game.data.MODE_SHOOTING == ShootingMode.LASER) {
				if(dir == Projectile.UP || dir == Projectile.DOWN) {
					level.add(new LaserProjectile(level, centerX - (Projectile.SIZE / 2) - Projectile.SIZE, centerY, dir));
					level.add(new LaserProjectile(level, centerX - (Projectile.SIZE / 2) + Projectile.SIZE, centerY, dir));					
				} else if(dir == Projectile.LEFT || dir == Projectile.RIGHT) {
					level.add(new LaserProjectile(level, centerX, centerY - (Projectile.SIZE / 2) - Projectile.SIZE, dir));
					level.add(new LaserProjectile(level, centerX, centerY - (Projectile.SIZE / 2) + Projectile.SIZE, dir));	
				}
			}
		}
	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_W) {
			up = true;
		} else if (k == KeyEvent.VK_A) {
			left = true;
		} else if (k == KeyEvent.VK_S) {
			down = true;
		} else if (k == KeyEvent.VK_D) {
			right = true;
		}

		if (k == KeyEvent.VK_UP) {
			shoot(Projectile.UP);
		} else if (k == KeyEvent.VK_DOWN) {
			shoot(Projectile.DOWN);
		} else if (k == KeyEvent.VK_LEFT) {
			shoot(Projectile.LEFT);
		} else if (k == KeyEvent.VK_RIGHT) {
			shoot(Projectile.RIGHT);
		}
	}

	public void keyReleased(int k) {
		if (k == KeyEvent.VK_W) {
			up = false;
		} else if (k == KeyEvent.VK_A) {
			left = false;
		} else if (k == KeyEvent.VK_S) {
			down = false;
		} else if (k == KeyEvent.VK_D) {
			right = false;
		}
	}

	public void damage() {
		health--;
		if(health == 0) {
			level.add(new DestroyAnimation(x, y, size));
		}
		justDamaged = true;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getSize() {
		return size;
	}

	public int getHealth() {
		return health;
	}

	public boolean isDead() {
		return health < 0;
	}

	public boolean damageable() {
		if (justDamaged) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean moving() {
		return up || down || left || right;
	}
	
	public boolean doubleFiring() {
		return doubleFiring;
	}
	
	public void kill() {
		health = 0;
	}
	
	public void collectPowerUp(PowerUp p) {
		pAnimation = new AquiredPowerUpAnimation(x, y, size, p.getColor());
		if(p.getType() == PowerUp.Type.HEALTH) {
			health++;
		} else if(p.getType() == PowerUp.Type.DOUBLE_FIRE) {
			doubleFiring = true;
			timer = new Timer(20);
			timer.setRenderingColor(Color.BLUE);
		}
		
		level.add(new ParticleSpawner(level, p.getX() + (p.getSize() / 2), p.getY() + (p.getSize() / 2), 50, 45, ParticleSpawner.Type.PARTICLE_FADING, p.getColor()));
	}

}
