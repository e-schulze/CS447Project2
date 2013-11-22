package edu.wsu.vancouver.ssdd;

import jig.Entity;
import jig.Vector;

public class Bomb extends Entity{
	
	private int blastRadius;
	private Vector vector;
	private int type;
	
	public Bomb(int x, int y, int type, String dir){
		super(x, y);
		
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

}
