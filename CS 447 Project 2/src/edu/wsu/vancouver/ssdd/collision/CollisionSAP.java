package edu.wsu.vancouver.ssdd.collision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.wsu.vancouver.ssdd.EntityManager;
import edu.wsu.vancouver.ssdd.GameEntity;
import edu.wsu.vancouver.ssdd.GameEntityComparator;

public class CollisionSAP extends CollisionDetection {
	public CollisionSAP(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void detectCollision() {
		findCollision();
		handleCollision();
	}

	/**
	 * SAP findCollision sorts the entities in the list by their minimum coarse
	 * grained x boundary. Then iterates through each element by placing them
	 * into a list. For each insertion into the list, compare each element in
	 * the list with each other. At each insertion in the list check if any of
	 * the elements in the list maximum coarse grained x boundary is less than
	 * the newly added element. If it is remove it, else leave it be.
	 */
	void findCollision() {
		List<GameEntity> gameEntityList = new ArrayList<GameEntity>(entityManager.getEntityMap().values());
		Collections.sort(gameEntityList, new GameEntityComparator());
		List<GameEntity> sweepList = new ArrayList<GameEntity>();
		for (GameEntity ge : gameEntityList) {
			prune(sweepList, ge);
			sweepList.add(ge);
			// Two loops, for each and iterator. Iterators faster than non indexing
			// get on ArrayLists.
			for (GameEntity geA : sweepList) {
				Iterator<GameEntity> it = sweepList.iterator();
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
	
	private void prune(List<GameEntity> sweepList, GameEntity ge) {
		Iterator<GameEntity> it = sweepList.iterator();
		while (it.hasNext()) {
			GameEntity geSweep = it.next();
			if (geSweep.getCoarseGrainedMaxX() < ge.getCoarseGrainedMinX()) {
				it.remove();
			}
		}
	}
	
	private void printSweepList(List<GameEntity> sweepList) {
		for (GameEntity ge : sweepList) {
			System.out.println(ge);
		}
	}
}
