import processing.core.PApplet;
import processing.core.PVector;

public class StartingPoint implements Displayable {
	private PVector position;
	PApplet p;
	
	public StartingPoint(PVector pos,PApplet parent){
		p=parent;
		position=pos;
	}
	
	public void show(){
		p.fill(0, 250, 0);
		p.ellipse(position.x,position.y, 40, 40);// starting point
	}

	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position;
	}
}
