package edu.wsu.vancouver.ssdd;

import jig.ResourceManager;
import jig.Vector;

public class Bullet extends GameEntity {
	public enum BulletType {
		C4, GUN_BULLET, CHARGE;
	}
	
	private enum AnimationState {
		MOVING_LEFT, MOVING_RIGHT;
	}

	private BulletType BulletType;
	private AnimationState bulletMovement;

	private enum EntityState {
		NORMAL, AIR;
	}

	private EntityState eState;

	private Map map;

	private int blastRadius;
	private Vector vel;
	private int timer;
	private String dirBlast;
	public int damage;

	public Bullet(EntityManager entityManager, Map map, BulletType BulletType, String dir) {
		this(entityManager, map, 0.0f, 0.0f, BulletType, dir);
	}

	public Bullet(EntityManager entityManager, Map map, float x, float y, BulletType BulletType, String dir) {
		super(entityManager, x, y);
		this.map = map;
		damage = 10;
		this.eState = EntityState.AIR;
		entityMask.set(GameEntity.EntityProperty.BULLET.getValue());

		
		this.BulletType = BulletType;
		switch (BulletType) {
		case C4:
			this.addImageWithBoundingBox(ResourceManager.getImage("images/bullet.png"));
			this.scale(0.25f);
			break;
		case GUN_BULLET:
			this.addImageWithBoundingBox(ResourceManager.getImage("images/bullet.png"));
			this.scale(0.25f);
			this.timer = 1000;
			break;
		case CHARGE:
			this.addImageWithBoundingBox(ResourceManager.getImage("images/bullet.png"));
			this.scale(0.25f);
			break;
		}

		vel = new Vector(0.0f, 0.0f);
		
		 if(dir.compareTo("Left") == 0){ bulletMovement = AnimationState.MOVING_LEFT;}
		 else if(dir.compareTo("Right") == 0){ bulletMovement= AnimationState.MOVING_RIGHT; }
		 
		float speedBullet = 4.8f;

		if (eState == EntityState.AIR) {
			if ( bulletMovement == AnimationState.MOVING_LEFT){
				vel = vel.add(new Vector(-speedBullet, 0.0f));

			}else if ( bulletMovement == AnimationState.MOVING_RIGHT){ // Moving Right
				vel = vel.add(new Vector(speedBullet, 0.0f));
			}
		}
	}

	public void update(int delta) {
		//explosionTimer(delta);
		bitCollision();
		//gravity();
		setPosition(getPosition().add(vel));
	}
	
	public void collision(GameEntity gameEntity) {
		entityManager.entityDeleteMark(getEntityId());
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

			// Remove entity next loop
			entityManager.entityDeleteMark(getEntityId());
		}
		// Test inner right
		if (bfBitDetection((int) getCoarseGrainedMaxX() - 1, (int) getCoarseGrainedMaxX(),
				(int) getCoarseGrainedMinY(), (int) getCoarseGrainedMaxY())) {
			vel = vel.setX(0.0f);
			Vector p = getPosition();
			setPosition(p.getX() - 1.0f, p.getY());
			
			// Remove entity next loop
			entityManager.entityDeleteMark(getEntityId());
		}
		// Test inner upper
		if (bfBitDetection((int) getCoarseGrainedMinX(), (int) getCoarseGrainedMaxX(), (int) getCoarseGrainedMinY(),
				(int) getCoarseGrainedMinY() + 1)) {
			vel = vel.setY(0.0f);
			Vector p = getPosition();
			setPosition(p.getX(), p.getY() + 1.0f);
			
			// Remove entity next loop
			entityManager.entityDeleteMark(getEntityId());
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
			
			// Remove entity next loop
			entityManager.entityDeleteMark(getEntityId());
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

	public BulletType getBulletType() {
		return BulletType;
	}

	public int getTimer() {
		return timer;
	}

	private String getDirBlast() {
		return dirBlast;
	}
}
