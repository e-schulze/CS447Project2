package edu.wsu.vancouver.ssdd;

import java.util.BitSet;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Sound;

import jig.ResourceManager;

public class Explosion extends GameEntity {

	private Animation explosion;
	private Sound sound;
	
	private final float explosionRadius;
	private final int damage;
	
	private Map map;
	
	public Explosion(EntityManager entityManager, Map map, float x, float y){
		super(entityManager, x, y);
		entityMask.set(GameEntity.EntityProperty.WEAPON.getValue());
		this.map = map;
		
		this.addImageWithBoundingBox(ResourceManager.getImage("images/placeholder.png"));
		this.removeImage(ResourceManager.getImage("images/placeholder.png"));
		explosion = new Animation(ResourceManager.getSpriteSheet("images/explosionframes.png", 58, 58), 50);
		this.addAnimation(explosion);
		explosion.setLooping(false);
		
		explosionRadius = explosion.getImage(2).getWidth() * 0.5f;
		this.damage = 5;
	}
	
	public void update(int delta) {
		if (!isActive()) {
			entityManager.entityDeleteMark(getEntityId());
			circularDestruction(getPosition().getX(), getPosition().getY(), explosionRadius);
		}
	}
	
	@Override
	public void collision(GameEntity gameEntity) {
		BitSet enemy = new BitSet();
		enemy.set(EntityProperty.ENEMY.getValue());
		if (gameEntity.getEntityMask().equals(enemy)) {
			Enemy enemyEntity = (Enemy) gameEntity;
			enemyEntity.setHealth(enemyEntity.getHealth() - damage);
		}
	}
	
	boolean isActive(){
		return !explosion.isStopped();
	}
	
	void circularDestruction (float cx, float cy, float r) {
		int mapWidth = map.getMapWidth();
		int mapHeight = map.getMapHeight();
		boolean[][] destructibleMap = map.getDestructibleMap();
		
		for (int x = (int) (cx - r); x < (int) (cx + r); x++) {
			for (int y = (int) (cy - r); y < (int) (cy + r); y++) {
				if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
					continue;
				}
				// Checks if the distance square is in range.
				if (destructibleMap[x][y] == true && ((cx - x) * (cx - x) + (cy - y) * (cy - y) <= r * r)) {
					map.getBackbuffer().setRGBA(x, y, 0, 0, 0, 0);
				}
			}
		}
		map.setBackbufferChanged();
	}
}
