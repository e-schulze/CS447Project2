package edu.wsu.vancouver.ssdd.collision;

import edu.wsu.vancouver.ssdd.GameEntity;

public class CollisionTuple {
	private GameEntity gameEntity1;
	private GameEntity gameEntity2;
	private Integer entityId1;
	private Integer entityId2;
	
	public CollisionTuple(GameEntity geA, GameEntity geB) {
		if (geA.getEntityId() <= geB.getEntityId()) {
			this.gameEntity1 = geA;
			this.gameEntity2 = geB;
			this.entityId1 = geA.getEntityId();
			this.entityId2 = geB.getEntityId();
		} else {
			this.gameEntity1 = geB;
			this.gameEntity2 = geA;
			this.entityId1 = geB.getEntityId();
			this.entityId2 = geA.getEntityId();
		}
	}
	
	public GameEntity getLeft() {
		return gameEntity1;
	}
	
	public GameEntity getRight() {
		return gameEntity2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entityId1 == null) ? 0 : entityId1.hashCode());
		result = prime * result + ((entityId2 == null) ? 0 : entityId2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollisionTuple other = (CollisionTuple) obj;
		if (entityId1 == null) {
			if (other.entityId1 != null)
				return false;
		} else if (!entityId1.equals(other.entityId1))
			return false;
		if (entityId2 == null) {
			if (other.entityId2 != null)
				return false;
		} else if (!entityId2.equals(other.entityId2))
			return false;
		return true;
	}
}
