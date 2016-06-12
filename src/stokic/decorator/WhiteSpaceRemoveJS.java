package stokic.decorator;

import Util.FileHandler;

/**
 * This class removes removes whitespaces and comments using regular expressions
 * 
 * @author Stefan Stokic
 * @version 0.1
 */
public class WhiteSpaceRemoveJS extends FileDecorator {

	private FileDecorator decorator;
	
	public WhiteSpaceRemoveJS(FileDecorator decorator) {
		
		this.decorator = decorator;
	}
	
	/**
	 * The concrete class extends FileDecorator therefore we implement the decorate() method here.
	 */
	@Override
	public FileHandler decorate() {
		
		FileHandler fHandler = decorator.decorate();
		
		String replacedText = new String(fHandler.getFContent());
		replacedText = replacedText.replaceAll("//.*?\n",""); // remove comments to avoid problems with removed whitespaces later(at the point there is a comment, everything would be still in a comment)
															  // so first we need to remove the comments and then the whitespaces
		replacedText = replacedText.replaceAll("\\s+", "");
		
		fHandler.setFContent(replacedText.getBytes());
		
		return fHandler;
	}
}