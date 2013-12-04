package edu.wsu.vancouver.ssdd;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame {
	public static final int logicInterval = 20;
	private static final int START_UP = 1;
	private static final int PLAYING = 2;
	private static final int GAME_OVER = 3;
	
	private final int ScreenWidth = 600;
	private final int ScreenHeight = 800;
	private int gameState;
	private int gameOverTimer;
	

	public Main(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		startUp(container);
	}

	public void startUp(GameContainer container) {
		gameState = START_UP;
		container.setSoundOn(false);
	}
	
	public void newGame(GameContainer container) {
		gameState = PLAYING;
		container.setSoundOn(true);
	}
	
	public void gameOver() {
		gameState = GAME_OVER;
		gameOverTimer = 4000;
	}
	
	public void makeMenu(){
		//MenuLevel mLev = new MenuLevel(entity, x, y);
	}
	
	@Override
	public void update(GameContainer container, int lastUpdateInterval) throws SlickException {
		if (gameState == GAME_OVER) {
			gameOverTimer -= lastUpdateInterval;
			if (gameOverTimer <= 0)
				startUp(container);
		} else {
			// get user input
			Input input = container.getInput();

			if (gameState == START_UP) {
				if (input.isKeyDown(Input.KEY_SPACE))
					newGame(container);
			} else {
				if (input.isKeyDown(Input.KEY_W)) {
					
				}
				if (input.isKeyDown(Input.KEY_S)) {
					
				}
				if (input.isKeyDown(Input.KEY_A)) {
					
				}
				if (input.isKeyDown(Input.KEY_D)) {
					
				}
				
			}
			
		}

		/*if (gameState == PLAYING && ) {
			gameOver();
		}*/
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		switch(gameState){
		case START_UP:
			//render stuff
		case PLAYING:
			//render stuff
		case GAME_OVER:
			//render stuff
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		
	}

	@Override
	public void mouseWheelMoved(int change) {
		
	}

	public int getLogicInterval() {
		return logicInterval;
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Main("CS447 Project 2 [To Be Named]"), 800, 600, false);
			appgc.setDisplayMode(800, 600, false);
			// Fixed time step, min == max.
			appgc.setMinimumLogicUpdateInterval(logicInterval);
			appgc.setMaximumLogicUpdateInterval(logicInterval);
			appgc.setUpdateOnlyWhenVisible(false);
			appgc.setAlwaysRender(true);
			appgc.setVSync(true);
			// Maximum Frame Rate
			appgc.setTargetFrameRate(120);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

