import processing.core.*;

public class Obstacle {

	private float x;
	private float h;
	private float y;
	private float w;
	PApplet p;

	public Obstacle(float x, float y, float oWidth, float oHeight, PApplet parent) {
		p = parent;
		
		if(oWidth<0){
			oWidth=Math.abs(oWidth);
			x=x-oWidth;	
		}
		if(oHeight<0){
			oHeight=Math.abs(oHeight);
			y=y-oHeight;
		}
		
		this.x = x;
		this.w = oWidth;
		this.h = oHeight;
		this.y = y;
	}

	boolean ifHit(PVector position) {
		boolean hit = false;
		if (this.x < position.x && this.x + w > position.x && this.y < position.y && this.y + h > position.y)
			hit = true;
		return hit;
		// return this.x < position.x && this.x + w > position.x && this.y <
		// position.y && this.y + h > position.y
	}

	void show() {
		p.fill(15);
		p.rect(x, y, w, h);
	}

}
