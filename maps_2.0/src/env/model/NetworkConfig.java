package model;

public enum NetworkConfig {
	
	SERVER_PORT(10000);
	
	private final int value;
	
	NetworkConfig(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
