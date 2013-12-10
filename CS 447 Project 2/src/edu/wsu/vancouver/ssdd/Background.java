package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Image;

public class Background {
	private Camera camera;
	private Map map;
	
	private Image background;
	private int backgroundWidth, backgroundHeight;
	private int windowWidth, windowHeight;
	
	
	public Background(Image background, Camera camera, Map map, int windowWidth, int windowHeight) {
		this.camera = camera;
		this.map = map;
		this.background = background;
		this.backgroundWidth = background.getWidth();
		this.backgroundHeight = background.getHeight();
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
	}
	
	public Image getBackGroundShot() {
		int x = (int) Math.round(camera.getTlx() / map.getMapWidth() * backgroundWidth);
		if (x + windowWidth > backgroundWidth) {
			x = backgroundWidth - windowWidth;
		}
		int y = (int) Math.round(camera.getTly() / map.getMapHeight() * backgroundHeight);
		if (y + windowHeight > backgroundHeight) {
			y = backgroundHeight - windowHeight;
		}
		return background.getSubImage(x, y, windowWidth, windowHeight);
	}
	
	public Image getBackground() {
		return background;
	}

}
