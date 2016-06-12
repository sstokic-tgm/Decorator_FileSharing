package stokic.decorator;

import Util.FileHandler;

/**
 * This class removes the author from the comment using regular expressions
 * 
 * @author Stefan Stokic
 * @version 0.1
 */
public class AuthorRemove extends FileDecorator {

	private FileDecorator decorator;
	
	public AuthorRemove(FileDecorator decorator) {
		
		this.decorator = decorator;
	}

	/**
	 * The concrete class extends FileDecorator therefore we implement the decorate() method here.
	 */
	@Override
	public FileHandler decorate() {
		
		FileHandler fHandler = decorator.decorate();
		
		String replacedText = new String(fHandler.getFContent());
		replacedText = replacedText.replaceAll("\\s*\\*+\\s*(@author){1}(\\s+\\w+)+", "");
		
		fHandler.setFContent(replacedText.getBytes());
		
		return fHandler;
	}
}