
public class GravitationalForce extends Force {
	final static public double gravitationalAcceleration = 9.8;
	public GravitationalForce(double mass) {
		this.radians = -Math.PI/2;
		this.Newtons = mass * gravitationalAcceleration;
	}
}
