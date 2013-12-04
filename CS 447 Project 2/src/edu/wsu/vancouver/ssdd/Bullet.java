package edu.wsu.vancouver.ssdd;

import jig.Entity;
import jig.Vector;

public class Bullet extends Entity{

	private Vector vector;
	
	public Bullet(int x, int y, String dir){
		super(x, y);
		if(dir.compareTo("Right") == 0){
			vector = new Vector(0.15f, 0f);
		}
		else{
			vector = new Vector(-0.15f, 0f);
		}
	}
	
	public void update(int delta){
		translate(vector.scale(delta));
	}
	
}
