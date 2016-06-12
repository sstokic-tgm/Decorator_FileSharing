package stokic.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import Util.FileHandler;
import stokic.decorator.*;

/**
 * Listens to incoming connections and decorate the file we choose as the client
 * 
 * @author Stefan Stokic
 * @version 0.5
 */
public class ServerListener implements Runnable {

	protected int m_iPort;

	protected Socket m_sSocket;
	protected ObjectInputStream m_oisInput;
	protected FileOutputStream m_fosFileOutputStream = null;

	protected FileHandler fileHandler;
	
	protected File outFile;

	public ServerListener(Socket socket, int port) {

		this.m_sSocket = socket;
		this.m_iPort = port;
	}

	@Override
	public void run() {

		try {

			m_oisInput = new ObjectInputStream(m_sSocket.getInputStream());

			FileDecorator decorator;

			while(true) {

				fileHandler = (FileHandler)m_oisInput.readObject(); // we read our send object from the network and cast it to our custom class FileHandler which holds the information of the sent file

				if(!fileHandler.getReturnMsg().equalsIgnoreCase("Error") || fileHandler.getModification() != null) { // we check if the incoming file doesn't have errors

					String out = fileHandler.getDstDir() + fileHandler.getFName();
					if(!new File(fileHandler.getDstDir()).exists())
						new File(fileHandler.getDstDir()).mkdirs();
					
					outFile = new File(out);
					m_fosFileOutputStream = new FileOutputStream(outFile);
					
					decorator = new FileToDecorate(fileHandler); // we create our decorator

					for(int i = 0; i < fileHandler.getModification().length; i++) { // we iterate through the modification list, the client has choosen

						switch(fileHandler.getModification()[i]) // we handle it
						{

						case REMOVE_AUTHOR:

							if(!fileHandler.getFName().endsWith(".js") || !fileHandler.getFName().endsWith(".xml")) { // we check if it isn't a js or xml file

								decorator = new AuthorRemove(decorator);
								fileHandler = decorator.decorate();
							}

							break;

						case INDENT_CORRECTION:

							if(!fileHandler.getFName().endsWith(".js") || !fileHandler.getFName().endsWith(".xml")) { // we check if it isn't a js or xml file

								decorator = new IndentCorrection(decorator);
								fileHandler = decorator.decorate();
							}

							break;

						case JS_REMOVE_WHITESPACES:

							if(fileHandler.getFName().endsWith(".js")) { // we check if it is a js file

								decorator = new WhiteSpaceRemoveJS(decorator);
								fileHandler = decorator.decorate();

							}

							break;

						case REMOVE_PACKAGE:

							if(!fileHandler.getFName().endsWith(".js") || !fileHandler.getFName().endsWith(".xml")) { // we check if it isn't a js or xml file

								decorator = new PackageRemove(decorator);
								fileHandler = decorator.decorate();
							}

							break;
							
						case XML_ADDAUTHOR:
							
							if(fileHandler.getFName().endsWith(".xml")) { // we check if it isn't a js file and if it's a xml file
								
								decorator = new XMLAddAuthor(decorator);
								fileHandler = decorator.decorate();
							}
							
							break;
							
						case XPATH:
							
							if(fileHandler.getFName().endsWith(".xml")) { // we check if it isn't a js file and if it's a xml file
								
								decorator = new XPathData(decorator);
								fileHandler = decorator.decorate();
							}
							
							break;

						default:

							fileHandler = decorator.decorate();

							break;
						}
					}
					
					// write file
					m_fosFileOutputStream.write(fileHandler.getFContent());
					m_fosFileOutputStream.flush();
					m_fosFileOutputStream.close();
				}

				Thread.sleep(20);
			}

		} catch(Exception e) {

			//e.printStackTrace();
		}
	}
}