package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Input;

import edu.wsu.vancouver.ssdd.Enemy.EnemyType;
import edu.wsu.vancouver.ssdd.tests.EmptyEntity;
import edu.wsu.vancouver.ssdd.tests.TestEntity;

public class EntityFactory {
	public enum EntityType {
		EMPTY, TEST, PLAYER_COPY, ENEMY
	}

	private EntityManager entityManager;
	private Map map;
	private Input input;
	private Camera camera;

	public EntityFactory(EntityManager entityManager, Input input) {
		this.entityManager = entityManager;
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
		case PLAYER_COPY:
			entity = new PlayerCopy(entityManager, map, input, camera, "Left");
			break;
		case ENEMY:
			entity = new Enemy(entityManager, map, "Left", EnemyType.ZOMBIE);
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
		case PLAYER_COPY:
			entity = new PlayerCopy(entityManager, map, input, camera, cxp, cyp, "Left");
			break;
		case ENEMY:
			entity = new Enemy(entityManager, map, cxp, cyp, "Left", EnemyType.ZOMBIE);
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
	
	public void updateCamera(Camera camera) {
		this.camera = camera;
	}
}
