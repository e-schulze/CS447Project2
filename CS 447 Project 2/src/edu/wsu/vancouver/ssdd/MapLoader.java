package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class MapLoader {
	private Map currentMap;

	public MapLoader() {
		this.currentMap = null;
	}

	/**
	 * Must use the Tiled Map Editor (c) as a tmx file. The tmx file must use a
	 * relative path tileset reference name with respect to the tile map file
	 * and be saved in the gzip compression format. Slick does not provide
	 * support for the other compression format.
	 * 
	 * Grid coordinates in the map are index 0 as well as layers. tileId type is
	 * index 0. A value of 0 is for non-type.
	 */
	public Map loadMap(String mapFileRsc) throws SlickException {
		TiledMap tiledMap = new TiledMap(mapFileRsc);
		Map map = new Map(tiledMap);

		for (int y = 0; y < map.getYTiles(); y++) {
			for (int x = 0; x < map.getXTiles(); x++) {
				if ((tiledMap.getTileId(x, y, 0)) != 0) {
					Image tile = tiledMap.getTileImage(x, y, 0);
					map.loadTile(tile, x, y, true);
				}
				if ((tiledMap.getTileId(x, y, 1)) != 0) {
					Image tile = tiledMap.getTileImage(x, y, 1);
					map.loadTile(tile, x, y, false);
				}
			}
		}

		currentMap = map;
		return map;
	}

	public Map getCurrentMap() {
		return currentMap;
	}
}
