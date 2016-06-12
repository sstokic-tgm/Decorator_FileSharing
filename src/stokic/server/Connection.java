package stokic.server;

import java.io.IOException;

/**
 * Ein Interface fuer den Server. Beinhaltet das Vorbereiten der Server Connection(hostname, port) und 
 * das Starten des Servers.
 * 
 * @author Stefan Stokic
 * @version 0.1
 */
public interface Connection {

	/**
	 * @param hostname der Name des Hosts, auf dem der MulticastSocket laufen soll
	 * @param port der Port, auf dem der Socket laufen soll
	 * 
	 * Dient zum Herstellen der Verbindung zum Socket.
	 */
	public void setupConnection(String hostname, int port);
	
	/**
	 * Dient zum Starten des Servers
	 * @throws IOException 
	 */
	public void startConnection() throws IOException;
}