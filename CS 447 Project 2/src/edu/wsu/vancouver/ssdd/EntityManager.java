package edu.wsu.vancouver.ssdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Graphics;

public class EntityManager {
	Map<Integer, GameEntity> entityMap;
	ArrayList<Integer> deletePool;
	
	public EntityManager() {
		new EntityId();
		entityMap = new HashMap<Integer, GameEntity>();
		deletePool = new ArrayList<Integer>();
	}
	
	Integer addEntity(Integer entityID, GameEntity gameEntity) {		
		entityMap.put(entityID, gameEntity);
		return entityID;
	}
	
	GameEntity retrieveEntity(Integer entityID) {
		if (entityMap.containsKey(entityID)) {
			return entityMap.get(entityID);
		}
		return null;
	}
	
	void entityDeleteMark(Integer entityID) {
		deletePool.add(entityID);
	}
	
	void entityDeleteProcess() {
		for (Integer i : deletePool) {
			entityMap.remove(i);
		}
		deletePool.clear();
	}

	void updateEntities(int delta) {
		for (Map.Entry<Integer, GameEntity> entry : entityMap.entrySet()) {
			entry.getValue().update(delta);
		}
	}
	
	void renderEntities(Graphics g) {
		for (Map.Entry<Integer, GameEntity> entry : entityMap.entrySet()) {
			entry.getValue().render(g);
		}
	}
	
	void clearAllObjects() {
		deletePool.clear();
		entityMap.clear();
	}

}
