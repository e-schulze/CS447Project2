package edu.wsu.vancouver.ssdd.collision;

import edu.wsu.vancouver.ssdd.Map;

/**
 * A collection of stubs that can be used for collision detection and handling
 * at the bit level. Please do not instantiate this class, it is just an
 * example.
 */
public class CollisionBitMapStub {
	private Map map;

	public CollisionBitMapStub(Map map) {
		this.map = map;
	}

	boolean isCollidable(int xp, int yp) {
		return map.getDestructibleMap()[xp][yp];
	}

	/**
	 * Makes the bit transparent, no check for destructible.
	 */
	void setBitDestructed(int xp, int yp) {
		map.getBackbuffer().setRGBA(xp, yp, 0, 0, 0, 0);
	}

	/**
	 * Make a check on the alpha bit, when alpha is 0 it is determined as
	 * destroyed (and unseeable)
	 */
	boolean testBitDestructed(int xp, int yp) {
		int mapWidth = map.getBackbuffer().getWidth();
		byte[] rgba = map.getBackbuffer().getRGBA();
		if (rgba[mapWidth * yp + xp + 4] == 0) {
			return true;
		} else {
			return false;
		}
	}

	void rectangleDestruction(int cx, int cy, int halfWidth, int halfHeight) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();

		for (int x = cx - halfWidth; x < cx + halfWidth; x++) {
			for (int y = cy - halfHeight; y < cy + halfHeight; y++) {
				if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
					continue;
				}
				if (isCollidable(x, y)) {
					map.getBackbuffer().setRGBA(x, y, 0, 0, 0, 0);
				}
			}
		}
	}

	void circleDestruction(int cx, int cy, int r) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();
		for (int x = cx - r; x < cx + r; x++) {
			for (int y = cy - r; y < cy + r; y++) {
				if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
					continue;
				}
				// Checks if the distance square is in range.
				if (isCollidable(x, y) && ((cx - x) * (cx - x) + (cy - y) * (cy - y) <= r * r)) {
					map.getBackbuffer().setRGBA(x, y, 0, 0, 0, 0);
				}
			}
		}
	}

}
