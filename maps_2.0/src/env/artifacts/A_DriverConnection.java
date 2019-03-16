// CArtAgO artifact code for project maps_holo

package artifacts;

import java.util.LinkedList;

import cartago.Artifact;
import cartago.OPERATION;
import control.DBControl;
import control.T_DriverConnection;
import model.InboxMsg;
import model.MSG_Type;
import model.Message;
import model.PSOffer;
import model.PSRequest;
import model.PSpace;
import model.RDriver;

public class A_DriverConnection extends Artifact {

	private T_DriverConnection server;
	private Thread threadServer;
	public LinkedList<InboxMsg> bufferMessages;
	private PSRequest psRequest;
	private String driver;

	void init() {
		bufferMessages = new LinkedList<InboxMsg>();
		server = new T_DriverConnection(this);
		threadServer = new Thread(server);
		threadServer.start();

	}

	@OPERATION
	public void checkNewRequests() {
		if (this.bufferMessages.size() > 0) {
			for (InboxMsg inboxMsg : bufferMessages) {
				switch (inboxMsg.getMessage().getHeader()) {
				case LEAVING_PS:
					driver = (String) inboxMsg.getMessage().getContent();
					if (DBControl.isOn(driver)) {
						DBControl.getDriver(driver).setdOutput(inboxMsg.getSenderAddress());
						signal("isLeavingRequest", driver);
					}
					break;
				case REQUEST_PS:
					psRequest = (PSRequest) inboxMsg.getMessage().getContent();
					if (!DBControl.isOn(psRequest.getdProfile().getDriverName())) {
						signal("isRequest", psRequest.getdProfile().getDriverName(),
								psRequest.getdPreferences().getPsPrice(), psRequest.getdPreferences().getPriceWeight(),
								psRequest.getdPreferences().getPsTime(), psRequest.getdPreferences().getTimeWeight(),
								inboxMsg.getSenderAddress());
					}
					break;					
				case QUEUE_INFO:
					driver = (String) inboxMsg.getMessage().getContent();
					if (DBControl.isOn(driver)) {
						DBControl.getDriver(driver).setdOutput(inboxMsg.getSenderAddress());
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

	@OPERATION
	public void sendPSInfo(String dName, String psName) {
		
		RDriver rDriver = DBControl.getDriver(dName);
		PSpace ps = DBControl.getPSpace(psName);
		PSOffer psOffer = new PSOffer(psName,ps.getContract().getMaxTime(),ps.getContract().getPsPrice(),ps.getSectorName().getAgentName());
		Message msg = new Message(MSG_Type.PS_INFO);
		msg.setContent(psOffer);
		this.sendResponse(rDriver, msg);
	}
	
	@OPERATION
	public void sendPSPaymentInfo(String dName, double finalPrice){
		RDriver rDriver = DBControl.getDriver(dName);
		Message msg = new Message(MSG_Type.PAYMENT_INFO, String.valueOf(finalPrice));
		this.sendResponse(rDriver, msg);
	}
	
	@OPERATION
	public void sendQueueInfo(String dName){
		RDriver rDriver = DBControl.getDriver(dName);
		Message msg = new Message(MSG_Type.QUEUE_INFO);
		msg.setContent("System is full! Inserting you in the Queue!");
		this.sendResponse(rDriver, msg);
	}
	
	@OPERATION
	public void sendUnavailableInfo(String dName, String stMsg){
		RDriver rDriver = DBControl.getDriver(dName);
		Message msg = new Message(MSG_Type.UNAVAILABLE_SYS);
		msg.setContent(stMsg);
		this.sendResponse(rDriver, msg);
	}
	
	private void sendResponse(RDriver rDriver, Message msg){
		server.sendMessage(rDriver, msg);
	}

}
