import org.opensourcephysics.controls.*;  //needed for the simulation
import org.opensourcephysics.frames.*;   //needed to use a DisplayFrame

/**
 * This class contains the basics of any simulation with some simple graphics.
 */
public class Simulation extends AbstractSimulation {
	public Simulation() {

		
	}



	double xInit;
	double yInit;


	boolean done = false;
	double timeStep;


	PlotFrame xyFrame = new PlotFrame("x", "y", "Trajectory");
	Bungee chord;

	/**
	 *
	 *Iterates the particle one step and stops if it reaches the target
	 *
	 */
	protected void doStep() {
		chord.update();
	}

	/**
	 *  
	 *  Creates a list of particles with different betas
	 *  
	 */
	public void reset() {
		control.setValue("x", 0);
		control.setValue("y", 100);
		control.setValue("Time Step", .1);
		control.setValue("Number of Springs", 4);
		control.setValue("Length of each spring", .5);
		control.setValue("Mass of Person", 50);
		control.setValue("K", 50);
		control.setValue("Mass of each spring", 1);
	
		xyFrame.clearData();
		this.setDelayTime(1);


	}

	/**
	 * 
	 * Creates a particle defined by inputs from the simulation interface
	 * 
	 */
	public void initialize() {

		xyFrame.clearData();
		 chord = new Bungee(control.getDouble("Number of Springs"), control.getDouble("Length of each spring"), control.getDouble("K"), control.getDouble("Mass of each spring"), control.getDouble("Mass of Person"), control.getDouble("Time Step"), xyFrame, control.getDouble("x"), control.getDouble("y"));
		
	}






	public static void main(String[] args) {

		SimulationControl.createApp(new Simulation());

	}

}