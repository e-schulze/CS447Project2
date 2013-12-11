package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Input;

import edu.wsu.vancouver.ssdd.Enemy.EnemyType;
import edu.wsu.vancouver.ssdd.tests.EmptyEntity;
import edu.wsu.vancouver.ssdd.tests.TestEntity;

public class EntityFactory {
	public enum EntityType {
		EMPTY, TEST, PLAYER, ROBOT, ZOMBIE;
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
		case PLAYER:
			entity = new Player(entityManager, map, input, camera, "Left");
			break;
		case ZOMBIE:
			entity = new Enemy(entityManager, map, "Left", EnemyType.ZOMBIE);
			break;
		case ROBOT:
			entity = new Enemy(entityManager, map, "Left", EnemyType.ROBOT);
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
		case PLAYER:
			entity = new Player(entityManager, map, input, camera, cxp, cyp, "Left");
			break;
		case ZOMBIE:
			entity = new Enemy(entityManager, map, cxp, cyp, "Left", EnemyType.ZOMBIE);
			break;
		case ROBOT:
			entity = new Enemy(entityManager, map, cxp, cyp, "Left", EnemyType.ROBOT);
			break;
		default:
			break;
		}

		if (entity != null) {
			entityId = entity.getEntityId();
		}

		return entityId;
	}
	
	public Integer createEntity(EntityType entityType, float cxp, float cyp, String direction) {
		GameEntity entity = null;
		Integer entityId = null;
		switch (entityType) {
		case EMPTY:
			entity = new EmptyEntity(entityManager, cxp, cyp);
			break;
		case TEST:
			entity = new TestEntity(entityManager, map, input, cxp, cyp);
			break;
		case PLAYER:
			entity = new Player(entityManager, map, input, camera, cxp, cyp, direction);
			break;
		case ZOMBIE:
			entity = new Enemy(entityManager, map, cxp, cyp, direction, EnemyType.ZOMBIE);
			break;
		case ROBOT:
			entity = new Enemy(entityManager, map, cxp, cyp, direction, EnemyType.ROBOT);
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
