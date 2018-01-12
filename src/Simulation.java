import org.opensourcephysics.controls.*;  //needed for the simulation
import org.opensourcephysics.frames.*;   //needed to use a DisplayFrame

/**
 * This class contains the basics of any simulation with some simple graphics.
 */
public class Simulation extends AbstractSimulation {
	public Simulation() {
		detailedPlot = false;
		targetTesting = false;
		xInit = 0;
		yInit = 100;
		xTarget = 0;
		yTarget = 0;
		velocity = 10;
		angle = 90;
		
	}

	public Simulation(boolean detailedPlot, boolean targetTesting, double xInit, double yInit, double xTarget, double yTarget, double velocity, double angle ) {
		this.detailedPlot = detailedPlot;
		this.targetTesting = targetTesting;
		this.xInit = xInit;
		this.yInit = yInit;
		this.xTarget = xTarget;
		this.yTarget = yTarget;
		this.velocity = velocity;
		this.angle = angle;
	}

	boolean detailedPlot;
	boolean targetTesting;
	double xInit;
	double yInit;
	double xTarget;
	double yTarget;
	double velocity;
	double angle;

	boolean done = false;
	Particle ball = new Particle();
	double timeStep;

	double targetVelocity;
	double targetAngle;
	PlotFrame xyFrame = new PlotFrame("x", "y", "Trajectory");
	PlotFrame posFrame;
	PlotFrame vFrame;
	PlotFrame aFrame;

	/**
	 *
	 *Iterates the particle one step and stops if it reaches the target
	 *
	 */
	protected void doStep() {
		ball.printStep();//prints a step
		if(detailedPlot)
			ball.printData(new PlotFrame[] { posFrame, vFrame, aFrame});
		ball.moveStep();//ch\anges the positition velocity and acceleration
		if(ball.reachedTarget) {//will only be true if targetTesting is true NOTE: This is used in the extension to find the velocity at the moment of the collision
			ball.printStep();
			targetVelocity = this.returnVelocity();
			targetAngle = this.returnAngle();
			done = true;
			this.stopSimulation();

		}
		if(!ball.reachedTarget && ball.landed()) {
			this.stopSimulation();
			System.out.println("The ball never reached the target");
		}


		control.println("v= "+ ball.yV);
		control.println("a = "+ ball.yAcc);
		control.println("rT = " + ball.reachedTarget);

	}

	/**
	 *  
	 *  Creates a list of particles with different betas
	 *  
	 */
	public void reset() {
		control.setValue("x", xInit);
		control.setValue("y", yInit);
		control.setValue("x target", xTarget);
		control.setValue("y target", yTarget);
		control.setValue("velocity", velocity);
		control.setValue("angle", angle);
		control.setValue("Time Step", .1);
		control.setValue("Detailed Plot", detailedPlot);
		control.setValue("targetTesting", targetTesting);
		ball.time = 0;
		xyFrame.clearData();
		if(detailedPlot) {
			posFrame.clearData();
			vFrame.clearData();
			aFrame.clearData();
		}
		ball.trajectory.clear();
		ball.reachedTarget = false;
		done = false;

	}

	/**
	 * 
	 * Creates a particle defined by inputs from the simulation interface
	 * 
	 */
	public void initialize() {
		detailedPlot = control.getBoolean("Detailed Plot");
		ball.targetTesting = control.getBoolean("targetTesting");
		ball.time = 0;
		xyFrame.clearData();
		if(detailedPlot) {
			posFrame.clearData();
			vFrame.clearData();
			aFrame.clearData();
		}
		ball.beta = .02;
		ball.setXY(control.getDouble("x"), control.getDouble("y"));
		ball.x = control.getDouble("x");
		ball.xInit = ball.x;
		ball.y = control.getDouble("y");
		ball.yInit = ball.y;
		ball.radians = control.getDouble("angle");
		ball.radians = Math.toRadians(ball.radians);
		ball.velocity = control.getDouble("velocity");
		ball.determineVelocities(ball.velocity);

		ball.timeStep = (control.getDouble("Time Step")); 

		ball.xTarget = control.getDouble("x target");
		ball.yTarget = control.getDouble("y target");

		ball.yAcc = Particle.gravity;
		ball.index = 1;
		xyFrame.addDrawable(ball.trajectory);
		xyFrame.addDrawable(ball.body);
		if(detailedPlot) {
			posFrame = new PlotFrame("t", "x", "Position v Time");
			vFrame = new PlotFrame("t", "v", "Velocity v Time");
			aFrame = new PlotFrame("t", "a", "Acceleration v Time");
		}
	}
	/**
	 * @return the magnitude of the velocity vector
	 */
	double returnVelocity() {
		return Math.sqrt(ball.YtargetVelocity*ball.YtargetVelocity  + ball.XtargetVelocity*ball.XtargetVelocity);//returns the magnitude of the velocity at the target point
	}
	/**
	 * @return the angle of the velocity vector
	 */
	double returnAngle() {
		return Math.toDegrees(Math.atan(ball.YtargetVelocity/ball.XtargetVelocity));//returns the angle of the velocity vector at the target point
	}





	public static void main(String[] args) {

		SimulationControl.createApp(new Simulation());

	}

}