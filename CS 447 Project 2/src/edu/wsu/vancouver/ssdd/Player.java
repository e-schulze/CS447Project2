package edu.wsu.vancouver.ssdd;

import java.util.BitSet;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;

import edu.wsu.vancouver.ssdd.Bomb.BombType;
import edu.wsu.vancouver.ssdd.Bullet.BulletType;
import jig.ResourceManager;
import jig.Vector;

public class Player extends GameEntity {
	private enum AnimationState {
		STANDING_LEFT, MOVING_LEFT, STANDING_RIGHT, MOVING_RIGHT;
	}

	private AnimationState aState;

	private enum PlayerState {
		NORMAL, AIR, SLIDING_LEFT, SLIDING_RIGHT;
	}

	private PlayerState pState;

	private Map map;
	private Input input;
	private Camera camera;

	private Animation right;
	private Animation left;

	private boolean jumped;
	private int wallJumpTimer;

	private Vector vel;
	private final float maxVelSq;
	private int curHealth;
	private final int maxHealth;
	private int invulnerable;
	private BombType currentBomb;

	public Player(EntityManager entityManager, Map map, Input input, Camera camera, String facing) {
		this(entityManager, map, input, camera, 0.0f, 0.0f, facing);
	}

	public Player(EntityManager entityManager, Map map, Input input, Camera camera, float x, float y, String facing) {
		super(entityManager, x, y);

		this.map = map;
		this.input = input;
		this.camera = camera;
		entityMask.set(GameEntity.EntityProperty.FRIENDLY.getValue());

		invulnerable = 0;
		this.pState = PlayerState.AIR;
		this.jumped = false;
		this.wallJumpTimer = 0;
		currentBomb = BombType.C4;

		setVel(new Vector(0f, 0f)); // no initial movement
		this.maxVelSq = 16.0f;
		this.maxHealth = this.curHealth = 1000;

		if (facing.compareTo("Left") == 0) {
			aState = AnimationState.STANDING_LEFT;
		} else {
			aState = AnimationState.STANDING_RIGHT;
		}
		this.right = new Animation(ResourceManager.getSpriteSheet("images/playerRight.png", 51, 58), 40);
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
		wallJumpTimer(delta);
		gravity();
		health();
		camera();
		if(this.invulnerable > 0){
			this.invulnerable -= delta;
		}
	}

	@Override
	public void render(Graphics g, Camera c) {
		// Draw health bar
		g.drawRect(5.0f, 5.0f, 125.0f, 28.0f);
		if (curHealth > 0.0f) {
			Color temp = g.getColor();
			g.setColor(Color.green);
			g.fillRect(5.0f, 5.0f, (float) ((float)curHealth / (float)maxHealth) * 125.0f,
					28.0f);
			g.setColor(temp);
		}

		Vector worldPosition = getPosition();
		Vector cameraPosition = worldPosition.subtract(new Vector(c.getTlx(), c.getTly()));
		setPosition(cameraPosition);
		// Entity class render function
		render(g);
		// Reset position to world position
		setPosition(worldPosition);
	}

	@Override
	public void collision(GameEntity gameEntity) {
		BitSet door = new BitSet();
		door.set(EntityProperty.DOOR.getValue());
		BitSet enemy = new BitSet();
		enemy.set(EntityProperty.ENEMY.getValue());
		BitSet bomb = new BitSet();
		bomb.set(EntityProperty.WEAPON.getValue());
		if(gameEntity.getEntityMask().equals(door)){
			new Explosion(entityManager, map, gameEntity.getX(), gameEntity.getY());
			map.win = true;
		}
		else if(gameEntity.getEntityMask().equals(enemy) && this.invulnerable <= 0){
			this.curHealth -= 200;
			this.invulnerable = 1000;
			Sound s = ResourceManager.getSound("sounds/ouch.wav");
			s.play();
		}
		else if(gameEntity.getEntityMask().equals(bomb) && this.invulnerable <= 0){
			Sound s = ResourceManager.getSound("sounds/ouch.wav");
			s.play();
		}
	}

	private void gravity() {
		float gravity = 0.1f;
		if (pState == PlayerState.AIR) {
			vel = vel.add(new Vector(0.0f, gravity));
			setPosition(getPosition().add(vel));
		} else if (pState == PlayerState.SLIDING_LEFT || pState == PlayerState.SLIDING_RIGHT) {
			float slidingCoeff = 5.0f;
			Vector p = getPosition();
			setPosition(p.setY(p.getY() + gravity * slidingCoeff));
		}
	}

	private void health() {
		if (curHealth <= 0) {
			map.die = true;
			entityManager.entityDeleteMark(getEntityId());
		}
	}

