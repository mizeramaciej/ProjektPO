import processing.core.PApplet;
import processing.core.PVector;

public class Portal implements Displayable {
	private PVector inPortal;
	private PVector outPortal;
	private int radious;
	private PApplet p;

	public Portal(PVector in, PVector out, PApplet parent) {
		this.p = parent;
		this.inPortal = in;
		this.outPortal = out;
		this.radious = 60;
	}

	public void teleport(Racket racket) {
		racket.setPosition(outPortal);
	}

	public void show() {
		p.fill(38, 116, 219);
		p.ellipse(inPortal.x, inPortal.y, radious, radious);
		p.fill(218, 130, 30);
		p.ellipse(outPortal.x, outPortal.y, (float) (radious * 0.8), (float) (radious * 0.8));
	}
	
	public PVector getInPortal() {
		return inPortal;
	}

	public void setInPortal(PVector inPortal) {
		this.inPortal = inPortal;
	}

	public PVector getOutPoratl() {
		return outPortal;
	}

	public void setOutPortal(PVector newloc) {
		this.outPortal = newloc;
	}

	public int getRadious() {
		return radious;
	}

	public void setRadious(int radious) {
		this.radious = radious;
	}
}
