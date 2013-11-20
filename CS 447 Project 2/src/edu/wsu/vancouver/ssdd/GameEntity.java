package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Graphics;

import jig.Entity;
import jig.Vector;

public abstract class GameEntity extends Entity {
	private Integer entityId;
	protected EntityManager entityManager;

	public GameEntity(EntityManager entityManager) {
		super();
		setEntityId(EntityId.generateID());
		entityManager.addEntity(getEntityId(), this);
		this.entityManager = entityManager;
	}

	public GameEntity(EntityManager entityManager, final float x, final float y) {
		super(x, y);
		setEntityId(EntityId.generateID());
		entityManager.addEntity(getEntityId(), this);
		this.entityManager = entityManager;
	}

	public GameEntity(EntityManager entityManager, final Vector position) {
		super(position);
		setEntityId(EntityId.generateID());
		entityManager.addEntity(getEntityId(), this);
		this.entityManager = entityManager;
	}

	public void update(int delta) {

	}
	
	public void render(Graphics g) {
		
	}

	public Integer getEntityId() {
		return this.entityId;
	}

	public void setEntityId(final Integer entityID) {
		// Set once.
		this.entityId = this.entityId == null ? entityID : this.entityId;
	}
}