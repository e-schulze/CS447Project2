package edu.wsu.vancouver.ssdd.tests.bitmap;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

import edu.wsu.vancouver.ssdd.Map;
import edu.wsu.vancouver.ssdd.MapLoader;

public class BitmapMain extends BasicGame {
	public static final int logicInterval = 20;
	public static int windowWidth;
	public static int windowHeight;

	private Map map;
	private Image screenBuffer;

	private UnsetBitTest u;

	public BitmapMain(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		windowWidth = gc.getWidth();
		windowHeight = gc.getHeight();
		gc.getGraphics().setBackground(Color.black);

		jig.ResourceManager.loadImage("rsc/dungeontiles.gif");
		jig.ResourceManager.loadImage("rsc/TestImage.png");

		MapLoader mapLoader = new MapLoader("rsc/dungeontiles.gif", 9, 10);
		mapLoader.printTileInfo();
		
		map = mapLoader.loadMap("rsc/maps/map_test.tmx");
		
		screenBuffer = map.getViewableArea(0, 0, windowWidth, windowHeight);

		u = new UnsetBitTest(map);

	}

	@Override
	public void update(GameContainer gc, int lastUpdateInterval) throws SlickException {
		screenBuffer = map.getViewableArea(0, 0, windowWidth, windowHeight);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawImage(screenBuffer, 0.0f, 0.0f);
	}

	@Override
	public void keyPressed(int key, char c) {

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		u.unsetBit(x, y);
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
			appgc = new AppGameContainer(new BitmapMain("CS447 Project 2 [To Be Named]"), 800, 600, false);
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
			Logger.getLogger(BitmapMain.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
