package stokic.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is setting up the connection and starts the ServerListener which listens to incoming connections
 * 
 * @author Stefan Stokic
 * @version 0.2
 */
public class Server implements Connection {

	protected String m_sHostname;
	protected int m_iPort;
	
	protected static Server serverInstance = null;
	
	protected ServerSocket m_sSocket = null;
	
	protected boolean m_bOnline;
	
	protected Server() {}
	
	public Server(String hostname, int port) {
		
		setupConnection(hostname, port);
	}
	
	public void setOnlineStatus(boolean status) {
		
		this.m_bOnline = status;
	}
	
	public boolean getOnlineStatus() {
		
		return this.m_bOnline;
	}
	
	@Override
	public void setupConnection(String hostname, int port) {
		
		this.m_sHostname = hostname;
		this.m_iPort = port;
		
		serverInstance = this;
	}

	
	/**
	 * Here we start the server connection and start the ServerListener
	 */
	@Override
	public void startConnection() throws IOException {
		
		try {
			
			m_sSocket = new ServerSocket(m_iPort);
			
			System.out.println("Server started!");
			
			Socket socket = m_sSocket.accept();
			
			m_bOnline = true;
			
			System.out.println("Client accepted!");
			
			Thread serverListenerThread = new Thread(new ServerListener(socket, m_iPort));
			serverListenerThread.start();
			
		
		}finally {
			
			m_bOnline = false;
			
			if(m_sSocket != null)
				m_sSocket.close();
		}
	}
	
	/**
	 * Cause the class is singleton we need to have the ability to return the instance of it
	 * 
	 * @return instance of Server class
	 */
	public static Server getInstance() {
		
		if(serverInstance == null)
			serverInstance = new Server();
		
		return serverInstance;
	}
}