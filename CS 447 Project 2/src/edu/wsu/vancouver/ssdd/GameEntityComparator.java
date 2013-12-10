package edu.wsu.vancouver.ssdd;

import java.util.Comparator;

public class GameEntityComparator implements Comparator<GameEntity> {
	@Override
	public int compare(GameEntity ge0, GameEntity ge1) {
		// Compares coarse grained boundaries
		if (ge0.getCoarseGrainedMinX() == ge1.getCoarseGrainedMinX()) {
			return 0;
		}
		else {
			return ge0.getCoarseGrainedMinX() < ge1.getCoarseGrainedMinX() ? -1 : 1;
		}
	}
}
