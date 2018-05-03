import org.opensourcephysics.controls.*;  //needed for the simulation
import org.opensourcephysics.frames.*;   //needed to use a DisplayFrame
//
/**
 * 
 * @author Stephen Eisner & Ethan Hellman
 * 
 * This is our simulation class which brings together all of the objects previously created.
 * This class is both capable of finding the proper spring constant to get the person to land
 * just above/on the water. It is also capable of modeling the motion of a bungee cord given a
 * certain spring constant, starting height, number of springs, and timestep. Because we used the
 * rectangle rule in our calculations of, changing the timestep to something small yields a better
 * simulation. Otherwise, what will be witnessed is the "oscillation" effect which gives a particle
 * more or less energy based on the size of the timestep and the direction of its acceleration, resulting
 * in the particle being either higher or lower than it should be. That is why a smaller timestep is
 * recommended when running our simulation
 *
 */
public class Simulation extends AbstractSimulation {
	public Simulation() {

	}
	int i = 0;

	//variable initialization
	double time;
	double xInit;
	double yInit;
	double timeStep;
	PlotFrame xyFrame = new PlotFrame("x", "y", "Trajectory");
//	PlotFrame aFrame = new PlotFrame("x", "y", "Trajectory"); //this frame plots the acceleration of the person at any given time 
	Bungee cord;
	int j = 0;
	
	//our doStep function which runs until told otherwise:
	protected void doStep() {

		//continually updates the position of the particle:
		cord.update();
//		aFrame.append(0, time+timeStep*j++, cord.Masses.get(0).Ya ); //plots the acceleration of the person at the given time 
		
		//the following lines of code are used when one is trying to find the proper
		//spring constant to have the person land exactly above the water 
//		if((cord.Masses.get(0).getY() <= 0)) { //here it evaluates whether the person is at the level of the water
//			if(cord.Masses.get(0).Yv > 0) { //if the velocity is zero then that means that we have the proper spring constant 
//				System.err.println(cord.k); //it prints this constant
//				this.stop(); //stops the program because we have our desired answer 
//			}
//			else 		{ //if it is not stopped, then it will increase the spring constant
//				i++;
				//initializes the bungee cord again, but this time with a higher spring constant 
//				cord = new Bungee(numSprings, length/numSprings, k + i * 10, mass/numSprings, massPerson, timeStep, xyFrame, x, y);
//			}
//		}
		//this case prints if the particle has not reached the water and has a velocity which
		//is in the upwards direction. In this case, we know that the spring constant is too high 
//		else if(cord.Masses.get(0).Yv > 0) {
//			System.err.println(cord.k);
		//stops the program in this case 
//			this.stop();
//		}
	}
	
	//our reset function 
	public void reset() {
		i=0;
		xyFrame.clearDrawables(); //clears everything from the frame so what we see is only the acting spring 
		//initializes all of the variable prompts 
		control.setValue("x", 0);
		control.setValue("y", 100);
		control.setValue("Time Step", .01);
		control.setValue("Number of Springs", 80);
		control.setValue("Mass of Person", 50);
		control.setValue("K", 2600);
		control.setValue("Mass of bungee cord", 50);
		control.setValue("Length of bungee", 40);

		xyFrame.clearData();
		this.setDelayTime(1);
	}

	//parameters for our bungee cord 
	double numSprings;
	double length;
	double k;
	double mass;
	double massPerson;
	double x;
	double y;
	
	//our initialize function
	public void initialize() {
		//sets all of the parameters equal to the inputs from the user 
		numSprings = control.getDouble("Number of Springs");
		length = control.getDouble("Length of bungee");
		k  = control.getDouble("K");
		mass = control.getDouble("Mass of bungee cord");
		massPerson = control.getDouble("Mass of Person");
		timeStep = control.getDouble("Time Step");
		x =  control.getDouble("x");
		y = control.getDouble("y");

		//changes the frame
		xyFrame.setPreferredMinMaxY(-50, 150);
		//clears everything from the frame again 
		xyFrame.clearData();
		//initializes our bungee cord 
		cord = new Bungee(numSprings, length/numSprings, k, mass/numSprings, massPerson, timeStep, xyFrame, x, y);
	}

	public static void main(String[] args) {
		//Creating a new simulation 
		SimulationControl.createApp(new Simulation());

	}
}
