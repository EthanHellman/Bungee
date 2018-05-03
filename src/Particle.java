//import java.util.ArrayList;
//import java.util.List;
//
//import org.opensourcephysics.display.Circle;
//
////this class is what we use for the masses in between springs, each one holds the basics of a
////particle: mass, position, velocity, acceleration...
////extends circle because the particles are graphable and will be plotted on our graph
//public class Particle extends Circle{
//
//	public double round(double num) {
//		if(Math.abs(Math.round(num) - num) < .0000001) num = Math.round(num);//rounds the number if it the difference between it and it rounded is very small
//		return num;
//	}
//	//
//	//initializing the characteristics of a particle
//	double Xa;
//	double Ya;
//	double Xv;
//	double Yv;
//	boolean fixed;
//
//
//	//x and y are part of the circle class
//
//	//setting the timestpe
//	double timeStep;
//
//	double mass; //the mass
//	List<Force> forces; //initializing the list of forces that are acting on the mass
//	
//	//constructor
//	public Particle(double mass, double timeStep, double xInit, double yInit) {
//		//setting all of the variables
//		this.pixRadius = 3;
//		this.x = xInit;
//		this.y = yInit;
//		this.timeStep = timeStep;
//		this.mass = mass;
//		//initializes the list of forces
//		forces = new ArrayList<Force>();
//		//adds the gravitational force
//		forces.add(new GravitationalForce(mass));
//	}
//
//	//one of the three update functions which evaluates the acceleration at a give time
//	public void updateAcc() {
//		//sets the forces equal to zero to begin with
//		double YForce= 0;
//		double XForce= 0;
//		boolean top = false;
//		//goes through each force in the list of forces acting on the particle and evaluates their magnitudes 
//		for(Force force:forces) {
//			if(force.getClass().getName().equals("Spring")) {
//				//switches the direction of the force of the spring in case the spring is on top 
//				if(force.top(this)) {
//					top = true;
//					force.radians = -force.radians;
//				}
//			}
//			YForce += this.round(force.Newtons*Math.sin(force.radians)); //finds the magnitude of the sum of the forces in the x direction
//			XForce += this.round(force.Newtons*Math.cos(force.radians)); //finds the magnitude of the sum of the forces in the y direction
//			//resets the direction of the force of the spring in case it is the spring on top of the particle
//			if(top) {
//				force.radians = -force.radians;
//				top = false;
//			}
//
//		}
//
//		//changes the accelerations in the x and y directions based on the sum of forces in the each direction
//		this.Xa = XForce/this.mass;
//		this.Ya = YForce/this.mass;
//
//	}
//	
//	//another update function which evaluates the new velocity based on the new accelerations
//	public void updateVelocity() {
//		this.Yv += this.Ya*this.timeStep; //multiples the acceleration by the timestep and then adds it to the velocity to get new velocity
//		this.Xv += this.Xa*this.timeStep; //multiples the acceleration by the timestep and then adds it to the velocity to get new velocity
//	}
//
//	//the last of the update functions which changes the position of the particle
//	//after having evaluated the new velocity
//	//uses the rectangle rule to evaluate the new position
//	public void updatePosition() {
//		if(!fixed) {
//			this.x += this.Xv *this.timeStep;//multiplies the new velocity by the timestep to find the change in distance in x direction 
//			this.y += this.Yv *this.timeStep;//multiplies the new velocity by the timestep to find the change in distance in x direction 
//		}
//	}	
//	
//	//this is the "step" function which calls all three of the update functions in proper order
//	//updates the accleration then the velocity and finally the position
//	public void Step() {
//		this.updateAcc();
//		this.updateVelocity();
//		this.updatePosition();
//	}
//	//finds the distance between two particles
//	public double distanceBetween(Particle p) {
//		//uses the distance formula to evaluate the distance between two particles
//		return this.round(Math.sqrt(Math.pow((this.x - p.x), 2) + Math.pow((this.y - p.y), 2)));
//	}
//	@SuppressWarnings("null")
//	//returns the angle the ray makes with an x axis going through the particle calling the function parallel to the x axis of the plot frame;
//	public double angleBetween(Particle p) {
//		double y = this.round(p.y - this.y); //finds the distance between particles in the y direction 
//		double x = this.round(p.x - this.x); //finds the distance between particles in the x direction
//		if(y == 0) {
//			if (x > 0)
//				return 0;
//			else if(x < 0) {
//				return Math.PI;
//			}
//			else {
//				return 0;
//			}
//		}
//		else if(x == 0) {
//			if(y > 0) {
//				return Math.PI/2;
//			}
//			else {//its not 0 and not greater so it must be less than 0
//				return -Math.PI/2;
//			}
//		}
//		else return Math.atan(y/x);
//	}
//}
//

import java.util.ArrayList;
import java.util.List;

import org.opensourcephysics.display.Trail;

public class Particle extends Coordinate{
	double lastVx;
	double lastVy;

	Trail trl = new Trail();
	double Xa;
	double Ya;
	double Xv;
	double Yv;

	boolean fixed;
	//x and y are part of the circle class
	double timeStep;
	
	double mass;

	List<Force> forces;
	public Particle(double mass, double timeStep, double xInit, double yInit) {
		super(xInit, yInit);
		this.pixRadius = 3;
		this.timeStep = timeStep;
		this.mass = mass;
		forces = new ArrayList<Force>();
	}
	public void updateAcc() {
		double YForce= 0;
		double XForce= 0;
		for(Force force:forces) {
			XForce += (force.getXForce(this));
			YForce += (force.getYForce(this));
		}

		this.Xa = XForce/this.mass;
		this.Ya = YForce/this.mass;


	}
	public void updateVelocity() {
		this.lastVy = this.Yv;
		this.lastVx = this.Xv;
		this.Yv += this.Ya*this.timeStep;	
		this.Xv += this.Xa*this.timeStep;	
	}
	public void updatePosition() {
		this.y += this.Yv *this.timeStep;//uses rectangle rule
		this.x += this.Xv *this.timeStep;//uses rectangle rule

	}	
	public void Step() {
		this.updateAcc();
		this.updateVelocity();
		this.updatePosition();
		this.updateTrail();
	}
	public double distanceBetween(Particle p) {
		return (Math.sqrt(Math.pow(this.xDistanceBetween(p), 2) + Math.pow(this.yDistanceBetween(p), 2)));
	}

	public void updateTrail() {
		trl.addPoint(this.getX(), this.getY());
	}
}

