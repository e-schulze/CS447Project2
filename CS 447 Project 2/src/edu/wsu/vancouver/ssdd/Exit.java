package edu.wsu.vancouver.ssdd;

import jig.ResourceManager;

public class Exit extends GameEntity{
	
	public Exit(EntityManager entityManager, Map map){
		this(entityManager, map, 0f, 0f);
	}
	public Exit(EntityManager entityManager, Map map, float x, float y){
		super(entityManager, x, y);
		entityMask.set(GameEntity.EntityProperty.DOOR.getValue());
		this.addImageWithBoundingBox(ResourceManager.getImage("images/door.png"));
	}

}
