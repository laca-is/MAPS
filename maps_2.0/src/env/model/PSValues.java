package model;

public enum PSValues {
	
	MIN_TIME(15),
	MAX_TIME(360),
	
	MIN_PRICE(2),
	MAX_PRICE(12),
	
	FREE('F'),
	OCCUPIED('O'),
	NEGOCIATION('N'),
	RESERVED('R'),
	
	DRIVERTHRESHOLD(6),
	
	TIMEOUTROUND(10000),
	MAXROUNDS(5);
	
	private final int value;
	
		
	PSValues(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	
}
