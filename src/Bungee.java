import java.awt.Color;
import java.util.ArrayList;

import org.opensourcephysics.frames.PlotFrame;

public class Bungee {
	public double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision+3);
	    return (double) Math.round(value * scale) / scale;
	}
	double numSprings;
	final double mass;
	final double lengthPerSpring;
	double k;
	int uncoiledBits = 1;
	double startHeight;
	int precision;
	ArrayList<Spring> Springs;
	ArrayList<Particle> Masses;
	boolean slack = true;
	public Bungee(double numSprings, double length, double K, double springMass, double bodyMass, double timeStep, PlotFrame pFrame, double x, double y) {//mass is the mass of each individual spring. Not really tho, the particles next to each. Same ish wit da K
	
		Double d = length;
		String[] splitter = d.toString().split("\\.");	
		this.precision = splitter[1].length(); 
		
		this.startHeight = y;
		this.k = K/numSprings;
		this.numSprings = numSprings;
		this.mass = springMass * numSprings;
		this.lengthPerSpring = length;
		Masses = new ArrayList<Particle>();
		Springs = new ArrayList<Spring>();
//		Masses.add(new Particle(bodyMass, timeStep, x, y-length*numSprings));//adds the human
		Masses.add(new Particle(bodyMass, timeStep, x, y));//adds the human

		Masses.get(0).color = Color.BLUE;
		Masses.get(0).pixRadius = 6;
		pFrame.addDrawable(Masses.get(0));
		for(int i = 0; i < numSprings; i++) {
			Masses.add(new Particle(springMass, timeStep, x, y));
//			Masses.add(new Particle(springMass, timeStep, x, y-(numSprings - i-1 )*length));

			pFrame.addDrawable(Masses.get(i));
			Spring s = new Spring((length), K, Masses.get(i),Masses.get(i+1));
			this.Springs.add(s);
		}
		Masses.get(Masses.size()-1).fixed = true;
		Masses.get(Masses.size()-1).color = Color.BLACK;//here yurd
		pFrame.addDrawable(Masses.get(Masses.size()-1));
	}
	public void updateSprings() {
		for(int i = 0; i < Springs.size(); i ++) {
			Springs.get(i).update();
		}		
	}
	public void updateParticles() {
		for(int i = 0; i < Masses.size(); i++) {
			Masses.get(i).Step();
		}
	}
	public void update(){
		
		if(slack) {
			System.out.println(Masses.get(0).getY());
			System.err.println(this.round(Masses.get(0).getY(), 1));
			System.err.println(startHeight-(uncoiledBits*lengthPerSpring));
			for(int i = 0; i < uncoiledBits; i++) {
				Masses.get(i).Step();
			}
			if(startHeight-(uncoiledBits*lengthPerSpring) >= this.round(Masses.get(0).getY(), 1)) {
				uncoiledBits++;	
			}
			if(uncoiledBits-1 == numSprings ) slack = false;
		}
		
		if(!slack) {
			this.updateParticles();
			this.updateSprings();
		}
	}
}