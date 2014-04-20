package nona.circlewars.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import nona.circlewars.Game;
import nona.circlewars.GameData.ShootingMode;
import nona.circlewars.animation.Animation;
import nona.circlewars.animation.DestroyAnimation;
import nona.circlewars.entity.Entity;
import nona.circlewars.entity.circle.Enemy;
import nona.circlewars.entity.circle.Player;
import nona.circlewars.entity.particle.ParticleSpawner;
import nona.circlewars.entity.powerup.PowerUp;
import nona.circlewars.entity.projectile.LaserProjectile;
import nona.circlewars.entity.projectile.Projectile;
import nona.circlewars.gamestate.GameStateManager;
import nona.circlewars.gamestate.LevelState;
import nona.circlewars.util.Timer;

public class Level {

	public static final int COUNT_DOWN_TIME = 3;

	private Random random;

	private Timer timer;
	private int seconds;
	private boolean doneTiming;

	private boolean standing;
	private boolean displayStandingTimer;
	private Timer standingTimer;

	private Player player;

	private ArrayList<Entity> entities;
	private ArrayList<PowerUp> powerUps;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private ArrayList<Animation> animations;

	private LevelState levelState;

	private int enemiesToSpawn;
	private int numEnemies;

	public Level(LevelState levelState) {
		this.levelState = levelState;

		random = new Random();

		player = new Player(this, 40);
		player.setPosition(Game.WIDTH / 2 - (player.getSize() / 2), Game.HEIGHT - (player.getSize() * 2));

		entities = new ArrayList<Entity>();
		powerUps = new ArrayList<PowerUp>();
		enemies = new ArrayList<Enemy>();
		projectiles = new ArrayList<Projectile>();
		animations = new ArrayList<Animation>();

		timer = new Timer(COUNT_DOWN_TIME);
	}

	public void update() {
		if (player.getHealth() == 0) {
			levelState.setGameOver(true);
		}

		player.update();

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
			if (entities.get(i).removed()) entities.remove(i);
		}

		for (PowerUp p : powerUps) {
			p.update();
		}

