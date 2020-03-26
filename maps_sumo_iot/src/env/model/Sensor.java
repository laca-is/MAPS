package model;

public class Sensor {

	private String agentName; //psName whose belongs
	private boolean isUsing;
	private float oldValue, value;
	
	

	public Sensor(String agentName, boolean isUsing, float oldValue, float value) {
		this.agentName = agentName;
		this.isUsing = isUsing;
		this.oldValue = oldValue;
		this.value = value;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public boolean isUsing() {
		return isUsing;
	}

	public void setUsing(boolean isUsing) {
		this.isUsing = isUsing;
	}

	public float getOldValue() {
		return oldValue;
	}

	public void setOldValue(float oldValue) {
		this.oldValue = oldValue;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.oldValue = this.value;
		this.value = value;
	}

}
