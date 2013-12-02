package edu.wsu.vancouver.ssdd;

import java.util.BitSet;

import org.newdawn.slick.Graphics;

import jig.Entity;
import jig.Vector;

public abstract class GameEntity extends Entity {
	public enum EntityProperty {
		NONE(0),
		GUI(1),
		FRIENDLY(2),
		ENEMY(3),
		WEAPON(4);
		
		private int value;
		
		private EntityProperty(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		public int getValueAsBitmask() {
			return 1 << value; 
		}
	}
	
	private Integer entityId;
	
	protected BitSet entityMask;
	protected EntityManager entityManager;

	public GameEntity(EntityManager entityManager) {
		super();
		setEntityId(EntityId.generateID());
		entityManager.addEntity(getEntityId(), this);
		this.entityMask = new BitSet();
		this.entityManager = entityManager;
	}

	public GameEntity(EntityManager entityManager, final float x, final float y) {
		super(x, y);
		setEntityId(EntityId.generateID());
		entityManager.addEntity(getEntityId(), this);
		this.entityMask = new BitSet();
		this.entityManager = entityManager;
	}

	public GameEntity(EntityManager entityManager, final Vector position) {
		super(position);
		setEntityId(EntityId.generateID());
		entityManager.addEntity(getEntityId(), this);
		this.entityMask = new BitSet();
		this.entityManager = entityManager;
	}

	public void update(int delta) {

	}
	
	public void render(Graphics g) {
		
	}
	
	public void collision(GameEntity gameEntity) {
		
	}
	
	public BitSet getEntityMask() {
		return entityMask;
	}

	public Integer getEntityId() {
		return this.entityId;
	}

	public void setEntityId(final Integer entityID) {
		// Set once.
		this.entityId = this.entityId == null ? entityID : this.entityId;
	}
}