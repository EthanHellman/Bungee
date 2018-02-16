//this is a special force, it extends the force class and applies to everything that has a mass
public class GravitationalForce extends Force {
	final static public double gravitationalAcceleration = 9.8; // setting the acceleration due to gravity
	public GravitationalForce(double mass) {
		this.radians = -Math.PI/2; //setting direction of force
		this.Newtons = mass * gravitationalAcceleration; //setting the magnitude of the force based on the given mass
	}
}


