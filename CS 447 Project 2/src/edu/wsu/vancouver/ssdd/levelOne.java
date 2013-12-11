package edu.wsu.vancouver.ssdd;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import edu.wsu.vancouver.ssdd.EntityFactory.EntityType;
import edu.wsu.vancouver.ssdd.collision.CollisionBruteForce;
import edu.wsu.vancouver.ssdd.tests.UnsetBitTest;

import java.util.ArrayList;
import java.util.Iterator;

import jig.Entity;
import jig.ResourceManager;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import edu.wsu.vancouver.ssdd.menu.Main;

public class levelOne extends BasicGameState {
	private final int ScreenWidth;
	private final int ScreenHeight;
	private int getID_state;
	int posX;
	int posY;
	
	public static final int logicInterval = 20;

	private enum GameState {
		START_UP, PLAYING, GAME_OVER;
	}

	public static int windowWidth;
	public static int windowHeight;

	private GameState gameState;
	private int gameOverTimer;

	private Input input;
	private Camera camera;
	private MapLoader mapLoader;
	private Map map;
	private Background background;
	private Image screenBuffer;

	private CollisionBruteForce collisionBf;

	private EntityManager entityManager;
	private EntityFactory entityFactory;
	private EntityLoader entityLoader;

	private UnsetBitTest u;
	private int level;
	private int winTimer;

	public levelOne(int state, int ScreenWidth, int ScreenHeight){
		this.ScreenHeight = ScreenHeight;
		this.ScreenWidth = ScreenWidth;
		
		getID_state = state;

	}

	public void init(GameContainer gc, StateBasedGame sbg) {
		startUp(gc);
	}

	public void startUp(GameContainer gc) {
		gameState = GameState.START_UP;

		this.input = gc.getInput();
		input.enableKeyRepeat();
		windowWidth = gc.getWidth();
		windowHeight = gc.getHeight();
		gc.setSoundOn(false);
		gc.getGraphics().setBackground(Color.black);

		Resources.loadImages();
		Resources.loadSounds();

		mapLoader = new MapLoader();

		entityManager = new EntityManager();
		entityFactory = new EntityFactory(entityManager, input);
		entityLoader = new EntityLoader(entityFactory);

		collisionBf = new CollisionBruteForce(entityManager);
	}

	public void newGame(GameContainer gc) throws SlickException {
		gameState = GameState.PLAYING;
		gc.setSoundOn(true);

		level = 1;
		
		map = mapLoader.loadMap("maps/mapLevelOne.tmx");
		map.printMapInfo();

		camera = new Camera(0.0f, 0.0f, windowWidth, windowHeight, map);
		background = new Background(ResourceManager.getImage("images/moon_background.jpg"), camera, map, windowWidth,
				windowHeight);
		screenBuffer = map.getViewableArea(0, 0, windowWidth, windowHeight);

		u = new UnsetBitTest(map);

		entityFactory.updateMap(map);
		entityFactory.updateCamera(camera);
		try {
			entityLoader.loadEntity("rsc/maps/map_test_entities.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		);
		entityFactory.createEntity(EntityType.DOOR, 300.0f, 100.0f);
		entityManager.entityCreateProcess();
	}

	public void gameOver() {
		gameState = GameState.GAME_OVER;
		gameOverTimer = 4000;
	}

	public void makeMenu() {
		// MenuLevel mLev = new MenuLevel(entity, x, y);
	}
	
	public void nextLevel() throws SlickException{
		switch(level){
		case 1:
			level++;
			map = mapLoader.loadMap("maps/mapLevelTwo.tmx");
			entityFactory.updateMap(map);
			camera.updateMap(map);
			entityFactory.updateCamera(camera);
			entityManager.clearAllObjects();
			try {
				entityLoader.loadEntity("rsc/maps/map_test_entities.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			// add player and enemies and door;
			break;
		case 2:
			level++;
			map = mapLoader.loadMap("maps/mapLevelThree.tmx");
			entityFactory.updateMap(map);
			camera.updateMap(map);
			entityFactory.updateCamera(camera);
			entityManager.clearAllObjects();
			try {
				entityLoader.loadEntity("rsc/maps/map_test_entities.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			// add player and enemies and door;
			break;
		case 3:
			gameOver();
			break;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int lastUpdateInterval)
			throws SlickException {
		
		switch (gameState) {
		// Start up only once (need an intermediary state between start up and
		// playing preferably menu
		case START_UP:
			gameState = GameState.PLAYING;
			// Temporary, remove when menu is implemented.
			newGame(gc);
			break;
		case PLAYING:
			screenBuffer = map.getViewableArea((int) camera.getTlx(), (int) camera.getTly(), windowWidth, windowHeight);
			entityManager.entityCreateProcess();
			entityManager.entityDeleteProcess();
			collisionBf.detectCollision();
			entityManager.updateEntities(lastUpdateInterval);
			if(map.win == true){
				nextLevel();
			}
			if(map.die == true){
				gameOver();
			}
			break;
		case GAME_OVER:
			gameOverTimer -= lastUpdateInterval;
			if (gameOverTimer <= 0) {
				// TODO: create a main screen
				gc.exit();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		switch (gameState) {
		case START_UP:
			break;
		case PLAYING:
			g.drawImage(background.getBackGroundShot(), 0.0f, 0.0f);
			g.drawImage(screenBuffer, 0.0f, 0.0f);
			entityManager.renderEntities(g, camera);
			break;
		case GAME_OVER:
			break;
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_I:
			camera.changeTly(-5.0f);
			break;
		case Input.KEY_K:
			camera.changeTly(5.0f);
			break;
		case Input.KEY_J:
			camera.changeTlx(-5.0f);
			break;
		case Input.KEY_L:
			camera.changeTlx(5.0f);
			break;
		default:
			break;
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		// For testing
		if (camera != null) {
			x += camera.getTlx();
			y += camera.getTly();
			u.unsetBit2(x, y, 20);
		}
	}

	@Override
	public void mouseWheelMoved(int change) {

	}

	public int getLogicInterval() {
		return logicInterval;
	}
	public int getID() {
	      return getID_state;
	}
}
