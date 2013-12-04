package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Animation;

import jig.ResourceManager;
import jig.Vector;

public class Enemy extends GameEntity {

	public enum EnemyType {
		ZOMBIE, ROBOT
	}

	private EnemyType enemyType;

	private enum AnimationState {
		MOVING_LEFT, MOVING_RIGHT;
	}

	private AnimationState aState;

	private enum EnemyState {
		NORMAL, AIR;
	}

	private EnemyState eState;

	private Map map;

	private Vector vel;
	private int type;
	private String dir;
	private Animation right;
	private Animation left;
	private final float maxVelSq;
	private int curHealth;
	private final int maxHealth;

	public Enemy(EntityManager entityManager, Map map, String dir, EnemyType enemyType) {
		this(entityManager, map, 0.0f, 0.0f, dir, enemyType);
	}

	public Enemy(EntityManager entityManager, Map map, float x, float y, String dir, EnemyType enemyType) {
		super(entityManager, x, y);
		this.enemyType = enemyType;
		this.dir = dir;
		this.map = map;
		
		
		this.eState = EnemyState.AIR;
		entityMask.set(GameEntity.EntityProperty.ENEMY.getValue());
		this.addImageWithBoundingBox(ResourceManager.getImage("images/PlayerStandingLeft.png"));
		this.removeImage(ResourceManager.getImage("images/PlayerStandingLeft.png"));
		switch (enemyType) {
		// Fall through
		default:
		case ZOMBIE:
			this.maxHealth = this.curHealth = 10;
			right = new Animation(ResourceManager.getSpriteSheet("images/ZombieRight.png", 34, 58), 175);
			left = new Animation();
			for (int i = 0; i < right.getFrameCount(); i++) {
				left.addFrame(right.getImage(i).getFlippedCopy(true, false), 175);
			}
			if (dir.compareTo("Right") == 0) {
				this.vel = new Vector(0.05f, 0f);
				this.addAnimation(right);
			} else {
				this.vel = new Vector(-0.05f, 0f);
				this.addAnimation(left);
			}
			break;
		case ROBOT:
			this.maxHealth = this.curHealth = 999999;
			right = new Animation(ResourceManager.getSpriteSheet("images/RobotRight.png", 33, 58), 143);
			left = new Animation();
			for (int i = 0; i < right.getFrameCount(); i++) {
				left.addFrame(right.getImage(i).getFlippedCopy(true, false), 143);
			}
			if (dir.compareTo("Right") == 0) {
				this.vel = new Vector(0.05f, 0f);
				this.addAnimation(right);
			} else {
				this.vel = new Vector(-0.05f, 0f);
				this.addAnimation(left);
			}
			break;
		}
		right.setLooping(true);
		left.setLooping(true);
		this.setVel(new Vector(0f, 0f)); // temp, get rid of after testing
		this.maxVelSq = 16.0f;
	}

	public int getHealth() {
		return curHealth;
	}

	public void setHealth(int health) {
		this.curHealth = health;
	}

	public void decHealth() {
		this.curHealth--;
	}

	public void incHealth() {
		this.curHealth++;
	}

	public Vector getVel() {
		return vel;
	}

	public void setVel(Vector vel) {
		this.vel = vel;
	}

	public void update(int delta) {
		// movement();
		bitCollision();
		gravity();
		health();
	}
	
	@Override
	public void collision(GameEntity gameEntity) {
	}

	private void bitCollision() {
		// Test inner left
		if (bfBitDetection((int) getCoarseGrainedMinX(), (int) getCoarseGrainedMinX() + 1,
				(int) getCoarseGrainedMinY(), (int) (getCoarseGrainedMinY() + getCoarseGrainedHeight() * 0.9f))) {
			vel = vel.setX(0.0f);
			Vector p = getPosition();
			setPosition(p.getX() + 1.0f, p.getY());
		}
		// Test inner right
		if (bfBitDetection((int) getCoarseGrainedMaxX() - 1, (int) getCoarseGrainedMaxX(),
				(int) getCoarseGrainedMinY(), (int) (getCoarseGrainedMinY() + getCoarseGrainedHeight() * 0.9f))) {
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
			if (eState == EnemyState.AIR) {
				eState = EnemyState.NORMAL;
			}
		}

		// Test outer bottom
		if (!bfBitDetection((int) getCoarseGrainedMinX(), (int) getCoarseGrainedMaxX(), (int) getCoarseGrainedMaxY(),
				(int) getCoarseGrainedMaxY() + 1)) {
			if (eState == EnemyState.NORMAL) {
				eState = EnemyState.AIR;
			}
		}
	}

	private void gravity() {
		if (eState == EnemyState.AIR) {
			float gravity = 0.1f;
			vel = vel.add(new Vector(0.0f, gravity));
			setPosition(getPosition().add(vel));
		}
	}
	
	private void health() {
		if (curHealth <= 0) {
			entityManager.entityDeleteMark(getEntityId());
		}
	}

	private String getDir() {
		return dir;
	}

	private void setDir(String dir) {
		this.dir = dir;
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
