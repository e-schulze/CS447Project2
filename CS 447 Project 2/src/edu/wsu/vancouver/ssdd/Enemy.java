package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Animation;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Enemy extends Entity{

	private static final int ZOMBIE = 0;
	private static final int ROBOT = 1;

	private Vector vector;
	private int type;
	private String dir;
	private Animation right;
	private Animation left;

	public Enemy(int x, int y, String dir, int type){
		super(x, y);
		this.type = type;
		this.dir = dir;
		switch(type){
		case ZOMBIE:
			this.addImageWithBoundingBox(ResourceManager.getImage("rsc/images/PlayerStandingLeft.png"));
			this.removeImage(ResourceManager.getImage("rsc/images/PlayerStandingLeft.png"));
			right = new Animation(ResourceManager.getSpriteSheet("rsc/images/ZombieRight.png", 53, 90), 1000);
			left = new Animation();
			for(int i = 0; i < right.getFrameCount(); i++){
				left.addFrame(right.getImage(i), 22);
			}
			if(dir.compareTo("Right") == 0){
				this.vector = new Vector(0.05f, 0f);
				this.addAnimation(right);
			}
			else{
				this.vector = new Vector(-0.05f, 0f);
				this.addAnimation(left);
			}
			break;
		case ROBOT:
			right = new Animation(ResourceManager.getSpriteSheet("rsc/images/RobotRight.png", 52, 90), 1000);
			left = new Animation();
			for(int i = 0; i < right.getFrameCount(); i++){
				left.addFrame(right.getImage(i), 143);
			}
			if(dir.compareTo("Right") == 0){
				this.vector = new Vector(0.05f, 0f);
				this.addAnimation(right);
			}
			else{
				this.vector = new Vector(-0.05f, 0f);
				this.addAnimation(left);
			}
			break;
		}
		right.setLooping(true);
		left.setLooping(true);
	}

	public Vector getVector() {
		return vector;
	}

	public void setVector(Vector vector) {
		this.vector = vector;
	}

	public void update(int delta){
		translate(vector.scale(delta));
		if((dir.compareTo("Right") == 0) && (vector.getX() < 0)){
			this.vector = new Vector(-0.05f, 0f);
			this.removeAnimation(right);
			this.addAnimation(left);
		}
		else if((dir.compareTo("Left") == 0) && (vector.getX() > 0)){
			this.vector = new Vector(0.05f, 0f);
			this.removeAnimation(left);
			this.addAnimation(right);
		}
	}

	private String getDir() {
		return dir;
	}

	private void setDir(String dir) {
		this.dir = dir;
	}

}
