package edu.wsu.vancouver.ssdd.collision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wsu.vancouver.ssdd.EntityManager;
import edu.wsu.vancouver.ssdd.GameEntity;

public class CollisionBruteForce extends CollisionDetection {
	public CollisionBruteForce(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void detectCollision() {
		findCollision();
		handleCollision();
	}

	/**
	 * Brute force findCollision iterates through each entity in the entity list
	 * and checks if they collide.
	 */
	void findCollision() {
		List<GameEntity> gameEntityList = new ArrayList<GameEntity>(entityManager.getEntityMap().values());
		// Two loops, for each and iterator. Iterators faster than non indexing
		// get on ArrayLists.
		for (GameEntity geA : gameEntityList) {
			Iterator<GameEntity> it = gameEntityList.iterator();
			// Prevents duplicates in detecting collisions.
			while (it.next() != geA)
				;
			// Inner loop to compare two entities
			while (it.hasNext()) {
				GameEntity geB = it.next();
				// Detect collisions here
				if (validCollision(geA, geB) && geA.collides(geB) != null) {
					addCollision(geA, geB);
				}
			}
		}
	}
}
