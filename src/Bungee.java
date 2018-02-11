import java.awt.Color;
import java.util.ArrayList;

import org.opensourcephysics.frames.PlotFrame;

public class Bungee {
	double numSprings;
	final double mass;
	final double lengthPerSpring;
	ArrayList<Spring> Springs;
	ArrayList<Particle> Masses;
	public Bungee(double numSprings, double length, double K, double springMass, double bodyMass, double timeStep, PlotFrame pFrame, double x, double y) {//mass is the mass of each individual spring. Not really tho, the particles next to each. Same ish wit da K
		this.numSprings = numSprings;
		this.mass = springMass * numSprings;
		this.lengthPerSpring = length;
		Masses = new ArrayList<Particle>();
		Springs = new ArrayList<Spring>();
		Masses.add(new Particle(bodyMass, timeStep, x, y-length*numSprings));//adds the human
		Masses.get(0).color = Color.BLUE;
		pFrame.addDrawable(Masses.get(0));
		for(int i = 0; i < numSprings; i++) {
			Masses.add(new Particle(springMass, timeStep, x, y-(numSprings-i-1)*length));
			pFrame.addDrawable(Masses.get(i));
			Spring s = new Spring((length), K, Masses.get(i),Masses.get(i+1));
			this.Springs.add(s);
		}
		Masses.get(Masses.size()-1).fixed = true;
		
		
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
		this.updateParticles();
		this.updateSprings();
	}
}



