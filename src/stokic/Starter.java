package stokic;

import java.io.IOException;
import java.util.Arrays;

import Util.Modification;
import stokic.cli.CLIparser;
import stokic.client.Client;
import stokic.server.Server;

/**
 * Parst Zeilenargumente und started demenstprechend den Server oder Client.
 * 
 * @author Stefan Stokic
 * @version 0.2
 */
public class Starter {

	protected static boolean successfullyParsed = false;

	public static void main(String[] args) {

		CLIparser cli = new CLIparser(args); // pass the command line arguments to the CLIparser
		cli.parse(); // parse the arguments
		successfullyParsed = cli.getIsEnoughArgs(); // check if we have all arguments

		if(successfullyParsed) {

			if(cli.getServerOrClient().equals("server")) {

				Server server = new Server(cli.getIp(), cli.getPort());

				try {
					server.startConnection();

				} catch (IOException e) {

					System.out.println("Fehler beim Erstellen des Servers!");
				}
			} else if(cli.getServerOrClient().equals("client")) {

				Client client;
				Modification[] modis = new Modification[0];

				if(cli.isIndentFix())
					modis = addElement(modis, Modification.INDENT_CORRECTION);
				if(cli.isRemAuhtor())
					modis = addElement(modis, Modification.REMOVE_AUTHOR);
				if(cli.isRemPackage())
					modis = addElement(modis, Modification.REMOVE_PACKAGE);
				if(cli.isRemJSwhitespaces())
					modis = addElement(modis, Modification.JS_REMOVE_WHITESPACES);
				if(cli.isXmlAddAuthor())
					modis = addElement(modis, Modification.XML_ADDAUTHOR);
				if((cli.getxPath() != null && !cli.getxPath().isEmpty())) {
					
					modis = addElement(modis, Modification.XPATH);
					client = new Client(cli.getIp(), cli.getPort(), cli.getFilePath(), cli.getxPath(), modis);
				}else {
					
					client = new Client(cli.getIp(), cli.getPort(), cli.getFilePath(), modis);
				}
				
				try {
					
					client.sendFile();
					
				} catch (IOException e) {
					
					System.out.println("Es ist ein Fehler aufgetreten beim senden der Datei!");
				}
			}
		}
	}

	/**
	 * Helper method to add dynamically Modification array
	 */
	static Modification[] addElement(Modification[] org, Modification added) {
		
		Modification[] result = Arrays.copyOf(org, org.length +1);
		result[org.length] = added;
		return result;
	}
}