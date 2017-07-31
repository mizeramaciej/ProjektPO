import java.util.ArrayList;
import java.util.Stack;

import controlP5.*;
import processing.core.*;

public class SetupAndDraw extends PApplet {

	private Adding adding;
	private PVector PortIN;
	private PVector PortOUT;

	private PImage background, obstacletexture;

	private Rackets population;
	private Target target;
	private StartingPoint start;
	private ArrayList<Portal> portals = new ArrayList<Portal>();
	private int populationSize;
	private static int frame;
	private static int lifeTime;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

	private float percent;
    Textlabel infoDisplay;
	Textlabel infoControls;
	boolean isPaused = false;
	Stack<PVector> stack;
	PVector newObsloc;
	int generation; // ktora generacja

	public static void main(String[] args) {
		PApplet.main("SetupAndDraw");
	}

	public void settings() {
		size(1280, 720);
	}

	public void setup() {

		adding = Adding.OBSTACLE;
		PortIN = null;
		PortOUT = null;

		populationSize = 300;
		lifeTime = 250;
		percent = 0;
		isPaused = false;
		generation = 0;
		frame = 0;
		population = new Rackets(this, populationSize);
		newObsloc = new PVector(-1, -1);
		target = new Target(width - 50, height / 2, 50, this);
		start = new StartingPoint(new PVector(100, height / 2), this);

		obstacletexture = loadImage("obstacletexture2.jpg");
		background = loadImage("background3.jpg");
		background.resize(1280, 720);

        ControlP5 jControl = new ControlP5(this);
		jControl.addSlider("populationSize", 0, populationSize, 300, 10, 10, 150, 30);

		infoDisplay = jControl.addTextlabel("General").setPosition(10, 50).setFont(createFont("Arial", 18));
		infoControls= jControl.addTextlabel("Controls").setPosition(1000,20).setFont(createFont("Arial",16));

		background(0);

	}

	public void draw() {
		background(background);

		showPortals();
		start.show();
		target.show();
		showObstacles();

		int gotToTarget = 0;
		for (int i = 0; i < populationSize; i++) {
			if (population.getRackets().get(i).getInTarget())
				gotToTarget++;
		}

		population.run(target, obstacles, populationSize, portals);

		frame++;
		if (frame >= lifeTime) {
			generation++;

			population.evaluate(target, populationSize);
			population.selection(this, populationSize);

			percent = Math.round(100 * (float) gotToTarget / populationSize);
			frame = 0;
		}

		showInfo(gotToTarget);


	}

	private void showInfo(int gotToTarget) {
		infoDisplay.setText("Generation : " + String.valueOf(generation) + "\nGotTotarget : "
				+ String.valueOf(gotToTarget) + "\nPercent : " + String.valueOf(percent) + "%\nSize of mating pool : "
				+ population.getMatingPool().size());
		infoDisplay.draw(this);

		infoControls.setText("'1' - dodaj przeszkode \n'z' - usun ostatnia przeszkode \n'2' - dodaj portal\n'x' - usun ostatni portal"
							+ "\n'r' - restart\n'p' - pauza");
		infoControls.draw(this);
	}

	private void showPortals() {
		if (adding == Adding.PORTAL) {
			if (PortIN == null) {
				fill(0, 0, 200);
				ellipse(mouseX, mouseY, 30, 30);
			} else {
				fill(170, 90, 0);
				ellipse(mouseX, mouseY, 30, 30);
			}

		}

		if (PortIN != null) {
			fill(0, 0, 200);
			ellipse(PortIN.x, PortIN.y, 30, 30);
		}

		for (int i = 0; i < portals.size(); i++) {
			portals.get(i).show();
		}

	}

	private void showObstacles() {
		for (int i = 0; i < obstacles.size(); i++) {
			obstacles.get(i).show();
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
		if (key == 'z' && !obstacles.isEmpty()) {
			obstacles.remove(obstacles.size() - 1);
		}

		if (key == 'x' && !portals.isEmpty()) {
			portals.remove(portals.size() - 1);
		}

		if (key == 'r') {
			infoDisplay.remove();
			setup();
		}

		if (key == '1') {
			adding = Adding.OBSTACLE;
		}
		if (key == '2') {
			adding = Adding.PORTAL;
		}
	}

	public void mouseDragged() {

		if (adding == Adding.OBSTACLE) {
			fill(115, 65, 7);
			rect(newObsloc.x, newObsloc.y, mouseX - newObsloc.x, mouseY - newObsloc.y);
		}
	}

	public void mousePressed() {
		if (adding == Adding.PORTAL) {
			if (PortIN == null) {
				PortIN = new PVector(mouseX, mouseY);
			} else {
				PortOUT = new PVector(mouseX, mouseY);
				addPortal(PortIN, PortOUT);
				PortIN = null;
				PortOUT = null;
			}
		}

		if (adding == Adding.OBSTACLE) {
			newObsloc.set(mouseX, mouseY, 0);
		}
	}

	public void mouseCliced() {
		if (adding == Adding.PORTAL) {
			if (PortIN == null) {
				PortIN = new PVector(mouseX, mouseY);
			} else {
				PortOUT = new PVector(mouseX, mouseY);
				addPortal(PortIN, PortOUT);
				PortIN = null;
				PortOUT = null;
			}
		}

		if (adding == Adding.OBSTACLE) {
			newObsloc.set(mouseX, mouseY, 0);
		}
	}

	public void mouseReleased() {
		if (adding == Adding.OBSTACLE) {
			if (Math.abs(newObsloc.x - mouseX) > 3 && Math.abs(newObsloc.y - mouseY) > 3) {
				obstacles.add(new Obstacle(newObsloc.x, newObsloc.y, mouseX - newObsloc.x, mouseY - newObsloc.y, this));
				newObsloc = new PVector();
			}
		}
	}

	public void addPortal(PVector in, PVector out) {
		portals.add(new Portal(in, out, this));
	}

}

// System.out.println("Generation : " + generation);
// System.out.println("GotTotarget : " + gotToTarget);
// System.out.printf("Percent : %.2f%% %n", 100 * (float)
// gotToTarget / populationSize);
// System.out.println("Size of mating pool : " +
// population.getMatingPool().size());
// System.out.println();
// System.out.println("---------------");
