package control;

import java.util.HashMap;
import java.util.LinkedList;

import model.DriverProfile;
import model.PSpace;
import model.RDriver;
import model.Sector;

public class DBControl {

	private static HashMap<String, RDriver> hashDrivers = new HashMap<String, RDriver>();	
	private static HashMap<String, PSpace> hashPSpaces = new HashMap<String, PSpace>();
	private static HashMap<String, Sector> hashSectors = new HashMap<String,Sector>();
	
	private static LinkedList<String> queueDrivers = new LinkedList<>();
	
	public static String driversInQueue(){
		return queueDrivers.toString();
	}
	
	public static void insertSector(String sectorName, Sector sector){
		hashSectors.put(sectorName, sector);
	}
	
	public static HashMap<String, Sector> getHashSectors(){
		return hashSectors;
	}
	
	public static HashMap<String, PSpace> getHashPSpaces(){
		return hashPSpaces;
	}
	
	public static Sector getSector(String sectorName){
		return hashSectors.get(sectorName);
	}
	
	public static int numDriversinQueue(){
		return queueDrivers.size();
	}
	 
	public static void addPSpace(PSpace ps){
		hashPSpaces.put(ps.getAgentName(), ps);
	}
	
	public static PSpace getPSpace(String psName){
		return hashPSpaces.get(psName);
	}

	public static void addCurrentDriver(RDriver driver) {
		hashDrivers.put(driver.getdProfile().getDriverName(), driver);
	}

	public static void removeDriver(String dName) {
		hashDrivers.remove(dName);
	}
	
	public static RDriver getDriver(String dName){
		return hashDrivers.get(dName);
	}

	public static boolean isOn(String dName) {
		return (hashDrivers.containsKey(dName));
	}

	
	public static boolean isOn(DriverProfile dProfile) {
		return hashDrivers.containsKey(dProfile.getDriverName());
				
	}

	public static boolean isEmptyQueue() {
		return queueDrivers.isEmpty();
	}

	public static void addIntoQueue(String dName) {
		queueDrivers.add(dName);
	}

	public static String pullQueue() {
		String dName = new String(queueDrivers.getFirst());
		queueDrivers.removeFirst();
		return dName;
	}

}
