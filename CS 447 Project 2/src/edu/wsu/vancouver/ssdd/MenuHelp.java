package edu.wsu.vancouver.ssdd;

import jig.ResourceManager;

public class MenuHelp extends GameEntity{
	public MenuHelp(EntityManager entityManager){
		super(entityManager);
	}
	
	public MenuHelp(EntityManager entityManager, float x, float y) {
		super(entityManager, x, y);
		
		addImageWithBoundingBox(ResourceManager.getImage("rsc/menuHelp.png"));
	}
	
}
