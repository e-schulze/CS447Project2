package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.tiled.TiledMap;

public class Map {
	/** Map size dimensions */
	private int mapWidth, mapHeight;
	/** Number of tiles that fits on the map */
	private int xTiles, yTiles;
	/** Size of each tile on the map. */
	private int xTileSize, yTileSize;

	private boolean[][] destructibleMap;
	private ImageBuffer backbuffer;
	private Image backbufferImage;
	private boolean backbufferChanged;

	public Map(TiledMap tiledMap) {
		this.xTiles = tiledMap.getWidth();
		this.yTiles = tiledMap.getHeight();
		
		this.xTileSize = tiledMap.getTileWidth();
		this.yTileSize = tiledMap.getTileHeight();

		this.mapWidth = xTiles * xTileSize;
		this.mapHeight = yTiles * yTileSize;

		this.destructibleMap = new boolean[mapWidth][mapHeight];
		this.backbuffer = new ImageBuffer(mapWidth, mapHeight);
		this.backbufferImage = null;
		this.backbufferChanged = true;
	}
	
	public void printMapInfo() {
		System.out.println("MapWidth: " + mapWidth);
		System.out.println("MapHeight: " + mapHeight);
		System.out.println("XTiles: " + xTiles);
		System.out.println("YTiles: " + yTiles);
		
		System.out.println("LENGTH: " + backbuffer.getRGBA().length);
	}

	/**
	 * Given an image and position on the map, load the image into the bitmap
	 * backbuffer.
	 */
	public void loadNonTile(Image image, int tlx, int tly, boolean isDestructible) {
		int[] rawImage = loadBits(image);
		addToBackbuffer(tlx, tly, image.getWidth(), image.getHeight(), rawImage, isDestructible);
	}

	/**
	 * Given a tile and grid position on the map, load the image into the bitmap
	 * backbuffer.
	 */
	public void loadTile(Image tile, int xGrid, int yGrid, boolean isDestructible) {
		int[] rawImage = loadBits(tile);
		addToBackbuffer(xGrid * xTileSize, yGrid * yTileSize, tile.getWidth(), tile.getHeight(), rawImage,
				isDestructible);
	}

	/**
	 * Loads the raw image bytes into a int array in row-col major. Each cell is
	 * organized by RGBA. Can use the byte data type, but need to use the
	 * unsigned byte trick.
	 */
	public int[] loadBits(Image image) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int[] rawImage = new int[imageWidth * imageHeight * 4];

		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++) {
				Color color = image.getColor(x, y);
				rawImage[(y * imageWidth * 4) + (x * 4) + 0] = color.getRed();
				rawImage[(y * imageWidth * 4) + (x * 4) + 1] = color.getGreen();
				rawImage[(y * imageWidth * 4) + (x * 4) + 2] = color.getBlue();
				rawImage[(y * imageWidth * 4) + (x * 4) + 3] = color.getAlpha();
			}
		}

		return rawImage;
	}

	/**
	 *
	 */
	public void addToBackbuffer(int tlx, int tly, int imageWidth, int imageHeight, int[] rawImage,
			boolean isDestructible) {
		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++) {
				// Set destructible value
				if (isDestructible) {
					destructibleMap[tlx + x][tly + y] = true;
				} else {
					destructibleMap[tlx + x][tly + y] = false;
				}

				// Set backbuffer pixel value
				backbuffer.setRGBA(tlx + x, tly + y, rawImage[(y * imageWidth * 4) + (x * 4) + 0], rawImage[(y
						* imageWidth * 4)
						+ (x * 4) + 1], rawImage[(y * imageWidth * 4) + (x * 4) + 2], rawImage[(y * imageWidth * 4)
						+ (x * 4) + 3]);
			}
		}
	}

	/**
	 * 
	 */
	public Image getViewableArea(int tlx, int tly, int width, int height) {
		if (backbufferChanged || backbufferImage == null) {
			backbufferImage = backbuffer.getImage();
			backbufferChanged = false;
		}
		Image subImage = backbufferImage.getSubImage(tlx, tly, width, height);
		return subImage;
	}
	
	public ImageBuffer getBackbuffer() {
		return backbuffer;
	}

	public boolean[][] getDestructibleMap() {
		return destructibleMap;
	}

	public void setBackbufferChanged() {
		backbufferChanged = true;
	}
	
	protected int getXTiles() {
		return xTiles;
	}

	protected int getYTiles() {
		return yTiles;
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}

}
