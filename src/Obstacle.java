import processing.core.*;

public class Obstacle implements Displayable {

	private float x;
	private float h;
	private float y;
	private float w;
	PApplet p;
	PImage texture;

	public Obstacle(float x, float y, float oWidth, float oHeight, PApplet parent) {
		p = parent;

		if (oWidth < 0) {
			oWidth = Math.abs(oWidth);
			x = x - oWidth;
		}
		if (oHeight < 0) {
			oHeight = Math.abs(oHeight);
			y = y - oHeight;
		}

		this.x = x;
		this.w = oWidth;
		this.h = oHeight;
		this.y = y;
		texture = p.loadImage("obstacletexture2.jpg");
	}

	boolean ifHit(PVector position) {
		boolean hit = false;
		if (this.x < position.x && this.x + w > position.x && this.y < position.y && this.y + h > position.y)
			hit = true;
		return hit;
		// return this.x < position.x && this.x + w > position.x && this.y <
		// position.y && this.y + h > position.y
	}

	public void show() {
		// p.fill(115,65,7);
		// p.rect(x, y, w, h);
		if (w > 0 && h > 0) {
			texture.resize((int) Math.ceil(w), (int) Math.ceil(h));
			p.image(texture, x, y);
		}
	}

}
