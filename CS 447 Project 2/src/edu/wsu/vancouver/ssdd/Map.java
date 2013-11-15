package edu.wsu.vancouver.ssdd;

import java.nio.ByteBuffer;

import org.newdawn.slick.Image;

public class Map {
	// Top left corner x position and its offset
	private float tlx;
	private float tlxo;
	// Top left corner y position and its offset
	private float tly;
	private float tlyo;

	// Map size dimensions
	private float mapWidth;
	private float mapHeight;

	// Size of the grids on the map. Sizes should be square
	private float gridSize;
	// Number of grids horizontally that fits on the map
	private int xGrids;
	// Number of grids vertically that fits on the map
	private int yGrids;

	private byte[] bitmap;

	public Map(float tlx, float tlxo, float tly, float tlyo, float mapWidth,
			float mapHeight, float gridSize) {
		this.tlx = tlx;
		this.tlxo = tlxo;
		this.tly = tly;
		this.tlyo = tlyo;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.gridSize = gridSize;

		this.xGrids = (int) Math.ceil(mapWidth / gridSize);
		this.yGrids = (int) Math.ceil(mapHeight / gridSize);
	}

	/*
	 * Given a tile and grid position on the map, load the image into the
	 * bitmap backbuffer.
	 */
	public void loadTileMap(int xGrid, int yGrid, Image tile) {
		tile.getTexture().getTextureData();
	}


	public int getXGrids() {
		return xGrids;
	}

	public int getYGrids() {
		return yGrids;
	}

	public byte[] getBitmap() {
		return bitmap;
	}

}
