import java.util.ArrayList;
import java.util.List;

import org.opensourcephysics.display.Circle;

public class Particle extends Circle{

	public double round(double num) {
		if(Math.abs(Math.round(num) - num) < .0000001) num = Math.round(num);//rounds the number if it the difference between it and it rounded is very small
		return num;
	}
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
		this.pixRadius = 3;
		this.x = xInit;
		this.y = yInit;
		this.timeStep = timeStep;
		this.mass = mass;
		forces = new ArrayList<Force>();
		forces.add(new GravitationalForce(mass));
	}

	public void updateAcc() {
		double YForce= 0;
		double XForce= 0;
		boolean top = false;
		for(Force force:forces) {
			if(force.getClass().getName().equals("Spring")) {
				if(force.top(this)) {
					top = true;
					force.radians = -force.radians;
				}
			}
			YForce += this.round(force.Newtons*Math.sin(force.radians));
			XForce += this.round(force.Newtons*Math.cos(force.radians));
			if(top) {
				force.radians = -force.radians;
				top = false;
			}

		}

		this.Xa = XForce/this.mass;
		this.Ya = YForce/this.mass;

	}
	public void updateVelocity() {
		this.Yv += this.Ya*this.timeStep;	
		this.Xv += this.Xa*this.timeStep;	
	}

	public void updatePosition() {
		if(!fixed) {
			this.x += this.Xv *this.timeStep;//uses rectangle rule
			this.y += this.Yv *this.timeStep;//uses rectangle rule
		}
	}	






	public void Step() {
		this.updateAcc();
		this.updateVelocity();
		this.updatePosition();
	}
	public double distanceBetween(Particle p) {
		return this.round(Math.sqrt(Math.pow((this.x - p.x), 2) + Math.pow((this.y - p.y), 2)));
	}
	@SuppressWarnings("null")
	public double angleBetween(Particle p) {//returns the angle the ray makes with an x axis going through the particle calling the function parallel to the x axis of the plot frame;
		double y = this.round(p.y - this.y);
		double x = this.round(p.x - this.x);
		if(y == 0) {
			if (x > 0)
				return 0;
			else if(x < 0) {
				return Math.PI;
			}
			else {
				return 0;
			}
		}
		else if(x == 0) {
			if(y > 0) {
				return Math.PI/2;
			}
			else {//its not 0 and not greater so it must be less than 0
				return -Math.PI/2;
			}
		}
		else return Math.atan(y/x);
	}



}
