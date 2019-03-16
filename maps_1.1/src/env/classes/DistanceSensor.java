package classes;

import artifacts.ISensorService;

public class DistanceSensor implements ISensorService{

	
	private boolean isActivated;
	private double distance = 0;
	
	@Override
	public boolean enable() {
		isActivated = true;
		return isActivated;
	}

	@Override
	public boolean disable() {
		isActivated = false;
		return isActivated;
	}

	@Override
	public Object read() {
		distance = Math.random();
		return distance;
	}

	@Override
	public boolean test() {
		return (isActivated && distance > 0);
			
	}

}
