package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

import jig.ResourceManager;
import jig.Vector;

public class PlayerCopy extends GameEntity {
	private enum AnimationState {
		STANDING_LEFT, MOVING_LEFT, STANDING_RIGHT, MOVING_RIGHT;
	}

	private AnimationState aState;

	private enum PlayerState {
		NORMAL, AIR, SLIDING;
	}

	private PlayerState pState;

	private Map map;
	private Input input;
	private Camera camera;

	private Animation right;
	private Animation left;

	private boolean jumped;

	private Vector vel;
	private final float maxVelSq;
	private int curHealth;
	private final int maxHealth;

	public PlayerCopy(EntityManager entityManager, Map map, Input input, Camera camera, String facing) {
		this(entityManager, map, input, camera, 0.0f, 0.0f, facing);
	}

	public PlayerCopy(EntityManager entityManager, Map map, Input input, Camera camera, float x, float y, String facing) {
		super(entityManager, x, y);

		this.map = map;
		this.input = input;
		this.camera = camera;
		entityMask.set(GameEntity.EntityProperty.FRIENDLY.getValue());

		this.pState = PlayerState.AIR;
		this.jumped = false;

		if (facing.compareTo("Left") == 0) {
			aState = AnimationState.STANDING_LEFT;
		} else {
			aState = AnimationState.STANDING_RIGHT;
		}
		this.right = new Animation(ResourceManager.getSpriteSheet("images/playerRight.png", 79, 90), 40);
		this.left = new Animation();
		for (int i = 0; i < this.right.getFrameCount(); i++) {
			this.left.addFrame(this.right.getImage(i).getFlippedCopy(true, false), 40);
		}
		right.setLooping(true);
		left.setLooping(true);

		if (aState == AnimationState.STANDING_LEFT) {
			this.addImageWithBoundingBox(ResourceManager.getImage("images/PlayerStandingLeft.png"));
		} else {
			this.addImageWithBoundingBox(ResourceManager.getImage("images/PlayerStandingRight.png"));
		}

		setVel(new Vector(0f, 0f)); // no initial movement
		this.maxVelSq = 16.0f;
		this.maxHealth = this.curHealth = 100;
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
		movement();
		bitCollision();
		gravity();
		camera();
	}

	private void gravity() {
		if (pState == PlayerState.AIR) {
			float gravity = 0.1f;
			vel = vel.add(new Vector(0.0f, gravity));
			setPosition(getPosition().add(vel));
		}
	}

	private void movement() {
		Vector p = getPosition();

		// Up and only up, no diagonals
		if (input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_LEFT)) {
			if (pState == PlayerState.NORMAL && jumped == false) {
				jumped = true;
				setPosition(p.getX(), p.getY() - 1.0f);
				vel = vel.add(new Vector(0.0f, -2.5f));
			}
		} // Down and only down, no diagonals
		else if (input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_RIGHT)
				&& !input.isKeyDown(Input.KEY_LEFT)) {
			setPosition(p.getX(), p.getY() + 1.0f);
		} // Left and only left, no diagonals
		else if (input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_UP)) {
			setPosition(p.getX() - 1.0f, p.getY());
			
			if (aState != AnimationState.MOVING_LEFT) {
				removeAnimation();
				this.addAnimation(left);
				aState = AnimationState.MOVING_LEFT;
			}
		} // Right and only right, no diagonals
		else if (input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN)) {
			setPosition(p.getX() + 1.0f, p.getY());
			
			if (aState != AnimationState.MOVING_RIGHT) {
				removeAnimation();
				this.addAnimation(right);
				aState = AnimationState.MOVING_RIGHT;
			}
		} // Up right
		else if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_RIGHT)) {

		} // Down right
		else if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_RIGHT)) {

		} // Down left
		else if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_LEFT)) {

		} // Up left
		else if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_LEFT)) {

		} // No commands
		else {
			if (aState == AnimationState.MOVING_LEFT) {
				removeAnimation();
				this.addImage(ResourceManager.getImage("images/PlayerStandingLeft.png"));
				aState = AnimationState.STANDING_LEFT;
			} else if (aState == AnimationState.MOVING_RIGHT) {
				removeAnimation();
				this.addImage(ResourceManager.getImage("images/PlayerStandingRight.png"));
				aState = AnimationState.STANDING_RIGHT;
			}
		}

		setPosition(getPosition().add(vel));
	}
	
	private void removeAnimation() {
		if (aState == AnimationState.MOVING_LEFT) {
			this.removeAnimation(left);
		} else if (aState == AnimationState.MOVING_RIGHT) {
			this.removeAnimation(right);
		} else if (aState == AnimationState.STANDING_LEFT) {
			this.removeImage(ResourceManager.getImage("images/PlayerStandingLeft.png"));
		} else if (aState == AnimationState.STANDING_RIGHT) {
			this.removeImage(ResourceManager.getImage("images/PlayerStandingRight.png"));
		}
			
		
	}

	/**
	 * Prevents entities from clipping the terrain.
	 */
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
			if (pState == PlayerState.AIR) {
				pState = PlayerState.NORMAL;
				jumped = false;
			}
		}

		// Test outer bottom
		if (!bfBitDetection((int) getCoarseGrainedMinX(), (int) getCoarseGrainedMaxX(), (int) getCoarseGrainedMaxY(),
				(int) getCoarseGrainedMaxY() + 1)) {
			if (pState == PlayerState.NORMAL) {
				pState = PlayerState.AIR;
			}
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
	
	private void camera() {
		float tlx = getPosition().getX() - camera.getCameraHalfWidth();
		float tly = getPosition().getY() - camera.getCameraHalfHeight();
		camera.setTlx(tlx);
		camera.setTly(tly);
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
