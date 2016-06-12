package stokic.decorator;

import Util.FileHandler;

/**
 * Helper class for the decorator principle so the decorator knows with what to work
 * 
 * @author Stefan Stokic
 * @version 0.2
 */
public class FileToDecorate extends FileDecorator {

	private FileHandler fHandler;
	
	public FileToDecorate(FileHandler fHandler) {
		
		this.fHandler = fHandler;
	}
	
	@Override
	public FileHandler decorate() {
		
		return fHandler;
	}
}