package edu.wsu.vancouver.ssdd.collision;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.wsu.vancouver.ssdd.EntityManager;
import edu.wsu.vancouver.ssdd.GameEntity;
import edu.wsu.vancouver.ssdd.GameEntity.EntityProperty;

/**
 * Broad phase collision detection
 */
public abstract class CollisionDetection {
	protected EntityManager entityManager;
	protected Set<CollisionTuple> collisionSet;

	public CollisionDetection(EntityManager entityManager) {
		this.entityManager = entityManager;
		collisionSet = new HashSet<CollisionTuple>();
	}

	/**
	 * Empty stub - to be overridden. Different types of collision detection
	 * algorithms implements a different form of detectCollision.
	 */
	public void detectCollision() {
	}

	/**
	 * Given two entities, checks their types if their collisions are
	 * applicable.
	 */
	protected boolean validCollision(GameEntity geA, GameEntity geB) {
		boolean valid = false;

		BitSet bsA = geA.getEntityMask();
		BitSet bsB = geB.getEntityMask();

		BitSet player = new BitSet();
		player.set(EntityProperty.FRIENDLY.getValue());
		BitSet enemy = new BitSet();
		enemy.set(EntityProperty.ENEMY.getValue());
		BitSet bomb = new BitSet();
		bomb.set(EntityProperty.WEAPON.getValue());
		BitSet door = new BitSet();
		door.set(EntityProperty.DOOR.getValue());
		BitSet bullet = new BitSet();
		bullet.set(EntityProperty.BULLET.getValue());

		// Check if player is colliding with bomb
		if (ddCheck(bsA, bsB, player, bomb)) {
			valid = true;
		} // Check if player is colliding with enemy
		else if (ddCheck(bsA, bsB, player, enemy)) {
			valid = true;
		} // Check if enemy is colliding with bomb
		else if (ddCheck(bsA, bsB, enemy, bomb)) {
			valid = true;
		} // check if the player has reached the exit
		else if(ddCheck(bsA, bsB, player, door)){
			valid = true;
		}
		else if(ddCheck(bsA, bsB, enemy, bullet)){
			valid = true;
		}
		// Unhandled case
		else {
			valid = false;
		}

		return valid;
	}

	/**
	 * 'double dispatch' check on entity types. bsA and bsB belong to the
	 * gameEntity's bitmask. bsC and bsD are what the gameEntities are compared
	 * to.
	 */
	private boolean ddCheck(BitSet bsA, BitSet bsB, BitSet bsC, BitSet bsD) {
		return (bsA.equals(bsC) && bsB.equals(bsD)) || (bsA.equals(bsD) && bsB.equals(bsC));
	}

	protected void addCollision(GameEntity geA, GameEntity geB) {
		collisionSet.add(new CollisionTuple(geA, geB));
	}

	/**
	 * Goes through each valid detected collision (no duplicates) and calls the
	 * entities own collision handling method.
	 */
	protected void handleCollision() {
		Iterator<CollisionTuple> iterator = collisionSet.iterator();
		while (iterator.hasNext()) {
			CollisionTuple collisionTuple = iterator.next();
			GameEntity geL = collisionTuple.getLeft();
			GameEntity geR = collisionTuple.getRight();
			geL.collision(geR);
			geR.collision(geL);
		}
		collisionSet.clear();
	}
}
