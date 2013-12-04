package edu.wsu.vancouver.ssdd;

import jig.ResourceManager;

public class MenuLevel extends GameEntity {

	public MenuLevel(EntityManager entityManager){
		super(entityManager);
	}
	
	public MenuLevel(EntityManager entityManager, float x, float y) {
		super(entityManager, x, y);
		
		addImageWithBoundingBox(ResourceManager.getImage("rsc/menuLevel.png"));
	}
	
	
	
}
