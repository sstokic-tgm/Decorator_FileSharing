package stokic.decorator;

import Util.FileHandler;

/**
 * Abstract class which implements the decorator interface
 * 
 * @author Stefan Stokic
 * @version 0.4
 */
public abstract class FileDecorator implements Decorator {

	public abstract FileHandler decorate();
}