import java.util.ArrayList;

//Test the git

public class Spring extends Force{
	Particle[] particles= new Particle[2];
	double displacement;
	double length;
	boolean top;
	double K;
	public Spring(double length, double K, Particle... particles) {
		for(int i = 0; i < particles.length; i++) {
			this.particles[i] = particles[i];
			this.particles[i].forces.add(this);
		}
		this.length = length;
		this.K = K;
	}
	public void update() {
		double displacement = this.particles[0].distanceBetween(this.particles[1]);
		if(displacement>this.length)this.Newtons = this.K*(displacement- this.length);
		else this.Newtons = 0;
		this.radians = Math.atan((this.particles[1].getY()-this.particles[0].getY())/(this.particles[1].getX()-this.particles[0].getX()));

	}

}
