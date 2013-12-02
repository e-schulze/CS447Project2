package edu.wsu.vancouver.ssdd.tests;

import jig.ResourceManager;
import jig.Vector;

import edu.wsu.vancouver.ssdd.EntityManager;
import edu.wsu.vancouver.ssdd.GameEntity;
import edu.wsu.vancouver.ssdd.Map;

public class TestEntity extends GameEntity {
	private Map map;
	
	public TestEntity(EntityManager entityManager, Map map) {
		this(entityManager, map, 0.0f, 0.0f);
	}

	public TestEntity(EntityManager entityManager, Map map, float cxp, float cyp) {
		super(entityManager, cxp, cyp);
		this.map = map;
		entityMask.set(GameEntity.EntityProperty.NONE.getValue());
		addImageWithBoundingBox(ResourceManager.getImage("images/TestImage.png"));
	}

	@Override
	public void update(int delta) {
		float gravity = 1.0f;
		Vector op = getPosition();
		setPosition(new Vector(op.getX(), op.getY() + gravity));
		
		if (bfBitDetectionBottom()) {
			System.out.println("TRUE");
			setPosition(new Vector(op.getX(), op.getY() - gravity));
		} else {
			System.out.println("FALSE");
		}
	}
	
	boolean bfBitDetectionBottom() {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();
		int y = (int) getCoarseGrainedMaxY();

		for (int x = (int) getCoarseGrainedMinX(); x < (int) getCoarseGrainedMaxX(); x++) {
			System.out.println("X: " + x + "Y: " + y);
			if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
				continue;
			}
			if (!testBitDestructed(x, y)) {
				return true;
			}
		}
		return false;
	}
	
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

}
