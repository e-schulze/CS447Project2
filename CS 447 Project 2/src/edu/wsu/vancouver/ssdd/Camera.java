package edu.wsu.vancouver.ssdd;

public class Camera {
	/** Camera top left x position */
	private float tlx;
	/** Camera top left y position */
	private float tly;
	/** Viewable width */
	private float cameraWidth;
	/** Viewable height */
	private float cameraHeight;
	
	public Camera(float tlx, float tly, float cameraWidth, float cameraHeight) {
		this.tlx = tlx;
		this.tly = tly;
		this.cameraWidth = cameraWidth;
		this.cameraHeight = cameraHeight;
	}
	
	public float getTlx() {
		return tlx;
	}
	
	public float getTly() {
		return tly;
	}
	
	public void setTlx(float tlx) {
		this.tlx = tlx;
	}
	
	public void setTly(float tly) {
		this.tly = tly;
	}
	
}
