import processing.core.PApplet;
import processing.core.PVector;

public class Target {
	private PVector position;
	private int radious;
	private PApplet p;

	public Target(float x, float y, int r, PApplet parent) {
		this.p = parent;
		this.setPosition(new PVector(x, y));
		this.radious = r;
	}

	public void show() {
		p.fill(250, 10, 10);
		p.ellipse(position.x, position.y, radious, radious); // target
		p.fill(255);
		p.ellipse(position.x, position.y, (float)(radious / 1.5), (float)(radious / 1.5)); // target
		p.fill(250, 10, 10);
		p.ellipse(position.x, position.y, radious / 3, radious / 3); // target
	}

	public int getRadious() {
		return radious;
	}

	public void setRadious(int radious) {
		this.radious = radious;
	}

	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position;
	}

}
