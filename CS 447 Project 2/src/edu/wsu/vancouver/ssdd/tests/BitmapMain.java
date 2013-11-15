package edu.wsu.vancouver.ssdd.tests;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;

public class BitmapMain extends BasicGame {
	public static final int logicInterval = 20;

	public BitmapMain(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		jig.ResourceManager.loadImage("rsc/TestImage.png");
		Image image = jig.ResourceManager.getImage("rsc/TestImage.png");
		Texture texture = image.getTexture();
		System.out.println(texture.getImageHeight());
		System.out.println(texture.getImageWidth());
		
		System.out.println("Working Directory = "
				+ System.getProperty("user.dir"));

	}

	@Override
	public void update(GameContainer gc, int lastUpdateInterval)
			throws SlickException {

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

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
			appgc = new AppGameContainer(new BitmapMain(
					"CS447 Project 2 [To Be Named]"), 800, 600, false);
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
			Logger.getLogger(BitmapMain.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}
