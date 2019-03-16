// CArtAgO artifact code for project maps_holo

package artifacts;

import java.io.IOException;
import java.util.LinkedList;

import cartago.Artifact;
import cartago.OPERATION;
import control.DBControl;
import model.DriverProfile;
import model.MSG_Type;
import model.Message;
import model.PSPreferences;
import model.PSRequest;

public class A_Simulation extends Artifact {

	public LinkedList<Message> bufferMessages;
	private PSRequest psRequest;
	private String driver;
	
	void init() {
		bufferMessages = new LinkedList<>();
		
		
	}

	@OPERATION
	public void requestPS(String driverName, double price, double psWeight, int time, double timeWeight) {
		PSPreferences dPref = new PSPreferences(price, psWeight, time, timeWeight);
		psRequest = new PSRequest(new DriverProfile(0, driverName, dPref), dPref);
		Message msg = new Message(MSG_Type.REQUEST_PS, psRequest);
		bufferMessages.add(msg);
	}

	@OPERATION
	public void requestLeavePS(String driverName, String psName) {
		Message msg = new Message(MSG_Type.LEAVING_PS, driverName);
		bufferMessages.add(msg);
	}

	@OPERATION
	public void checkNewRequests() throws IOException {
		if (this.bufferMessages.size() > 0) {
			for (Message msg : bufferMessages) {
				switch (msg.getHeader()) {
				case LEAVING_PS:
					driver = (String) msg.getContent();
					if (DBControl.isOn(driver)) {
						DBControl.getDriver(driver).setdOutput(null);
						signal("isLeavingRequest", driver);
					}
					break;
				case REQUEST_PS:
					psRequest = (PSRequest) msg.getContent();
					if (!DBControl.isOn(psRequest.getdProfile().getDriverName())) {
						signal("isRequest", psRequest.getdProfile().getDriverName(),
								psRequest.getdPreferences().getPsPrice(), psRequest.getdPreferences().getPriceWeight(),
								psRequest.getdPreferences().getPsTime(), psRequest.getdPreferences().getTimeWeight(),
								null);
					}
					break;

				default:
					break;
				}
			}
			clearBufferMessages();
		}
	}

	private void clearBufferMessages() {
		this.bufferMessages.clear();
	}

}
