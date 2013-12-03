package edu.wsu.vancouver.ssdd;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Bomb extends Entity{
	
	private final static int C4 = 0;
	private final static int GRENADE = 1;
	private final static int CHARGE = 2;
	
	private int blastRadius;
	private Vector vector;
	private int type;
	private int timer;
	private String dirBlast;
	
	public Bomb(int x, int y, int type, String dir){
		super(x, y);
		this.type = type;
		switch(type){
		case C4:
			this.addImageWithBoundingBox(ResourceManager.getImage("rsc/images.bomb1.png"));
			this.scale(0.5f);
			break;
		case GRENADE:
			this.addImageWithBoundingBox(ResourceManager.getImage("rsc/images.bomb1.png"));
			this.scale(0.5f);
			setTimer(5000);
			break;
		case CHARGE:
			this.addImageWithBoundingBox(ResourceManager.getImage("rsc/images.bomb1.png"));
			this.scale(0.5f);
			break;
		}
		if(dir.compareTo("Left") == 0){
			vector = new Vector(-0.075f, 0.05f);
		}
		else if(dir.compareTo("Right") == 0){
			vector = new Vector(0.075f, 0.05f);
		}
		else if(dir.compareTo("Down") == 0){
			vector = new Vector(0f, 0f);
		}
	}
	
	
	public int getBlastRadius() {
		return blastRadius;
	}
	public void setBlastRadius(int blastRadius) {
		this.blastRadius = blastRadius;
	}
	public Vector getVector() {
		return vector;
	}
	public void setVector(Vector vector) {
		this.vector = vector;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void update(int delta){
		translate(vector.scale(delta));
		if(timer > 0){
			timer -= delta;
		}
	}


	public int getTimer() {
		return timer;
	}


	public void setTimer(int timer) {
		this.timer = timer;
	}


	private String getDirBlast() {
		return dirBlast;
	}


	private void setDirBlast(String dirBlast) {
		this.dirBlast = dirBlast;
	}

}
