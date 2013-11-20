package edu.wsu.vancouver.ssdd.tests.bitmap;

import org.newdawn.slick.Graphics;

import edu.wsu.vancouver.ssdd.EntityManager;
import edu.wsu.vancouver.ssdd.GameEntity;

public class EmptyEntity extends GameEntity {

	public EmptyEntity(EntityManager entityManager) {
		super(entityManager);
	}
	
	public EmptyEntity(EntityManager entityManager, float cxp, float cyp) {
		super(entityManager, cxp, cyp);
	}
	
	@Override
	public void update(int delta) {
		
	}
	
	@Override
	public void render(Graphics g) {
		
	}
}