		if (timer == null) {
			checkCollisions();
			destroyEnemies();

			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).update();
			}

			if (numEnemies == 0) {
				if (!doneTiming) {
					timer = new Timer(COUNT_DOWN_TIME);
					standingTimer = null;
					displayStandingTimer = false;
				}
			}

			if (doneTiming) {
				doneTiming = false;
				levelState.nextStage();
				enemiesToSpawn = Game.stage;
				spawnNew(enemiesToSpawn);
			}
		} else {
			checkCollisions();
			if (timer.paused()) timer.unpause();

			timer.update();
			seconds = timer.getSecondsLeft();
			if (seconds == 0) {
				timer = null;
				seconds = COUNT_DOWN_TIME;
				doneTiming = true;
			}
		}

		standing = !player.moving();

		if (!standing) standingTimer = null;

		if (standingTimer == null && timer == null) {
			standingTimer = new Timer(5);
			standingTimer.setRenderingColor(Color.RED);
		}

		if (standingTimer != null && timer == null) {
			if (standingTimer.paused()) standingTimer.unpause();
			standingTimer.update();
			displayStandingTimer = standingTimer.getSecondsLeft() <= 3;
			if (standingTimer.getSecondsLeft() == 0) {
				player.kill();
				standingTimer = null;
				displayStandingTimer = false;
			}
		}

		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);

			p.update();
			p.calculateCenter();

			if (p.getX() <= 0) {
				add(new ParticleSpawner(this, 0, p.getCenterY(), 50, 25, ParticleSpawner.Type.PARTICLE_FADING, p.getColor()));
				projectiles.remove(p);
			} else if (p.getX() >= Game.WIDTH - p.getSize()) {
				add(new ParticleSpawner(this, Game.WIDTH, p.getCenterY(), 50, 25, ParticleSpawner.Type.PARTICLE_FADING, p.getColor()));
				projectiles.remove(p);
			}

			if (p.getY() <= 0) {
				add(new ParticleSpawner(this, p.getCenterX(), 0, 50, 25, ParticleSpawner.Type.PARTICLE_FADING, p.getColor()));
				projectiles.remove(p);
			} else if (p.getY() >= Game.HEIGHT - p.getSize()) {
				add(new ParticleSpawner(this, p.getCenterX(), Game.HEIGHT, 50, 25, ParticleSpawner.Type.PARTICLE_FADING, p.getColor()));
				projectiles.remove(p);
			}
		}

		for (Animation a : animations) {
			a.update();
		}
	}

	public void render(Graphics2D g) {
		player.render(g);

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(g);
		}

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(g);
		}

		for (int i = 0; i < animations.size(); i++) {
			animations.get(i).render(g);
		}

		for (Entity e : entities) {
			e.render(g);
		}

		for (PowerUp p : powerUps) {
			p.render(g);
		}

		if (timer != null) timer.render(g);
		if (displayStandingTimer) standingTimer.render(g);
	}

	public void checkCollisions() {
		double xdist;
		double ydist;
		double distance;
		for (int x = 0; x < enemies.size(); x++) {
			Enemy e = enemies.get(x);
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = projectiles.get(i);

				xdist = e.getCenterX() - p.getCenterX();
				ydist = e.getCenterY() - p.getCenterY();

				distance = Math.hypot(xdist, ydist);

				if (distance < e.getSize() + p.getSize()) {
					p.impact();
					e.damage();
					projectiles.remove(p);
				}
			}
			if (Math.hypot(player.getCenterX() - e.getCenterX(), player.getCenterY() - e.getCenterY()) < player.getSize() && player.damageable()) {
				player.damage();
			}
		}
		for (int i = 0; i < powerUps.size(); i++) {
			PowerUp pu = powerUps.get(i);

			xdist = player.getCenterX() - pu.getX();
			ydist = player.getCenterY() - pu.getY();

			distance = Math.hypot(xdist, ydist);

			if (distance < pu.getSize() + player.getSize()) {
				player.collectPowerUp(pu);
				powerUps.remove(pu);
			}
		}
	}

	private void spawnNew(int num) {
		for (int i = 0; i < num; i++) {
			enemies.add(new Enemy(this, random.nextInt(50) + 10));
			numEnemies++;
		}
	}

	public void destroyEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (e.shouldDestroy()) {
				if (Game.data.MODE_SHOOTING == ShootingMode.CANNON) {
					add(new DestroyAnimation(e.getX(), e.getY(), e.getSize()));
				} else if (Game.data.MODE_SHOOTING == ShootingMode.PLASMA) {
					add(new ParticleSpawner(this, e.getCenterX(), e.getCenterY(), 100, 45, ParticleSpawner.Type.PARTICLE_FADING, Color.GREEN));
				} else if(Game.data.MODE_SHOOTING == ShootingMode.LASER) {
					add(new ParticleSpawner(this, e.getCenterX(), e.getCenterY(), 200, 60, ParticleSpawner.Type.PARTICLE_FADING, LaserProjectile.color));
				}
				e.spawnPowerUp();
				enemies.remove(e);
				levelState.score();
			}
		}
		numEnemies = enemies.size();
	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ESCAPE) {
			if(standingTimer != null) standingTimer.pause();
			if(timer != null) timer.pause();
			Game.getGameStateManager().setState(GameStateManager.STATE_PAUSED, true);
		}

		player.keyPressed(k);
	}

	public void keyReleased(int k) {
		player.keyReleased(k);
	}

	public void updateGameOverScene() {
		for (Enemy e : enemies) {
			e.update();
		}

		for (Projectile p : projectiles) {
			p.update();
		}

		for (Animation d : animations) {
			d.update();
		}
	}

	public void renderGameOverScene(Graphics2D g) {
		for (Enemy e : enemies) {
			e.render(g);
		}

		for (Projectile p : projectiles) {
			p.render(g);
		}

		for (Animation d : animations) {
			d.render(g);
		}
	}

	public void add(Entity e) {
		if (e instanceof Enemy)
			enemies.add((Enemy) e);
		else if (e instanceof Projectile)
			projectiles.add((Projectile) e);
		else if (e instanceof PowerUp)
			powerUps.add((PowerUp) e);
		else
			entities.add(e);
	}

	public void add(Animation a) {
		animations.add(a);
	}

	public Player getPlayer() {
		return player;
	}

}