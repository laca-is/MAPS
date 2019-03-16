package model;

import java.io.OutputStream;

public class InboxMsg {
	
	private Message message;
	private OutputStream senderAddress;
	
	public InboxMsg(Message message, OutputStream senderAddress) {
		this.message = message;
		this.senderAddress = senderAddress;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public OutputStream getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(OutputStream senderAddress) {
		this.senderAddress = senderAddress;
	}
	
	
	
	

}
