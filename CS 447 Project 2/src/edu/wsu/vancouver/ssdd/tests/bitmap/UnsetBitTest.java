package edu.wsu.vancouver.ssdd.tests.bitmap;

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
				backbuffer.setRGBA(x + j, y + i, 0, 0, 0, 0);
			}
		}
		
		map.setBackbufferChanged();
	}
}
