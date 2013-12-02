package edu.wsu.vancouver.ssdd;

import org.newdawn.slick.Animation;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Player extends Entity{

	private int health;
	private Vector vector;
	private String dir;
	private Animation right;
	private Animation left;

	public Player(int x, int y, String dir){
		super(x, y);
		this.dir = dir;
		this.right = new Animation(ResourceManager.getSpriteSheet("rsc/images/playerRight.png", 79, 90), 1000);
		this.left = new Animation();
		for(int i = 0; i < this.right.getFrameCount(); i++){
			this.left.addFrame(this.right.getImage(i), 31);
		}
		if(dir.compareTo("Left") == 0){
			this.addImageWithBoundingBox(ResourceManager.getImage("rsc/images/PlayerStandingLeft.png"));
		}
		else if(dir.compareTo("Right") == 0){
			this.addImageWithBoundingBox(ResourceManager.getImage("rsc/images/PlayerStandingRight.png"));
		}
		setHealth(5); // might need to change
		setVector(new Vector(0f, 0f)); // no initial movement
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Vector getVector() {
		return vector;
	}

	public void setVector(Vector vector) {
		this.vector = vector;
	}

	public void decHealth(){
		this.health--;
	}
	public void incHealth(){
		this.health++;
	}

	public void update(int delta){
		translate(vector.scale(delta));
		if(vector.getY() == 0){
			if(vector.getX() > 0){ // change the player's image to match the direction of movement (left)
				if(dir.compareTo("Right") == 0){
					this.removeImage(ResourceManager.getImage("rsc/images/PlayerStandingRight.png"));
				}
				else{
					this.removeImage(ResourceManager.getImage("rsc/images/PlayerStandingLeft.png"));
				}
				this.addAnimation(right);
			}
			else if(vector.getX() < 0){ // change the player's image to match the direction of movement (left)
				if(dir.compareTo("Right") == 0){
					this.removeImage(ResourceManager.getImage("rsc/images/PlayerStandingRight.png"));
				}
				else{
					this.removeImage(ResourceManager.getImage("rsc/images/PlayerStandingLeft.png"));
				}
				this.addAnimation(left);
			}
			else if(vector.getX() == 0){
				if(dir.compareTo("Right") == 0){
					this.removeAnimation(right);
					this.addImage(ResourceManager.getImage("rsc/images/PlayerStandingRight.png"));
				}
				else{
					this.removeAnimation(left);
					this.addImage(ResourceManager.getImage("rsc/images/PlayerStandingLeft.png"));
				}
			}
		}
	}
}
