package classes;

import artifacts.ISensorService;

public class PresenceSensor implements ISensorService {

	private boolean isAnyOne = false;
	private boolean isActivated = false;
	
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
		double status = Math.random();
		if(status < 1)
			isAnyOne = true;
		else
			isAnyOne = false;
		
		return isAnyOne;
	}

	@Override
	public boolean test() {
		return true;
	}

}