	private void movement() {
		Vector p = getPosition();
		// Up
		if (input.isKeyDown(Input.KEY_UP)) {
			if ((pState == PlayerState.NORMAL || wallJumpTimer > 0) && jumped == false) {
				jumped = true;
				vel = vel.add(new Vector(0.0f, -2.5f));
			}
		}
		// Left
		if (input.isKeyDown(Input.KEY_LEFT)) {
			setPosition(p.getX() - 1.0f, p.getY());
			if (aState != AnimationState.MOVING_LEFT) {
				removeAnimation();
				aState = AnimationState.MOVING_LEFT;
				this.addAnimation(left);
			}
		} // Right
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			setPosition(p.getX() + 1.0f, p.getY());
			if (aState != AnimationState.MOVING_RIGHT) {
				removeAnimation();
				aState = AnimationState.MOVING_RIGHT;
				this.addAnimation(right);
			}
		}

		// No movement commands
		if (!input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_LEFT)
				&& !input.isKeyDown(Input.KEY_RIGHT)) {
			if (aState == AnimationState.MOVING_LEFT) {
				removeAnimation();
				aState = AnimationState.STANDING_LEFT;
				this.addImage(ResourceManager.getImage("images/PlayerStandingLeft.png"));

			} else if (aState == AnimationState.MOVING_RIGHT) {
				removeAnimation();
				aState = AnimationState.STANDING_RIGHT;
				this.addImage(ResourceManager.getImage("images/PlayerStandingRight.png"));
			}
		}

		setPosition(getPosition().add(vel));

		if (input.isKeyPressed(Input.KEY_Z)) {
			new Bomb(entityManager, map, getPosition().getX(), getPosition().getY(), currentBomb, "Left");
		}
		if(input.isKeyPressed(Input.KEY_S)){
			if(currentBomb.compareTo(BombType.C4) == 0){
				currentBomb = BombType.GRENADE;
			}
			else if(currentBomb.compareTo(BombType.GRENADE) == 0){
				currentBomb = BombType.CHARGE;
			}
			else if(currentBomb.compareTo(BombType.CHARGE) == 0){
				currentBomb = BombType.C4;
			}
		}
		if (input.isKeyPressed(Input.KEY_X)) {
			Sound s = ResourceManager.getSound("sounds/gunshot.wav");
			s.play();
			if (aState == AnimationState.STANDING_LEFT || aState == AnimationState.MOVING_LEFT) {
				new Bullet(entityManager, map, getPosition().getX(), getPosition().getY(), BulletType.GUN_BULLET, "Left");
			} else if (aState == AnimationState.STANDING_RIGHT || aState == AnimationState.MOVING_RIGHT){
				new Bullet(entityManager, map, getPosition().getX(), getPosition().getY(), BulletType.GUN_BULLET, "Right");
			}

		}
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
			if (pState == PlayerState.AIR || pState == PlayerState.SLIDING_LEFT || pState == PlayerState.SLIDING_RIGHT) {
				pState = PlayerState.NORMAL;
			}
			jumped = false;
		}

		// Test outer bottom
		if (!bfBitDetection((int) getCoarseGrainedMinX(), (int) getCoarseGrainedMaxX(), (int) getCoarseGrainedMaxY(),
				(int) getCoarseGrainedMaxY() + 1)) {
			// Test outer left
			if (bfBitDetection((int) getCoarseGrainedMinX() - 1, (int) getCoarseGrainedMinX(),
					(int) getCoarseGrainedMinY(), (int) getCoarseGrainedMaxY()) && input.isKeyDown(Input.KEY_LEFT)) {
				if (pState != PlayerState.SLIDING_LEFT) {
					pState = PlayerState.SLIDING_LEFT;
					jumped = false;
					wallJumpTimer = 250;
				}
				vel = new Vector(0.0f, 0.0f);
			}
			// Test outer right
			else if (bfBitDetection((int) getCoarseGrainedMaxX(), (int) getCoarseGrainedMaxX() + 1,
					(int) getCoarseGrainedMinY(), (int) getCoarseGrainedMaxY()) && input.isKeyDown(Input.KEY_RIGHT)) {
				if (pState != PlayerState.SLIDING_RIGHT) {
					pState = PlayerState.SLIDING_RIGHT;
					jumped = false;
					wallJumpTimer = 250;
				}
				vel = new Vector(0.0f, 0.0f);
			} else {
				if (jumped) {
					jumped = true;
				}
				pState = PlayerState.AIR;
			}
		}
	}

	private void wallJumpTimer(int delta) {
		if (wallJumpTimer > 0 && (pState != PlayerState.SLIDING_LEFT && pState != PlayerState.SLIDING_RIGHT)) {
			wallJumpTimer -= delta;

		} else if (wallJumpTimer < 0){
			wallJumpTimer = 0;
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
