package stokic.cli;

import org.apache.commons.cli.*;

/**
 * Kommandozeilen Argumente werden im GNU Style geparst.
 * 
 * @author Stokic Stefan
 * @version 1.1
 */
public class CLIparser {

	private String[] args;
	private Options options;

	private String serverOrClient;
	private String ip = "localhost";
	private int port = 4445;
	private String filePath;
	private boolean indentFix = false,
			remAuhtor = false,
			remPackage = false,
			remJSwhitespaces = false;
	private String xPath;
	private boolean xmlAddAuthor = false;

	private boolean enoughArgs = true;

	public CLIparser(String[] args){

		this.args = args;
		this.options = new Options();

		Option serverOrClient = OptionBuilder.withArgName("Server/Client")
				.hasArg()
				.withDescription("Server/Client. -sc server oder -sc client. Falls -sc server gewaehlt wird, kann man -p zusaetzlich verwenden um den Port zu setzen wo der Server läuft.")
				.isRequired(true)
				.create("sc");
		Option hostname = OptionBuilder.withArgName("Hostname")
				.hasArg()
				.withDescription("Hostname des Servers. Standard: localhost")
				.isRequired(false)
				.create("h");
		Option port = OptionBuilder.withArgName("Port")
				.hasArg()
				.withDescription("Port des Hostnames. Standard: 4445")
				.isRequired(false)
				.create("p");
		Option filePath = OptionBuilder.withArgName("Datei Pfad")
				.hasArg()
				.isRequired(false)
				.withDescription("Pfad der Datei.")
				.create("f");
		Option indentFix = OptionBuilder.withArgName("Einruekung")
				.hasArg()
				.withDescription("Einrueckung korriegieren. true/false Standart: false")
				.isRequired(false)
				.create("ic");
		Option remAuthor = OptionBuilder.withArgName("Author")
				.hasArg()
				.withDescription("Author von Kommentaren entfernen. true/false Standart: false")
				.isRequired(false)
				.create("ra");
		Option remPackage = OptionBuilder.withArgName("Package")
				.hasArg()
				.isRequired(false)
				.withDescription("Package aus der Datei entfernen. true/false Standart: false")
				.create("rp");
		Option remJSwhitespaces = OptionBuilder.withArgName("Whitespaces")
				.hasArg()
				.isRequired(false)
				.withDescription("JS Whitespaces entfernen. true/false Standart: false")
				.create("rw");
		Option xPath = OptionBuilder.withArgName("XPath")
				.hasArg()
				.withDescription("XPath Ausdruck. Dient zum uebertragen von Daten die mithilfe dieses XPaths ausdrucks angegeben werden zu übertragen.")
				.isRequired(false)
				.create("xp");
		Option xmlAddAuthor = OptionBuilder.withArgName("Tag author")
				.hasArg()
				.withDescription("Fuegt jedem Tag das Attribut 'author' mit eurem OS Namen. true/false Standart: false")
				.isRequired(false)
				.create("xaa");


		this.options.addOption(serverOrClient);
		this.options.addOption(hostname);
		this.options.addOption(port);
		this.options.addOption(filePath);
		this.options.addOption(indentFix);
		this.options.addOption(remAuthor);
		this.options.addOption(remPackage);
		this.options.addOption(remJSwhitespaces);
		this.options.addOption(xPath);
		this.options.addOption(xmlAddAuthor);
	}

	/**
	 * Methode die die Argumente parst
	 */
	public void parse(){

		GnuParser parser = new GnuParser();

		try{

			CommandLine line = parser.parse(this.options, this.args);

			if(line.hasOption("sc")) {

				if(line.getOptionValue("sc").equals("server") || line.getOptionValue("sc").equals("client"))
					serverOrClient = line.getOptionValue("sc");
			}else {

				enoughArgs = false;
				this.help();
			}

			if(line.hasOption("h"))
				this.ip = line.getOptionValue("h");

			if(line.hasOption("p"))
				this.port = Integer.parseInt(line.getOptionValue("p"));

			if(line.getOptionValue("sc").equals("client")) {
				
				if(line.hasOption("f")) {
					
					this.filePath = line.getOptionValue("f");
				} else {
					
					enoughArgs = false;
					this.help();
				}
			}

			if(line.hasOption("ic"))
				this.indentFix = Boolean.valueOf(line.getOptionValue("ic"));

			if(line.hasOption("ra"))
				this.remAuhtor = Boolean.valueOf(line.getOptionValue("ra"));

			if(line.hasOption("rp"))
				this.remPackage = Boolean.valueOf(line.getOptionValue("rp"));

			if(line.hasOption("rw"))
				this.remJSwhitespaces = Boolean.valueOf(line.getOptionValue("rw"));
			
			if(line.hasOption("xp"))
				this.xPath = line.getOptionValue("xp");
			
			if(line.hasOption("xaa"))
				this.xmlAddAuthor = Boolean.valueOf(line.getOptionValue("xaa"));

		}catch(ParseException e){

			this.help();
			enoughArgs = false;
		}
	}

	/**
	 * Methode die eine Hilfestellung zeigt
	 */
	public void help(){

		HelpFormatter hf = new HelpFormatter();
		hf.printHelp("Filesharing", this.options);
	}

	public boolean getIsEnoughArgs() {

		return enoughArgs;
	}

	public String getServerOrClient() {
		return serverOrClient;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getFilePath() {
		return filePath;
	}

	public boolean isIndentFix() {
		return indentFix;
	}

	public boolean isRemAuhtor() {
		return remAuhtor;
	}

	public boolean isRemPackage() {
		return remPackage;
	}

	public boolean isRemJSwhitespaces() {
		return remJSwhitespaces;
	}

	public String getxPath() {
		return xPath;
	}

	public boolean isXmlAddAuthor() {
		return xmlAddAuthor;
	}
}