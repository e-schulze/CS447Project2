package edu.wsu.vancouver.ssdd.collision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wsu.vancouver.ssdd.EntityManager;
import edu.wsu.vancouver.ssdd.GameEntity;
import edu.wsu.vancouver.ssdd.Map;

public class CollisionQTree extends CollisionDetection {
	private Map map;
	private static final int MAXELEMENTNODE = 4;

	public CollisionQTree(EntityManager entityManager, Map map) {
		super(entityManager);
		this.map = map;
	}

	@Override
	public void detectCollision() {
		findCollision();
		handleCollision();
	}

	void findCollision() {
		QuadTreeNode root = new QuadTreeNode(map.getMapWidth() / 2, map.getMapHeight() / 2, MAXELEMENTNODE);
		List<GameEntity> gameEntityList = new ArrayList<GameEntity>(entityManager.getEntityMap().values());
		// Populate tree
		for (GameEntity ge : gameEntityList) {
			root.addElement(ge);
		}
		traverseTree(root);
	}

	private void traverseTree(QuadTreeNode node) {
		QuadTreeNode[] quadrant = new QuadTreeNode[4];
		if ((quadrant[0] = node.getQuadrant(0)) != null) {
			traverseTree(quadrant[0]);
		}
		if ((quadrant[1] = node.getQuadrant(1)) != null) {
			traverseTree(quadrant[1]);
		}
		if ((quadrant[2] = node.getQuadrant(2)) != null) {
			traverseTree(quadrant[2]);
		}
		if ((quadrant[3] = node.getQuadrant(3)) != null) {
			traverseTree(quadrant[3]);
		}
		processCollision(node.getGameEntityList());
	}
	
	private void processCollision(List<GameEntity> gameEntityList) {
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

	public class QuadTreeNode {
		private QuadTreeNode[] quadrant;

		private float xOrigin, yOrigin;

		private int elementCount;
		private final int elementMaxCount;
		private List<GameEntity> gameEntityList;

		/**
		 * This quadtree implementation uses an implicit boundary definition.
		 * Based on positive coordinates as the the center origin, and distance
		 * to the real origin (0, 0) is the boundary distance.
		 * 
		 */
		public QuadTreeNode(float xOrigin, float yOrigin, int elementMaxCount) {
			quadrant = new QuadTreeNode[4];
			quadrant[0] = null;
			quadrant[1] = null;
			quadrant[2] = null;
			quadrant[3] = null;

			this.xOrigin = xOrigin;
			this.yOrigin = yOrigin;

			this.elementMaxCount = elementMaxCount;
			this.elementCount = 0;

			this.gameEntityList = new ArrayList<GameEntity>(elementMaxCount + 1);
		}

		public void addElement(GameEntity ge) {
			// If atleast one quadrant has been initialized then all of the
			// others have been initialized. Prevent adding to inner nodes.
			if (quadrant[0] != null) {
				spanningAdd(ge);
			} // As a leaf node
			else {
				gameEntityList.add(ge);
				if (elementCount + 1 > elementMaxCount) {
					split();
					elementCount = 0;
				} else {
					elementCount++;
				}
			}
		}

		private void split() {
			// Quadrant I
			quadrant[0] = new QuadTreeNode(xOrigin + getXOriginHalf(), yOrigin - getYOriginHalf(), elementMaxCount);
			// Quadrant II
			quadrant[1] = new QuadTreeNode(xOrigin - getXOriginHalf(), yOrigin - getYOriginHalf(), elementMaxCount);
			// Quadrant III
			quadrant[2] = new QuadTreeNode(xOrigin - getXOriginHalf(), yOrigin + getYOriginHalf(), elementMaxCount);
			// Quadrant IV
			quadrant[3] = new QuadTreeNode(xOrigin + getXOriginHalf(), yOrigin + getYOriginHalf(), elementMaxCount);

			for (GameEntity ge : gameEntityList) {
				spanningAdd(ge);
			}
			gameEntityList.clear();
		}

		private void spanningAdd(GameEntity ge) {
			// Corners in ST/UW coordinates with same alignment with
			// quadrants. Top-right CCW
			if (ge.getCoarseGrainedMaxX() > xOrigin && ge.getCoarseGrainedMinY() < yOrigin) {
				quadrant[0].addElement(ge);
			}
			if (ge.getCoarseGrainedMinX() < xOrigin && ge.getCoarseGrainedMinY() < yOrigin) {
				quadrant[1].addElement(ge);
			}
			if (ge.getCoarseGrainedMinX() < xOrigin && ge.getCoarseGrainedMaxY() > yOrigin) {
				quadrant[2].addElement(ge);
			}
			if (ge.getCoarseGrainedMaxX() > xOrigin && ge.getCoarseGrainedMaxY() > yOrigin) {
				quadrant[3].addElement(ge);
			}
		}

		public float getXOrigin() {
			return xOrigin;
		}

		public float getYOrigin() {
			return yOrigin;
		}

		public float getXOriginHalf() {
			return xOrigin * 0.5f;
		}

		public float getYOriginHalf() {
			return yOrigin * 0.5f;
		}

		public int getElementCount() {
			return elementCount;
		}

		public List<GameEntity> getGameEntityList() {
			return gameEntityList;
		}

		public QuadTreeNode getQuadrant(int quadrantNum) {
			switch (quadrantNum) {
			case 1:
				return quadrant[0];
			case 2:
				return quadrant[1];
			case 3:
				return quadrant[2];
			case 4:
				return quadrant[3];
			default:
				return null;
			}
		}
	}
}
