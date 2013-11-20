package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class MapLoader {
	/** Image file used to create the map with tiles */
	private SpriteSheet tileset;
	/** Number of tiles on the tileset */
	private int xTileset, yTileset;
	/** Size of each tile in the tileset */
	private int xTileSize, yTileSize;

	private Map currentMap; 
	
	public MapLoader(String tilesetRsc, int xTileset, int yTileset) {
		Image image = jig.ResourceManager.getImage(tilesetRsc);

		this.xTileset = xTileset;
		this.yTileset = yTileset;
		this.xTileSize = image.getWidth() / xTileset;
		this.yTileSize = image.getHeight() / yTileset;
		this.tileset = jig.ResourceManager.getSpriteSheet(tilesetRsc, xTileSize, yTileSize);
		
		this.currentMap = null;
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

		for (int y = 0; y < map.getYTiles(); y++) {
			for (int x = 0; x < map.getXTiles(); x++) {
				int tileId = 0, xTile = 0, yTile = 0;
				if ((tileId = tiledMap.getTileId(x, y, 0)) != 0) {
					xTile = (tileId - 1) % xTileset;
					yTile = (tileId - 1) / (yTileset - 1);
					Image tile = tileset.getSprite(xTile, yTile);
					map.loadTile(tile, x, y, true);
				}
				if ((tileId = tiledMap.getTileId(x, y, 1)) != 0) {
					xTile = (tileId - 1) % xTileset;
					yTile = (tileId - 1) / (yTileset - 1);
					Image tile = tileset.getSprite(xTile, yTile);
					map.loadTile(tile, x, y, false);
				}
			}
		}
		
		currentMap = map;
		return map;
	}

	public void printTileInfo() {
		System.out.println("XTiles: " + xTileset);
		System.out.println("YTiles: " + yTileset);
		System.out.println("XTilesSize: " + xTileSize);
		System.out.println("YTilesSize: " + yTileSize);
	}

	public Map getCurrentMap() {
		return currentMap;
	}
}
