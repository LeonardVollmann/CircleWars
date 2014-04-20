package nona.circlewars;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import nona.circlewars.gamestate.GameStateManager;

/*
 * Author Leonard Vollmann
 * 25.01.2014
 */

public class Game extends JPanel implements Runnable, KeyListener{
	private static final long serialVersionUID = 8162921467849549020L;
		
	public static final int WIDTH = 1080;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int FPS = 60;
	
	public static final Color COLOR_TEXT = Color.WHITE;
	
	public static final Font FONT = new Font("Arial", Font.PLAIN, 18);
	public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 36);
	public static FontMetrics METRICS_FONT;
	public static FontMetrics METRICS_TITLEFONT;
	
	public static int score;
	public static int stage;
	
	public static GameData data;
	
	private static JFrame frame;
	private BufferedImage image;
	private static Graphics2D g;
	
	private static boolean running = false;
	private Thread thread;
	
	private static GameStateManager gsm;
		
	public Game() {
		super();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
				
		frame = new JFrame("Circle Wars");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.requestFocus();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void addNotify() {
		super.addNotify();
	}
	
	public synchronized void start() {
		running = true;
		
		setSize(WIDTH, HEIGHT);
		setFocusable(true);
		requestFocus();
		
		loadGameData();
		if(data == null) data = new GameData();
		
		thread = new Thread(this);
		addKeyListener(this);
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		METRICS_FONT = g.getFontMetrics(FONT);
		METRICS_TITLEFONT = g.getFontMetrics(TITLE_FONT);
		
		gsm = new GameStateManager();
		gsm.setState(GameStateManager.STATE_MENU, true);
				
		thread.start();
	}
	
	public synchronized void stop(){
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / FPS;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while(running) {
			boolean shouldrender = false;
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				updates++;
				shouldrender = true;
				delta--;
			}
			if(shouldrender) {
				render();
				frames++;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("Circle Wars   |   FPS [ " + frames + " ] UPS [ " + updates + " ]");
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	
	public void update() {
		gsm.update();
	}
	
	public void render() {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		gsm.render(g);
		
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}	
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	
	public static void loadGameData() {
		try {
			File file = new File("gameData.dat");
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			data = (GameData) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveGameData() {
		try {
			File file = new File("gameData.dat");
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(data);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Graphics2D getGraphics2D() {
		return g;
	}
	
	public static GameStateManager getGameStateManager() {
		return gsm;
	}
	
	
	public static void main(String[] args) {
		new Game().start();
	}	
	
}
