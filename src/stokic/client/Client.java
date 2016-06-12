package stokic.client;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Util.FileHandler;
import Util.Modification;

/**
 * Client class which sends the file as an object over the network
 * 
 * @author Stefan Stokic
 * @version 0.4
 */
public class Client {

	private String ip, file, xpath;
	private int port;
	private Modification[] modis;

	public Client(String ip, int port, String file, Modification ...modis) {

		this.ip = ip;
		this.port = port;
		this.file = file;
		this.modis = modis;
	}

	public Client(String ip, int port, String file, String xpath, Modification ...modis) {

		this.ip = ip;
		this.port = port;
		this.file = file;
		this.modis = modis;
		this.xpath = xpath;
	}

	public void sendFile() throws UnknownHostException, IOException {

		String srcFile = file;

		if(srcFile == null)
			return;

		String fileName = srcFile.substring(srcFile.lastIndexOf("/") + 1, srcFile.length()); // get only the file name

		FileHandler fHandler = new FileHandler();
		fHandler.setFName(fileName);
		fHandler.setSrcDir(srcFile);
		fHandler.setDstDir("./_downloads/");
		
		if(xpath != null && !xpath.isEmpty())
			fHandler.setXPath(xpath);

		File file = new File(srcFile);
		if(file.isFile()) {

			DataInputStream dis = new DataInputStream(new FileInputStream(file));

			long size = (int)file.length();
			byte[] bytes = new byte[(int)size];
			int read = 0;
			int numRead = 0;

			while(read < bytes.length && (numRead = dis.read(bytes, read, bytes.length - read)) >= 0)
				read = read + numRead;

			fHandler.setFSize(size);
			fHandler.setFContent(bytes);
			fHandler.setModification(modis);
			fHandler.setReturnMsg("Succes");
		}else {

			fHandler.setReturnMsg("Error");
		}

		Socket socket = new Socket(ip, port);

		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		outputStream.writeObject(fHandler);

		socket.close();
	}
}