//this class is simple, it holds the basics of a force
public class Force {
	double Newtons; // the magnitude of the force
	double radians; // the direcet oc the force
	public boolean top(Particle p) {//this exists so that we can ovveride it in the spring class. 
		return false;
	};
}

