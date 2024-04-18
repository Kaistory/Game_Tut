package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelManager;
import utilz.Constants.PlayerContants;

public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Player player;
	private LevelManager levelManager;
	
	public final static int TILES_DEFAUNT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int)(TILES_DEFAUNT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	
	public Game() {
		initClass();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		
		startGameLoop();
		
		
	}
	
	private void initClass() {
		levelManager = new LevelManager(this);
		player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE));
		player.loadvlData(levelManager.getCurrentLevel().getLevelData());
		
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		player.update();
		levelManager.update();
	}
	
	public void render(Graphics g) {
		player.render(g);
		levelManager.draw(g);
	}
	
	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;

		while (true) {

			
			long currentTime = System.nanoTime();
			
			deltaU +=(currentTime - previousTime) / timePerUpdate;
			deltaF +=(currentTime - previousTime) / timePerFrame;
			
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates ++;
				deltaU --;
			}
			
			if(deltaF >= 1) {
				gamePanel.repaint();
				deltaF--;
				frames++;
			}
//			if (now - lastFrame >= timePerFrame) {
//				gamePanel.repaint();
//				lastFrame = now;
//				frames++;
//			}

		if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + "| UPS " +updates);
				frames = 0;
				updates = 0;
			}
		
		}

	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}

}
