package edu.wsu.vancouver.ssdd;

import jig.Entity;
import jig.Vector;

public class Player extends Entity{

	private int health;
	private Vector vector;
	
	public Player(int x, int y, String dir){
		super(x, y);
		if(dir.compareTo("Left") == 0){
			//this.addImageWithBoundingBox(image); need to add the image of the player facing left
		}
		else if(dir.compareTo("Right") == 0){
			//this.addImageWithBoundingBox(image); need to add the image of the player facing right
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
		if(vector.getX() < 0){ // change the player's image to match the direction of movement (left)
//			this.removeImage(image);
//			this.addImageWithBoundingBox(image);
		}
		else if(vector.getX() < 0){ // change the player's image to match the direction of movement (left)
//			this.removeImage(image);
//			this.addImageWithBoundingBox(image);
		}
	}
}
