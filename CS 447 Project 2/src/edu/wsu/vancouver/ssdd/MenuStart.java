package edu.wsu.vancouver.ssdd;

import jig.ResourceManager;

public class MenuStart extends GameEntity {

	public MenuStart(EntityManager entityManager){
		super(entityManager);
	}
	
	public MenuStart(EntityManager entityManager, float x, float y) {
		super(entityManager, x, y);
		
		addImageWithBoundingBox(ResourceManager.getImage("rsc/menuStart.png"));
	}
	
}
