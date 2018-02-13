public class Spring extends Force{	
	Particle[] particles= new Particle[2];
	double length;
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
		this.Newtons = this.K*(displacement- this.length);
		
		this.radians = this.particles[0].angleBetween(this.particles[1]);
	}
	@Override
	public boolean top(Particle p) {
			if(p.equals(this.particles[1])) return true;
			return false;
		
	}
}