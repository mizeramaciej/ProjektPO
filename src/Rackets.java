import java.util.ArrayList;
import processing.core.PApplet;

public class Rackets {

	private ArrayList<Racket> rackets = new ArrayList<Racket>();
	private ArrayList<Racket> matingPool = new ArrayList<Racket>();

	private PApplet p;

	public Rackets(PApplet parent, int populationSize) {
		this.p = parent;
		for (int i = 0; i < populationSize; i++) {
			this.rackets.add(new Racket(parent));
		}
		for (int i = 0; i < populationSize; i++) {
			this.rackets.get(i).createDNA();
		}
	}

	void evaluate(Target target, int populationSize) {

		for (int i = 0; i < populationSize; i++) {
			this.rackets.get(i).calcFitness(target.getPosition());
		}

		matingPool = new ArrayList<Racket>();

		for (int i = 0; i < populationSize; i++) {
			int n = (int) PApplet.round((float) this.rackets.get(i).getFitness()) * 10;

			for (int j = 0; j < n; j++) {
				matingPool.add(this.rackets.get(i));
			}
		}
	}

	void selection(PApplet parent, int populationSize) {

		for (int i = 0; i < populationSize; i++) {
			Racket parentA = matingPool.get((int) p.random(0, matingPool.size() - 1));
			Racket parentB = matingPool.get((int) p.random(0, matingPool.size() - 1));

			Racket child = parentA.crossover(parentB, parent);

			child.mutation(); // mutacja x%

			rackets.set(i, child);
		}

	}

	void createPopulation(PApplet parent, int populationSize) {
		for (int i = 0; i < populationSize; i++) {
			this.rackets.add(new Racket(parent));
		}

		for (int i = 0; i < populationSize; i++) {
			this.rackets.get(i).createDNA();
		}
	}

	void run(Target target, ArrayList<Obstacle> obstacles, int populationSize, ArrayList<Portal> portals) {
		for (int i = 0; i < populationSize; i++) {
			this.rackets.get(i).update(target, obstacles, portals);
			this.rackets.get(i).show();
		}
	}

	public ArrayList<Racket> getMatingPool() {
		return matingPool;
	}

	public ArrayList<Racket> getRackets() {
		return rackets;
	}

}
