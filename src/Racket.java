import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Racket {
	private PVector pozycja;
	private PVector predkosc = new PVector();
	private PVector przyspieszenie = new PVector();
	private ArrayList<PVector> DNA = new ArrayList<PVector>();
	private double fitness = 0;
	private boolean getToTarget = false;
	private int timeToTarget = 0;
	private boolean crushed = false;

	final private float scl;

	private PApplet p;

	public Racket(PApplet parent) {
		p = parent;
		pozycja = new PVector(100, p.height / 2);
		scl = (float) Math.pow(p.width - 150, 3);
	}

	void calcFitness(PVector target) {
		// float d = (float) Math.sqrt(Math.pow(pozycja.x - target.x, 2) +
		// Math.pow(pozycja.y - target.y, 2));
		// fitness += PApplet.map(d, 0, p.width - 150, 80, 0); // moze jakos nie
		// // liniowo?
		float d = (float) Math.sqrt(Math.pow(pozycja.x - target.x, 2) + Math.pow(pozycja.y - target.y, 2));
		d = (float) Math.pow(d, 3);
		fitness += PApplet.map(d, 0, scl, 80, 0);

		if (getToTarget) {
			fitness += PApplet.map(timeToTarget, 0, SetupAndDraw.getLifeTime(), 20, 0); // (10,0)
			fitness *= 10; // 2
		}

		if (crushed) {
			fitness /= 3;
		}

		if (fitness < 1)
			fitness = 1;

	}

	Racket crossover(Racket other, PApplet parent) {
		Racket child = new Racket(parent);
		// int midpoint=(int)floor(random(0,lifetime));
		double chance = 0.5;

		if (this.getToTarget == true)// jesli dotarl do celu to mu zwiekszam
										// szance na przekaznie dna
			chance += 0.3;//0.2

		if (other.getToTarget == true)
			chance += -0.3;//0.2

		for (int i = 0; i < SetupAndDraw.getLifeTime(); i++) {
			if (p.random(0, 1) < chance) {
				child.DNA.add(this.DNA.get(i));
			} else
				child.DNA.add(other.DNA.get(i));
		}

		return child;
	}

	void mutation() {
		for (int i = 0; i < SetupAndDraw.getLifeTime(); i++) {
			if (p.random(0, 1) < 0.02) {
				this.DNA.set(i, new PVector(p.random(-1, 1), p.random(-1, 1)));
				DNA.get(i).mult((float) 0.5); // szybkosc rakiet //dodane// 0.4

			}
		}
	}

	void createDNA() {
		for (int i = 0; i < SetupAndDraw.getLifeTime(); i++) { // lifetime
			DNA.add(new PVector(p.random(-1, 1), p.random(-1, 1)));
			DNA.get(i).mult((float) 0.5); // szybkosc rakiet//0.4
		}
	}

	public void pchnij(PVector sila) {
		this.przyspieszenie.add(sila);
	}

	void update(Target target, ArrayList<Obstacle> obstacles) {

		if (!this.getToTarget && !this.crushed) {

			// czy dotarly do celu
			if (Math.sqrt(Math.pow(pozycja.x - target.getPosition().x, 2) + Math.pow(pozycja.y - target.getPosition().y, 2)) < target.getRadious()/2) {  //
				this.getToTarget = true;
				if (timeToTarget == 0)
					timeToTarget = SetupAndDraw.getFrame();
			}

			// czy zderzyly sie z krawedzia // width +50 moga troche za
			// przeleciec
			if (this.pozycja.x < 0 || this.pozycja.x > p.width + 50 || this.pozycja.y < 0
					|| this.pozycja.y > p.height) {
				this.crushed = true;
			}

			// petla po osbtacle, czy zderzyly sie ze przeszkoda?
			for (int i = 0; i < obstacles.size(); i++) {
				if (!this.crushed)
					this.crushed = obstacles.get(i).ifHit(this.pozycja);
			}

			if (!this.getToTarget && !this.crushed) { // silnik
				this.pchnij(this.DNA.get(SetupAndDraw.getFrame()));

				this.predkosc.add(this.przyspieszenie);
				this.pozycja.add(this.predkosc);
				this.przyspieszenie.mult((float) 0);
			}
		}
	}

	void show() {
		p.fill(138, 43, 206);
		p.ellipse(this.pozycja.x, this.pozycja.y, 10, 10);
	}

	public double getFitness() {
		return fitness;
	}

	public boolean getInTarget(){
		return getToTarget;
	}
	
}
