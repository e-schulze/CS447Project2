package edu.wsu.vancouver.ssdd.collision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wsu.vancouver.ssdd.EntityManager;
import edu.wsu.vancouver.ssdd.GameEntity;
import edu.wsu.vancouver.ssdd.Map;

public class CollisionGrid extends CollisionDetection {
	/** Zero indexed */
	private int xGrids, yGrids;
	/** Size of each grid cell (square) */
	private int gridSize;
	/** Spatial Index Grid */
	private List<List<List<GameEntity>>> sig;

	public CollisionGrid(EntityManager entityManager, Map map, int gridSize) {
		super(entityManager);
		if (gridSize == 0) {
			this.gridSize = 50;
		} else {
			this.gridSize = gridSize;
		}
		initSPI(map);
	}

	/**
	 * Initializes a triple nested list for the spatial index grid. The list is
	 * in row-col order.
	 */
	private void initSPI(Map map) {
		this.xGrids = (int) Math.ceil((double) map.getMapWidth() / gridSize);
		this.yGrids = (int) Math.ceil((double) map.getMapHeight() / gridSize);

		// Create empty rows
		this.sig = new ArrayList<List<List<GameEntity>>>(yGrids);
		for (int y = 0; y < yGrids; y++) {
			// Initialize rows, create empty columns
			sig.add(y, new ArrayList<List<GameEntity>>(xGrids));
			for (int x = 0; x < xGrids; x++) {
				// Initialize columns, create empty elements
				sig.get(y).add(new ArrayList<GameEntity>());
			}
		}
	}

	@Override
	public void detectCollision() {
		findSpanningSIG();
		findCollision();
		handleCollision();
		clearSIG();
	}

	/**
	 * Add the entity to the SIG, if the entity is larger than a grid cell size
	 * or is in-between borders multiple references to the entity will be made
	 * in separate grids.
	 */
	void findSpanningSIG() {
		List<GameEntity> geList = new ArrayList<GameEntity>(entityManager.getEntityMap().values());
		for (GameEntity ge : geList) {
			for (int y = (int) ge.getCoarseGrainedMinY(); y <= (int) (ge.getCoarseGrainedMaxY() + gridSize); y = y
					+ gridSize) {
				for (int x = (int) ge.getCoarseGrainedMinX(); x <= (int) (ge.getCoarseGrainedMaxX() + gridSize); x = x
						+ gridSize) {
					int row = y / gridSize;
					int col = x / gridSize;
					if (row < yGrids && col < xGrids) {
						addElementSIG(row, col, ge);
					}
				}
			}
		}
	}

	void addElementSIG(int row, int col, GameEntity ge) {
		sig.get(row).get(col).add(ge);
	}

	void removeElementSIG(int row, int col, GameEntity ge) {
		sig.get(row).get(col).remove(ge);
	}

	/**
	 * Grid findCollision like brute force iterates through each entity in the
	 * entity list and checks if they collide except checks entities within the
	 * same spatial grid.
	 */
	void findCollision() {
		List<GameEntity> geList;
		for (int y = 0; y < yGrids; y++) {
			for (int x = 0; x < xGrids; x++) {
				// --Same as brute force--
				geList = sig.get(y).get(x);
				for (GameEntity geA : geList) {
					Iterator<GameEntity> it = geList.iterator();
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
				// --End same--
			}
		}
	}

	void clearSIG() {
		for (int y = 0; y < yGrids; y++) {
			for (int x = 0; x < xGrids; x++) {
				sig.get(y).get(x).clear();
			}
		}
	}
}
