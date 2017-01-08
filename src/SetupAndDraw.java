import java.util.ArrayList;
import java.util.Stack;

import controlP5.*;
import processing.core.*;

public class SetupAndDraw extends PApplet {

	Rackets population;
	
//	PVector target;
	Target target;
	
	int populationSize = 300;
	static int frame = 0;
	static int lifeTime = 250;
	public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	ControlP5 jControl;
	boolean isPaused = false;
	Stack<PVector> stack;
	PVector newObsloc;
	int generation=0;
	
	public static void main(String[] args) {
		PApplet.main("SetupAndDraw");

	}

	public void settings() {
		size(1280, 720);

	}

	public void setup() {
		jControl = new ControlP5(this);
		jControl.addSlider("populationSize", 0, populationSize, 300, 10, 10, 150, 30);
	//	jControl.addTextarea("frallme").setPosition(50, 50).setSize(100, 100).setStringValue("lol").setValue(123); //
		newObsloc = new PVector(-1, -1);

		target=new Target(width - 50, height / 2,50,this);
		
		frame = 0;
		background(0);
		population = new Rackets(this, populationSize);
	//	target = new PVector(width - 50, height / 2);
		// obstacles.add(new Obstacle(400, 0, 30, 400, this));
		// obstacles.add(new Obstacle(850, 350, 30, 420, this));

	}

	public void draw() {
		background(200, 200, 200);

		noFill();
		stroke(3);
		ellipse(100, height / 2, 40, 40);// starting point
		fill(50);
		target.show();

		int gotToTarget=0;
		for(int i=0;i<populationSize;i++){
			if(population.getRackets().get(i).getInTarget())
				gotToTarget++;
		}
		
		
		for (int i = 0; i < obstacles.size(); i++) {
			obstacles.get(i).show();
			
		}

		population.run(target, obstacles, populationSize);

		frame++;
		if (frame >= lifeTime) {
			generation++;

			population.evaluate(target, populationSize);
			population.selection(this, populationSize);
			
			System.out.println("Generation : "+generation);
			System.out.println("GotTotarget : "+gotToTarget);
			System.out.format("Percent : %.2f  %n",100*(float)gotToTarget/populationSize);
			System.out.println("Size of mating pool : "+population.getMatingPool().size());
			System.out.println();
			frame = 0;
		}

	}

	public static int getFrame() {
		return frame;
	}

	public static int getLifeTime() {
		return lifeTime;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void keyPressed() {
		if (key == 'p' || key == 'P') {
			if (isPaused) {
				loop();
				isPaused = false;
			} else {
				noLoop();
				isPaused = true;
			}
		}
		if(key=='z' && !obstacles.isEmpty()){
			obstacles.remove(obstacles.size()-1);
		}
	}

	public void mouseDragged() {
		System.out.println("Dragged");
		rect(newObsloc.x, newObsloc.y, mouseX - newObsloc.x, mouseY - newObsloc.y);
	}

	public void mousePressed() {
		newObsloc.set(mouseX, mouseY, 0);
		System.out.println("Pressed");
	}

	public void mouseReleased() {
		obstacles.add(new Obstacle(newObsloc.x, newObsloc.y, mouseX - newObsloc.x, mouseY - newObsloc.y, this));
		newObsloc = new PVector();
	}
}
