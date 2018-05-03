import java.awt.Color;
import java.util.ArrayList;

import org.opensourcephysics.frames.PlotFrame;
//
//This is our bungee class which implements classes: Spring, Particle, and Force
public class Bungee {
	//another rounding function for the bungee class:
	public double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision+3);
	    return (double) Math.round(value * scale) / scale;
	}
	//initializing variables:
	double numSprings;
	final double mass;
	final double lengthPerSpring;
	double k;
	int uncoiledBits = 1;
	double startHeight;
	int precision;
	//initializing the two arrayList which hold all of the springs - equal to the number 
	//specified by the user - and the same goes for the masses, but + 1 because we have
	//to include the mass which is considered the person himself
	ArrayList<Spring> Springs;
	ArrayList<Particle> Masses;
	boolean slack = true; //tests to see if the bungee cord that not fallen yet and has "slack"
	
	//our constructor:
	public Bungee(double numSprings, double length, double K, double springMass, double bodyMass, double timeStep, PlotFrame pFrame, double x, double y) {//mass is the mass of each individual spring. Not really tho, the particles next to each. Same ish wit da K
	
		Double d = length;
		
		//finds the specified precision based on the user input, type: string
		String[] splitter = d.toString().split("\\.");	
		this.precision = splitter[1].length(); 
		
		//setting all variables equal to specified parameters:
		this.startHeight = y;
		this.k = K/numSprings;
		this.numSprings = numSprings;
		this.mass = springMass * numSprings;
		this.lengthPerSpring = length;
		Masses = new ArrayList<Particle>();
		Springs = new ArrayList<Spring>();
		
		Masses.add(new Particle(bodyMass, timeStep, x, y));//adds the human
		Masses.get(0).color = Color.BLUE; //setting color of the human 
		Masses.get(0).pixRadius = 6; //setting the size of the human 
		pFrame.addDrawable(Masses.get(0)); //adds the human to the frame
		for(int i = 0; i < numSprings; i++) {
			Masses.add(new Particle(springMass, timeStep, x, y)); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
			pFrame.addDrawable(Masses.get(i)); //adds the individual particle to the frame
			Spring s = new Spring((length), K, Masses.get(i),Masses.get(i+1)); //creates a new spring to go along with each particle
			this.Springs.add(s); //adds the spring to the arrayList of springs 
		}
		Masses.get(Masses.size()-1).fixed = true; 
		Masses.get(Masses.size()-1).color = Color.BLACK;//setting the color 
		pFrame.addDrawable(Masses.get(Masses.size()-1)); //adding the mass to the frame
	}
	
	//first of three update functions - this one updates all of the springs in the arrayList of springs
	public void updateSprings() {
		//for each spring, it will simply call the update function in the spring class 
		for(int i = 0; i < Springs.size(); i ++) {
			Springs.get(i).update();
		}		
	}
	
	//next update function updates all of the particles
	public void updateParticles() {
		//for each of the particles, it will call the update function in the particle class 
		for(int i = 0; i < Masses.size(); i++) {
			Masses.get(i).Step();
		}
	}
	
	//this is the master update function
	public void update(){
		//THIS PART THE IF SLACK THING MAKES SENSE IF WE WANT TO TEST OUR GUESS FOR HOW THE ROPE UNCOILS ELSE COMMENT IT OUT GO TO THE TOP AND SET SLACK TO FALSE. ALSO NOTICE HOW THERE IS A COMMENT ABOVE THE INITIALIZE OF THE BODY AND ALSO WITHIN THE FOR LOOP FOR ADDING THE PARTICLES TO THE MASSES. USE THE ONES THAT ARE CURRENTLY COMMENTED OUT IF YOU WANT TO HAVE IT RUN WITH NO INITIAL VELOCITY FROM THE 40 METERS SLACK. 
		//if there is slack to begin with, it will let all of the particles free-fall to begin our simulation
		if(slack) {
			//here we are updating all of the particles, but we are not updating all of the springs
			//because during the 40 meters of free-fall before the springs extend, they are exerting no
			//forces and therefore do not need to be evaluated
			for(int i = 0; i < uncoiledBits; i++) {
				Masses.get(i).Step();
				Springs.get(i).update();
			}
			if(startHeight-lengthPerSpring >= this.round(Masses.get(0).getY(), 1)) {
				uncoiledBits++;	
			}
			//will change the state of "slack" if it has reached the desired height 
			if(uncoiledBits-1 == numSprings ) slack = false;
		}
		
		//if there is no "slack" and the mass has reached 40 meters, then the program will
		//begin evaluating both the particles and the springs
		if(!slack) {
			this.updateParticles(); //gets the new displacement of the particles first so that we can properly evaluate the new forces of the springs
			this.updateSprings();
		}
	}
}



