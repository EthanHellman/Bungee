import org.opensourcephysics.controls.*;  //needed for the simulation
import org.opensourcephysics.frames.*;   //needed to use a DisplayFrame

public class Simulation extends AbstractSimulation {
	public Simulation() {

	}
	int i = 0;


	double time;
	double xInit;
	double yInit;
	double timeStep;
	PlotFrame xyFrame = new PlotFrame("x", "y", "Trajectory");
//	PlotFrame aFrame = new PlotFrame("x", "y", "Trajectory");

	Bungee cord;
	int j = 0;
	protected void doStep() {

		cord.update();
//		aFrame.append(0, time+timeStep*j++, cord.Masses.get(0).Ya );
		if((cord.Masses.get(0).getY() <= 0)) {
			if(cord.Masses.get(0).Yv > 0) {
				System.err.println(cord.k);
				this.stop();
			}
			else 		{
				i++;
				System.out.println(cord.Masses.get(0).Yv);
				cord = new Bungee(numSprings, length/numSprings, k + i * 10, mass/numSprings, massPerson, timeStep, xyFrame, x, y);
				System.out.println(k + i * 10);
			}
		}
		else if(cord.Masses.get(0).Yv > 0) {
			System.err.println(cord.k);
			this.stop();
		}
	}
	public void reset() {
		i=0;
		xyFrame.clearDrawables();;
		control.setValue("x", 0);
		control.setValue("y", 100);
		control.setValue("Time Step", .01);
		control.setValue("Number of Springs", 80);
		control.setValue("Mass of Person", 50);
		control.setValue("K", 2600);
		control.setValue("Mass of bungee cord", 50);
		control.setValue("Length of bungee", 40);

		xyFrame.clearData();
		this.setDelayTime(1);
	}

	double numSprings;
	double length;
	double k;
	double mass;
	double massPerson;
	double x;
	double y;
	public void initialize() {
		numSprings = control.getDouble("Number of Springs");
		length = control.getDouble("Length of bungee");
		k  = control.getDouble("K");
		mass = control.getDouble("Mass of bungee cord");
		massPerson = control.getDouble("Mass of Person");
		timeStep = control.getDouble("Time Step");
		x =  control.getDouble("x");
		y = control.getDouble("y");

		xyFrame.setPreferredMinMaxY(-50, 150);
		xyFrame.clearData();
		cord = new Bungee(numSprings, length/numSprings, k, mass/numSprings, massPerson, timeStep, xyFrame, x, y);
	}

	public static void main(String[] args) {

		SimulationControl.createApp(new Simulation());

	}
}