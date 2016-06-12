package stokic.decorator;

import java.io.ByteArrayInputStream;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

import Util.FileHandler;

/**
 * This class fixes indent using a third-person library
 * 
 * @author Stefan Stokic
 * @version 0.1
 */
public class IndentCorrection extends FileDecorator {

	private FileDecorator decorator;
	
	public IndentCorrection(FileDecorator decorator) {
		
		this.decorator = decorator;
	}
	
	/**
	 * The concrete class extends FileDecorator therefore we implement the decorate() method here.
	 */
	@Override
	public FileHandler decorate() {
		
		FileHandler fHandler = decorator.decorate();
		
		byte[] encoded = fHandler.getFContent();
		
		try {
			
			CompilationUnit cu = JavaParser.parse(new ByteArrayInputStream(encoded));
			fHandler.setFContent(cu.toString().getBytes());
			
			return fHandler;
			
		} catch (ParseException e) {
			
			System.out.println("Error while correcting indent!");
		}
		
		return fHandler;
	}
}