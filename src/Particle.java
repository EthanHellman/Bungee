
//import java.awt.Color;

import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

public class Particle extends Circle{
	static final double gravity = 9.8;
	Circle body = new Circle();
	
	double beta; // air resistance constant
	double yDrag; // the change in acceleration of y
	double xDrag; // the change in acceleration of x

	boolean reachedTarget; // if the ball has gotten to the target

	double xTarget;// the x target of the shot
	double yTarget;// the y target of the shot

	double XtargetVelocity;// the x component of the velocity of the ball at the target
	double YtargetVelocity;// the y component of the velocity of the ball at the target

	double timeStep; // the interval between each timestep
	double time; // the total time of the simulation

	double y; // the height of the ball
	double yVinit; //the velocity of the ball before the acceleration changes it
	double yV;// the velocity of the ball after the acceleration changes it 
	double yAcc;//the acceleration of the ball

	double x;// the distance of the ball
	double xVinit;//the velocity of the ball before the acceleration changes it
	double xV;// the velocity of the ball after the acceleration changes it 
	double xAcc; //the acceleration of the ball

	double velocity; //the initial velocity vector
	double radians; //the angle in radians of the ball

	int index; // used for giving the graph different colors

	boolean targetTesting;//Will stop the simulation once the x and y reach the target if true
	Trail trajectory = new Trail(); //the xy graph of the motion

	double xInit;
	double yInit;

