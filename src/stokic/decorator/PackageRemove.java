package stokic.decorator;

import Util.FileHandler;

/**
 * This class removes the package from the file
 * 
 * @author Stefan Stokic
 * @version 0.1
 */
public class PackageRemove extends FileDecorator {

	private FileDecorator decorator;
	
	public PackageRemove(FileDecorator decorator) {
		
		this.decorator = decorator;
	}
	
	/**
	 * The concrete class extends FileDecorator therefore we implement the decorate() method here.
	 */
	@Override
	public FileHandler decorate() {
		
		FileHandler fHandler = decorator.decorate();
		
		String replacedText = new String(fHandler.getFContent());
		replacedText = replacedText.replaceAll("\\s*(package){1}\\s+[\\w.]+;{1}", "");
		
		fHandler.setFContent(replacedText.getBytes());
		
		return fHandler;
	}
}