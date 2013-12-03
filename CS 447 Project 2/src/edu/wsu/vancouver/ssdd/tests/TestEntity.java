package edu.wsu.vancouver.ssdd.tests;

import org.newdawn.slick.Input;

import jig.ResourceManager;
import jig.Vector;

import edu.wsu.vancouver.ssdd.EntityManager;
import edu.wsu.vancouver.ssdd.GameEntity;
import edu.wsu.vancouver.ssdd.Map;

public class TestEntity extends GameEntity {
	private enum PlayerState {
		NORMAL, AIR, SLIDING;
	}

	private Map map;
	private Input input;
	private PlayerState state = PlayerState.AIR;

	private Vector vel;
	private float maxVelSq;

	public TestEntity(EntityManager entityManager, Map map, Input input) {
		this(entityManager, map, input, 0.0f, 0.0f);
	}

	public TestEntity(EntityManager entityManager, Map map, Input input, float cxp, float cyp) {
		super(entityManager, cxp, cyp);
		this.map = map;
		this.input = input;
		entityMask.set(GameEntity.EntityProperty.NONE.getValue());
		addImageWithBoundingBox(ResourceManager.getImage("images/TestImage.png"));

		this.vel = new Vector(0.0f, 0.0f);
		this.maxVelSq = 16.0f;
	}

	@Override
	public void update(int delta) {
		movement();
		bitCollision();

		if (state == PlayerState.AIR) {
			float gravity = 0.1f;
			vel = vel.add(new Vector(0.0f, gravity));
			setPosition(getPosition().add(vel));
		}
	}

	private void movement() {
		Vector p = getPosition();

		// Up and only up, no diagonals
		if (input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_LEFT)) {
			setPosition(p.getX(), p.getY() - 1.0f);
			vel = vel.add(new Vector(0.0f, -1.0f));
		} // Down and only down, no diagonals
		else if (input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_RIGHT)
				&& !input.isKeyDown(Input.KEY_LEFT)) {
			setPosition(p.getX(), p.getY() + 1.0f);
		} // Left and only left, no diagonals
		else if (input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_UP)) {
			setPosition(p.getX() - 1.0f, p.getY());
		} // Right and only right, no diagonals
		else if (input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN)) {
			setPosition(p.getX() + 1.0f, p.getY());
		} // Up right
		else if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_RIGHT)) {

		} // Down right
		else if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_RIGHT)) {

		} // Down left
		else if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_LEFT)) {

		} // Up left
		else if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_LEFT)) {

		} // No commands, slow down horizontal velocity
		else {

		}

		setPosition(getPosition().add(vel));
	}

	/**
	 * Prevents entities from clipping the terrain.
	 */
	private void bitCollision() {
		int minX = (int) getCoarseGrainedMinX();
		int maxX = (int) getCoarseGrainedMaxX();
		int minY = (int) getCoarseGrainedMinY();
		int maxY = (int) getCoarseGrainedMaxY();
		// Test inner upper
		if (bfBitDetection(minX, maxX, minY, minY + 1)) {
			vel = vel.setY(0.0f);
			Vector p = getPosition();
			setPosition(p.getX(), p.getY() + 1.0f);
			System.out.println("IU");
		}
		// Test inner bottom
		if (bfBitDetection(minX, maxX, maxY - 1, maxY)) {
			vel = vel.setY(0.0f);
			Vector p = getPosition();
			setPosition(p.getX(), p.getY() - 1.0f);
			if (state == PlayerState.AIR) {
				state = PlayerState.NORMAL;
			}
			System.out.println("IB");
		}
		// Test inner left
		if (bfBitDetection(minX, minX + 1, minY, maxY)) {
			vel = vel.setX(0.0f);
			Vector p = getPosition();
			setPosition(p.getX() + 1.0f, p.getY());
			System.out.println("IL");
		}
		// Test inner right
		if (bfBitDetection(maxX - 1, maxX, minY, maxY)) {
			vel = vel.setX(0.0f);
			Vector p = getPosition();
			setPosition(p.getX() - 1.0f, p.getY());
			System.out.println("IR");
		}

		// Test outer bottom
		if (!bfBitDetection(minX, maxX, maxY, maxY + 1)) {
			if (state == PlayerState.NORMAL) {
				state = PlayerState.AIR;
			}
			System.out.println("OB");
		}
	}

	boolean bfBitDetection(int xs, int xe, int ys, int ye) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();

		for (int x = xs; x < xe; x++) {
			for (int y = ys; y < ye; y++) {
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

	boolean testBitDestructed(int xp, int yp) {
		int bbWidth = map.getBackbuffer().getTexWidth();
		byte[] rgba = map.getBackbuffer().getRGBA();
		if (rgba[(bbWidth * yp * 4) + (xp * 4) + 3] == 0) {
			return true;
		} else {
			return false;
		}
	}

}
