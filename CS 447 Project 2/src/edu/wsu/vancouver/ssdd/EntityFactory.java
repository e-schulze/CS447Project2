package edu.wsu.vancouver.ssdd;

import edu.wsu.vancouver.ssdd.tests.EmptyEntity;

public class EntityFactory {
	public enum EntityType {
		EMPTY
	}
	
	private EntityManager entityManager;

	public EntityFactory(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Integer createEntity(EntityType entityType) {
		GameEntity entity = null;
		Integer entityId = null;
		
		switch (entityType) {
			case EMPTY:
				entity = new EmptyEntity(entityManager);
				break;
			default:
				break;
		}
		
		if (entity != null) {
			entityId = entity.getEntityId();
		}
		
		return entityId;
	}

	public Integer createEntity(EntityType entityType, float cxp, float cyp) {
		GameEntity entity = null;
		Integer entityId = null;
		switch (entityType) {
			case EMPTY:
				entity = new EmptyEntity(entityManager, cxp, cyp);
				break;
			default:
				break;
		}
		
		if (entity != null) {
			entityId = entity.getEntityId();
		}
		
		return entityId;
	}
}
