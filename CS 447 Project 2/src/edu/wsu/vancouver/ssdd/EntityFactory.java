package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Input;

import edu.wsu.vancouver.ssdd.tests.EmptyEntity;
import edu.wsu.vancouver.ssdd.tests.TestEntity;

public class EntityFactory {
	public enum EntityType {
		EMPTY, TEST
	}

	private EntityManager entityManager;
	private Map map;
	private Input input;

	public EntityFactory(EntityManager entityManager, Map map, Input input) {
		this.entityManager = entityManager;
		this.map = map;
		this.input = input;
	}

	public Integer createEntity(EntityType entityType) {
		GameEntity entity = null;
		Integer entityId = null;

		switch (entityType) {
		case EMPTY:
			entity = new EmptyEntity(entityManager);
			break;
		case TEST:
			entity = new TestEntity(entityManager, map, input);
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
		case TEST:
			entity = new TestEntity(entityManager, map, input, cxp, cyp);
			break;
		default:
			break;
		}

		if (entity != null) {
			entityId = entity.getEntityId();
		}

		return entityId;
	}

	public void updateMap(Map map) {
		this.map = map;
	}
}