	double realDisplacementY;//The y distance traveled - the initial y
	double targetDisplacmentY;//the target y distance traveled - the initial y


/**
 * Constructs a particle without a velocity. Used in the green monster project
 */
	Particle(double timeStep, double xInit,double yInit,double xTarget,double yTarget, double angle, double beta, int index, PlotFrame xyFrame){
		this.time = 0;
		this.timeStep = timeStep;
		this.xInit = xInit;
		this.yInit = yInit;
		this.x = xInit;
		this.y = yInit;
		this.xTarget = xTarget;
		this.yTarget = yTarget;
		this.radians = Math.toRadians(angle);
		this.beta = beta;
		this.yAcc = gravity;
		this.index = index;
		xyFrame.addDrawable((this.trajectory));;
		targetTesting = false;

	}
/**
 * constructs a particle with a given beta. Used in the Projectile Experiment Project
 */
	Particle(double timeStep, double xInit,double yInit,double xTarget,double yTarget, double angle, double beta, int index, PlotFrame xyFrame, double velocity){
		this.time = 0;
		this.timeStep = timeStep;
		this.xInit = xInit;
		this.yInit = yInit;
		this.x = xInit;
		this.y = yInit;
		this.xTarget = xTarget;
		this.yTarget = yTarget;
		this.radians = Math.toRadians(angle);
		this.beta = beta;
		this.yAcc = gravity;
		this.index = index;
		xyFrame.addDrawable((this.trajectory));;
		this.determineVelocities(velocity);
		this.velocity = velocity;

	}
/**
 * When creating a general particle target testing is true used in XYMotion
 */
	public Particle(){targetTesting = true;}
/**
 * given a velocity and angle finds x and y components
 */
	void determineVelocities(double velocity) {
		this.xV = (velocity) * Math.cos(this.radians); // x component of the velocity
		this.yV = (velocity) * Math.sin(this.radians); // y component of the velocity
	}
/**
 * returns if the particle has landed
 */
	boolean landed() {
		if(this.y < 0) return true; // if the ball has hit the ground
		else return false;


	}
	/**
	 * this is used in the Green Monster and XY. Tests if the ball has reached the target.
	 * @return
	 */
	boolean reachedTarget() {
//		double realDisplacementX = (Math.abs(this.xInit-this.x));
//		double targetDisplacmentX = Math.abs(this.xTarget - this.xInit);
//		realDisplacementY = this.yInit - this.y;
//		targetDisplacmentY = this.yInit - this.yTarget;
//		if( realDisplacementX >  targetDisplacmentX) {
//			if(targetDisplacmentY > 0) {//if the end is higher than the start,
//				if(Math.abs(realDisplacementY) > Math.abs(targetDisplacmentY)) return true;//checks if the ball is higher than the target
//			}
//			if(targetDisplacmentY < 0) {//if the end result is lower than the start, 
//				if(Math.abs(realDisplacementY) < Math.abs(targetDisplacmentY)) return true;//checks if the ball is below the target
//			}
//		}
//		return false;
		if((Math.abs(x) >= Math.abs(xTarget))&& (Math.abs(y) >= Math.abs(yTarget))) return true;
		else return false;
	}
	/**
	 * sets the y drag using the ßv^2 equation
	 * @param V velocity 
	 * @param ß beta
	 */
	void determineYAirResistance(double V, double ß) {
		this.yDrag = V * V * ß; // finds the amount air resistance effects acceleration
	}
	/**
	 * sets the x drag using the ßv^2 equation
	 * @param V velocity
	 * @param ß beta
	 */
	void determineXAirResistance(double V, double ß) {
		this.xDrag = V * V * ß; // finds the amount air resistance effects acceleration
	}
	/**
	 * iterates time
	 */
	void changeTime() {
		this.time = this.time + this.timeStep;
	}
	/**
	 * iterates y acceleration
	 */
	void changeYAcc(){
		if(this.yV > 0) this.yAcc= -Particle.gravity - this.yDrag;//in different directions the air will be pushing in a different direction
		else this.yAcc= -Particle.gravity + this.yDrag;
	}
	/**
	 * iterates y velocity
	 */
	void changeYV() {
		this.yVinit = this.yV;
		this.yV = this.yV + this.yAcc*this.timeStep;
	}
	/**
	 * iterates y position
	 */
	void changeYPos() {
//		this.y = this.y + (this.yVinit + this.yV)/2 * this.timeStep;//uses trapezoid rule
				this.y = this.y + this.yV *this.timeStep;//uses rectangle rule

	}
	/**
	 * iterates x accelerates
	 */
	void changeXAcc() {
		if(this.xV > 0) this.xAcc = -this.xDrag;//in different directions the air will be pushing in a different direction
		else this.xAcc =+ this.xDrag;


	}
	/**
	 * iterates x velocity
	 */
	void changeXV() {
		this.xVinit = this.xV;
		this.xV = this.xV + this.xAcc*timeStep;
	}
	/**
	 * iterates x position
	 */
	void changeXPos() {
//		this.x = this.x +  (this.xVinit + this.xV)/2 * this.timeStep;//uses trapezoid rule
		this.x = this.x + this.xV *this.timeStep;//uses rectangle rule
	}
	void changePos(){
		this.x = this.x + this.xV *this.timeStep;
		this.y = this.y + this.yV *this.timeStep;
		body.setXY(this.x,this.y);
	}
	/**
	 * uses the functions required to describe the motion and iterates all of them 
	 */
	void moveStep() {
		
		if(!this.landed()) {

			this.changeTime();

			determineYAirResistance(this.yV, this.beta);
			this.changeYAcc();
			determineXAirResistance(this.xV, this.beta);
			this.changeXAcc();

			this.changeYV();
			this.changeXV();

			this.changePos();
			
			if(targetTesting) {
				if(this.reachedTarget() && !this.reachedTarget) {
					this.XtargetVelocity = this.xV;
					this.YtargetVelocity = this.yV;
					this.reachedTarget = true;
				}
			}

		}
	}

	void printStep() {

		this.trajectory.addPoint(this.x,this.y);
	}
	
	void printData(PlotFrame[] frames) {
		frames[0].append(this.index-1, this.time, this.y);//position
		frames[0].append(this.index , this.time, this.x);//position
		frames[1].append(this.index - 1, this.time, this.yV);//velocity 
		frames[1].append(this.index , this.time, this.xV);//velocity
		frames[2].append(this.index - 1, this.time,  this.yAcc);//acceleration
		frames[2].append(this.index , this.time, this.xAcc);//acceleration
	}
	
	/**
	 * tests if the particle has reached the target in the context of the experiment calculating the effect of air resistance
	 * @return
	 */
	boolean betaReachedTarget() {
		realDisplacementY = this.yInit - this.y;
		targetDisplacmentY = this.yInit - this.yTarget;
		if( Math.abs(this.x - this.xTarget)   < .001) {//if it is less than a milimeter away
//			System.out.println(this.beta);
				return true;
		}
		return false;

	}










}
