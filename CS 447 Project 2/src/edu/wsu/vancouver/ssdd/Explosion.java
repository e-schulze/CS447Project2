package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Sound;

import jig.Entity;
import jig.ResourceManager;

public class Explosion extends Entity{

	private Animation explosion;
	private Sound sound;
	
	public Explosion(int x, int y){
		super(x, y);
		this.addImageWithBoundingBox(ResourceManager.getImage("rsc.images/placeholder.png"));
		this.removeImage(ResourceManager.getImage("rsc.images/placeholder.png"));
		explosion = new Animation(ResourceManager.getSpriteSheet("rsc/images/explosionframes/png", 96, 96), 1000);
		this.addAnimation(explosion);
		explosion.setLooping(false);
	}
	
	public boolean isActive(){
		return !explosion.isStopped();
	}
}
