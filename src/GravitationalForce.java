//this is a special force, it extends the force class and applies to everything that has a mass
public class GravitationalForce extends Force {

	@Override
	public double getXForce(Particle p) {
		// TODO Auto-generated method stub
		return 0;
	}//

	@Override
	public double getYForce(Particle p) {
		// TODO Auto-generated method stub
		return 0;
	}
//	final static public double gravitationalAcceleration = 9.8; // setting the acceleration due to gravity
//	public GravitationalForce(double mass) {
//		this.radians = -Math.PI/2; //setting direction of force
//		this.Newtons = mass * gravitationalAcceleration; //setting the magnitude of the force based on the given mass
//	}
//	@Override
//	public double getXForce(Particle p) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	@Override
//	public double getYForce(Particle p) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
}

