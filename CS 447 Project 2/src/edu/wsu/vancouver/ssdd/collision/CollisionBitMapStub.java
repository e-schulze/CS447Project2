package edu.wsu.vancouver.ssdd.collision;

import edu.wsu.vancouver.ssdd.GameEntity;
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
		int bbWidth = map.getBackbuffer().getTexWidth();
		byte[] rgba = map.getBackbuffer().getRGBA();
		if (rgba[(bbWidth * yp * 4) + (xp * 4) + 3] == 0) {
			return true;
		} else {
			System.out.println("RGBA" + rgba[(bbWidth * yp * 4) + (xp * 4) + 3]);
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

	/* Collision Detection Bit Level */

	/**
	 * Brute force bit level collision detection. Checks if every single bit in
	 * the entity collides with the map.
	 */

	boolean bfBitDetection(GameEntity ge) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();

		for (int x = (int) ge.getCoarseGrainedMinX(); x < (int) ge.getCoarseGrainedMaxX(); x++) {
			for (int y = (int) ge.getCoarseGrainedMinY(); y < (int) ge.getCoarseGrainedMaxY(); y++) {
				if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
					continue;
				}
				if (!testBitDestructed(x, y)) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * Generalized bit level detection. In linear / rectangular depending on the
	 * start and end of x and y.
	 */
	boolean bfBitDetection(GameEntity ge, int xs, int xe, int ys, int ye) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();

		for (int x = xs; x <= xe; x++) {
			for (int y = ys; y <= ye; y++) {
				if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
					continue;
				}
				if (!testBitDestructed(x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	boolean bfBitDetectionLeft(GameEntity ge) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();
		int x = (int) ge.getCoarseGrainedMinX();

		for (int y = (int) ge.getCoarseGrainedMinY(); y < (int) ge.getCoarseGrainedMaxY(); y++) {
			if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
				continue;
			}
			if (!testBitDestructed(x, y)) {
				return true;
			}
		}
		return false;
	}

	boolean bfBitDetectionRight(GameEntity ge) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();
		int x = (int) ge.getCoarseGrainedMaxX();

		for (int y = (int) ge.getCoarseGrainedMinY(); y < (int) ge.getCoarseGrainedMaxY(); y++) {
			if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
				continue;
			}
			if (!testBitDestructed(x, y)) {
				return true;
			}
		}
		return false;
	}

	boolean bfBitDetectionTop(GameEntity ge) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();
		int y = (int) ge.getCoarseGrainedMinY();

		for (int x = (int) ge.getCoarseGrainedMinX(); x < (int) ge.getCoarseGrainedMaxX(); x++) {
			if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
				continue;
			}
			if (!testBitDestructed(x, y)) {
				return true;
			}
		}
		return false;
	}

	boolean bfBitDetectionBottom(GameEntity ge) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();
		int y = (int) ge.getCoarseGrainedMaxY();

		for (int x = (int) ge.getCoarseGrainedMinX(); x < (int) ge.getCoarseGrainedMaxX(); x++) {
			if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
				continue;
			}
			if (!testBitDestructed(x, y)) {
				return true;
			}
		}
		return false;
	}
}
