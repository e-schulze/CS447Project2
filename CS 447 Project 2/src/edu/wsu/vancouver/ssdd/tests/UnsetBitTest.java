package edu.wsu.vancouver.ssdd.tests;

import org.newdawn.slick.ImageBuffer;

import edu.wsu.vancouver.ssdd.Map;

public class UnsetBitTest {
	private Map map;
	private ImageBuffer backbuffer;
	private boolean[][] destructibleMap;

	public UnsetBitTest(Map map) {
		this.map = map;
		this.backbuffer = map.getBackbuffer();
		this.destructibleMap = map.getDestructibleMap();
	}

	public void unsetBit(int x, int y) {
		System.out.println(x + " " + y);
		for (int i = -10; i < 10; i++) {
			for (int j = -10; j < 10; j++) {
				if (y + i < 0 || x + j < 0) {
					continue;
				}
				if (destructibleMap[x][y] == true) {
					backbuffer.setRGBA(x + j, y + i, 0, 0, 0, 0);
				}
			}
		}

		map.setBackbufferChanged();
	}
	
	public void unsetBit2(int cx, int cy, int r) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();
		for (int x = cx - r; x < cx + r; x++) {
			for (int y = cy - r; y < cy + r; y++) {
				if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
					continue;
				}
				// Checks if the distance square is in range.
				if (destructibleMap[x][y] == true && ((cx - x) * (cx - x) + (cy - y) * (cy - y) <= r * r)) {
					map.getBackbuffer().setRGBA(x, y, 0, 0, 0, 0);
				}
			}
		}
		map.setBackbufferChanged();
	}
}
