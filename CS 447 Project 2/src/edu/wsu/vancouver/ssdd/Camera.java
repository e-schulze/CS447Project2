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
	/** Current map */
	private Map map;

	public Camera(float tlx, float tly, float cameraWidth, float cameraHeight, Map map) {
		this.tlx = tlx;
		this.tly = tly;
		this.cameraWidth = cameraWidth;
		this.cameraHeight = cameraHeight;

		this.map = map;
	}

	public float getTlx() {
		return tlx;
	}

	public float getTly() {
		return tly;
	}

	public void setTlx(float tlx) {
		if (tlx > 0.0f && tlx + cameraWidth < (float) map.getMapWidth()) {
			this.tlx = tlx;
		}
	}

	public void setTly(float tly) {
		if (tly > 0.0f && tly + cameraHeight < (float) map.getMapHeight()) {
			this.tly = tly;
		}
	}

	public void changeTlx(float dTlx) {
		float temp = tlx + dTlx;
		if (temp > 0.0f && temp + cameraWidth < (float) map.getMapWidth()) {
			tlx = temp;
		}
	}

	public void changeTly(float dTly) {
		float temp = tly + dTly;
		if (temp > 0.0f && temp + cameraHeight < (float) map.getMapHeight()) {
			tly = temp;
		}
	}
}
