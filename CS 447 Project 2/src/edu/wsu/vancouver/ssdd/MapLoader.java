package edu.wsu.vancouver.ssdd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

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

	/** Map file format:
	 * (int)mapWidth (int)mapHeight (int)tlx (int)tlxo (int)tly (int)tlyo
	 * (int)tileNum (int)xGrid (int)yGrid (boolean)isDestructible
	 * 	.
	 * 	.
	 * 	.
	 * 	etc...
	 * 
	 * @param mapFileRsc
	 * @return
	 * @throws IOException
	 */
	public Map loadMap(String mapFileRsc) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(mapFileRsc));
		String[] s = br.readLine().split(" ");
		
		int mapWidth = Integer.parseInt(s[0]);
		int mapHeight = Integer.parseInt(s[1]);
		int tlx = Integer.parseInt(s[2]);
		int tlxo = Integer.parseInt(s[3]);
		int tly = Integer.parseInt(s[4]);
		int tlyo = Integer.parseInt(s[5]);
		
		Map map = new Map(tlx, tlxo, tly, tlyo,  mapWidth, mapHeight, xTileSize, yTileSize);
		
		String line;
		while((line = br.readLine()) != null) {
			if (line.equals("")) {
		        break;
		    }
			s = line.split(" ");
			int tileNum = Integer.parseInt(s[0]);
			int xGrid = Integer.parseInt(s[1]);
			int yGrid = Integer.parseInt(s[2]);
			boolean isDestructible = Boolean.parseBoolean(s[3]);
			
			// This does not check for tileset size of 1 (division by 0)
			// Zero index in col and row and tileNum. 
			int xTile = tileNum % xTiles;
			int yTile = tileNum / (yTiles - 1);
			Image tile = tileset.getSprite(xTile, yTile);
			
			map.loadTile(tile, xGrid, yGrid, isDestructible);
		}
		
		br.close();
		
		return map;
	}

}
