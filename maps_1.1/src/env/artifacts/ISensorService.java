package artifacts;

public interface ISensorService {	
	
	public boolean enable();
	public boolean disable();
	public Object read();
	public boolean test();

}
