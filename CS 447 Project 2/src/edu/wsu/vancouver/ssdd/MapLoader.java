package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class MapLoader {
	private SpriteSheet tileset;
	private int xTiles, yTiles;
	private int xTileSize, yTileSize;

	public MapLoader(String tilesetRsc, int xTiles, int yTiles) {
		Image image = jig.ResourceManager.getImage(tilesetRsc);

		this.xTiles = xTiles;
		this.yTiles = yTiles;
		this.xTileSize = image.getWidth() / xTiles;
		this.yTileSize = image.getHeight() / yTiles;
		this.tileset = jig.ResourceManager.getSpriteSheet(tilesetRsc, xTileSize, yTileSize);
	}

	/**
	 * Must use the Tiled Map Editor (c) as a tmx file. The tmx file must use a
	 * relative path tileset reference name with respect to the tile map file
	 * and be saved in the gzip compression format. Slick does not provide
	 * support for the other compression format.
	 * 
	 * Grid coordinates in the map are index 0 as well as layers. tileId type is
	 * index 0. A value of 0 is for non-type. tileset getSprite is index 0.
	 */
	public Map loadMap(String mapFileRsc) throws SlickException {
		TiledMap tiledMap = new TiledMap(mapFileRsc);
		Map map = new Map(tiledMap);
		map.printMapInfo();

		for (int y = 0; y < map.getYGrids(); y++) {
			for (int x = 0; x < map.getXGrids(); x++) {
				int tileId = 0, xTile = 0, yTile = 0;
				if ((tileId = tiledMap.getTileId(x, y, 0)) != 0) {
					xTile = (tileId - 1) % xTiles;
					yTile = (tileId - 1) / (yTiles - 1);
					Image tile = tileset.getSprite(xTile, yTile);
					map.loadTile(tile, x, y, true);
				}
				if ((tileId = tiledMap.getTileId(x, y, 1)) != 0) {
					xTile = (tileId - 1) % xTiles;
					yTile = (tileId - 1) / (yTiles - 1);
					Image tile = tileset.getSprite(xTile, yTile);
					map.loadTile(tile, x, y, false);
				}
			}
		}

		return map;
	}

	public void printTileInfo() {
		System.out.println("XTiles: " + xTiles);
		System.out.println("YTiles: " + yTiles);
		System.out.println("XTilesSize: " + xTileSize);
		System.out.println("YTilesSize: " + yTileSize);
	}

}
