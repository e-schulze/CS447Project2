package edu.wsu.vancouver.ssdd;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Bomb extends GameEntity {
	public enum BombType {
		C4, GRENADE, CHARGE;
	}

	private BombType bombType;

	private enum EntityState {
		NORMAL, AIR;
	}

	private EntityState eState;

	private Map map;

	private int blastRadius;
	private Vector vel;
	private int timer;
	private String dirBlast;

	public Bomb(EntityManager entityManager, Map map, BombType bombType, String dir) {
		this(entityManager, map, 0.0f, 0.0f, bombType, dir);
	}

	public Bomb(EntityManager entityManager, Map map, float x, float y, BombType bombType, String dir) {
		super(entityManager, x, y);
		this.map = map;
		this.eState = EntityState.AIR;
		
		this.bombType = bombType;
		switch (bombType) {
		case C4:
			this.addImageWithBoundingBox(ResourceManager.getImage("images/bomb1.png"));
			this.scale(0.25f);
			break;
		case GRENADE:
			this.addImageWithBoundingBox(ResourceManager.getImage("images/bomb1.png"));
			this.scale(0.25f);
			this.timer = 1000;
			break;
		case CHARGE:
			this.addImageWithBoundingBox(ResourceManager.getImage("images/bomb1.png"));
			this.scale(0.25f);
			break;
		}

		vel = new Vector(0.0f, 0.0f);
		/*
		 * if(dir.compareTo("Left") == 0){ vel = new Vector(-0.075f, 0.05f); }
		 * else if(dir.compareTo("Right") == 0){ vel = new Vector(0.075f,
		 * 0.05f); } else if(dir.compareTo("Down") == 0){ vel = new Vector(0f,
		 * 0f); }
		 */
	}

	public void update(int delta) {
		explosionTimer(delta);
		bitCollision();
		gravity();
	}

	private void explosionTimer(int delta) {
		if (timer > 0) {
			timer -= delta;
		} else if (timer <= 0) {
			// Remove entity next loop
			entityManager.entityDeleteMark(getEntityId());
			// Create explosion
			new Explosion(entityManager, map, getPosition().getX(), getPosition().getY());
		}
	}

	/**
	 * Prevents entities from clipping the terrain.
	 */
	private void bitCollision() {
		// Test inner left
		if (bfBitDetection((int) getCoarseGrainedMinX(), (int) getCoarseGrainedMinX() + 1,
				(int) getCoarseGrainedMinY(), (int) getCoarseGrainedMaxY())) {
			vel = vel.setX(0.0f);
			Vector p = getPosition();
			setPosition(p.getX() + 1.0f, p.getY());
		}
		// Test inner right
		if (bfBitDetection((int) getCoarseGrainedMaxX() - 1, (int) getCoarseGrainedMaxX(),
				(int) getCoarseGrainedMinY(), (int) getCoarseGrainedMaxY())) {
			vel = vel.setX(0.0f);
			Vector p = getPosition();
			setPosition(p.getX() - 1.0f, p.getY());
		}
		// Test inner upper
		if (bfBitDetection((int) getCoarseGrainedMinX(), (int) getCoarseGrainedMaxX(), (int) getCoarseGrainedMinY(),
				(int) getCoarseGrainedMinY() + 1)) {
			vel = vel.setY(0.0f);
			Vector p = getPosition();
			setPosition(p.getX(), p.getY() + 1.0f);
		}
		// Test inner bottom
		if (bfBitDetection((int) getCoarseGrainedMinX(), (int) getCoarseGrainedMaxX(),
				(int) getCoarseGrainedMaxY() - 1, (int) getCoarseGrainedMaxY())) {
			vel = vel.setY(0.0f);
			Vector p = getPosition();
			setPosition(p.getX(), p.getY() - 1.0f);
			if (eState == EntityState.AIR) {
				eState = EntityState.NORMAL;
			}
		}
	}

	private void gravity() {
		float gravity = 0.1f;
		if (eState == EntityState.AIR) {
			vel = vel.add(new Vector(0.0f, gravity));
			setPosition(getPosition().add(vel));
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

	public int getBlastRadius() {
		return blastRadius;
	}

	public Vector getVel() {
		return vel;
	}

	public BombType getBombType() {
		return bombType;
	}

	public int getTimer() {
		return timer;
	}

	private String getDirBlast() {
		return dirBlast;
	}
}
