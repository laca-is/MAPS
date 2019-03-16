package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import artifacts.A_DriverConnection;
import cartago.Artifact;
import model.InboxMsg;
import model.Message;
import model.NetworkConfig;
import model.RDriver;

public class T_DriverConnection implements Runnable {

	private ServerSocket socketServer;
	private Socket socketClient;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	public A_DriverConnection feedbackArtifact;

	public T_DriverConnection(Artifact feedbackArtifact) {
		this.feedbackArtifact = (A_DriverConnection) feedbackArtifact;
	}

	private boolean startServer() {
		try {
			socketServer = new ServerSocket(NetworkConfig.SERVER_PORT.getValue());
			System.out.println("Server is working through port: " + socketServer.getLocalPort());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void acceptConnections() {
		
			try {
				socketClient = socketServer.accept();
				System.out.println(
						"Somenthing got here from " + socketClient.getInetAddress() + ":" + socketClient.getPort());
				inputStream = new ObjectInputStream(socketClient.getInputStream());
				Message msg = (Message) inputStream.readObject();
				notifyFeedbackArtifact(socketClient.getOutputStream(),msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	public void sendMessage(RDriver rDriver, Message msg) {
		try{
			outputStream = new ObjectOutputStream(rDriver.getdOutput());
			outputStream.writeObject(msg);
			outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void notifyFeedbackArtifact(OutputStream senderAddress, Message msg) {		
		feedbackArtifact.bufferMessages.add(new InboxMsg(msg, senderAddress));
	}

	public void run() {
		if (this.startServer()) {
			while (true) {
				this.acceptConnections();
			}
		} else {
			System.out.println("Problem with the server!");
		}
	}

}
