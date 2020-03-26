package control;

import java.util.HashMap;

import model.PSpace;

public class DBControl {

	public static HashMap<String, PSpace> hashPSpaces = new HashMap<String, PSpace>();
	
	public static PSpace getPSpace(String psName){
		return hashPSpaces.get(psName);
	}
	
	

}
