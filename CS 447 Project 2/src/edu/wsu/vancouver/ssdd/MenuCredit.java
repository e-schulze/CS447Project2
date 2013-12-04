package edu.wsu.vancouver.ssdd;

import jig.ResourceManager;

public class MenuCredit extends GameEntity {
	public MenuCredit(EntityManager entityManager){
		super(entityManager);
	}
	
	public MenuCredit(EntityManager entityManager, float x, float y) {
		super(entityManager, x, y);
		
		addImageWithBoundingBox(ResourceManager.getImage("rsc/menuCredit.png"));
	}
}
