package edu.wsu.vancouver.ssdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Graphics;

public class EntityManager {
	private Map<Integer, GameEntity> entityMap;
	private Map<Integer, GameEntity> addPool;
	private ArrayList<Integer> deletePool;
	
	public EntityManager() {
		new EntityId();
		entityMap = new HashMap<Integer, GameEntity>();
		addPool = new HashMap<Integer, GameEntity>();
		deletePool = new ArrayList<Integer>();
	}
	
	public Map<Integer, GameEntity> getEntityMap() {
		return entityMap;
	}
	
	public Integer addEntity(Integer entityID, GameEntity gameEntity) {		
		addPool.put(entityID, gameEntity);
		return entityID;
	}
	
	public GameEntity retrieveEntity(Integer entityID) {
		if (entityMap.containsKey(entityID)) {
			return entityMap.get(entityID);
		}
		return null;
	}
	
	public void entityDeleteMark(Integer entityID) {
		deletePool.add(entityID);
	}
	
	public void entityCreateProcess() {
		entityMap.putAll(addPool);
		addPool.clear();
	}
	public void entityDeleteProcess() {
		for (Integer i : deletePool) {
			entityMap.remove(i);
		}
		deletePool.clear();
	}

	public void updateEntities(int delta) {
		for (Map.Entry<Integer, GameEntity> entry : entityMap.entrySet()) {
			entry.getValue().update(delta);
		}
	}
	
	public void renderEntities(Graphics g, Camera c) {
		for (Map.Entry<Integer, GameEntity> entry : entityMap.entrySet()) {
			entry.getValue().render(g, c);
		}
	}
	
	protected void clearAllObjects() {
		entityMap.clear();
		addPool.clear();
		deletePool.clear();
	}

}
