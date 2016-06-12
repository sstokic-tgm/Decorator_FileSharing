package stokic.decorator;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import Util.FileHandler;

/**
 * This class sets data which is only requested by the XPath expression
 * 
 * @author Stefan Stokic
 * @version 0.1
 */
public class XPathData extends FileDecorator {

	private FileDecorator decorator;
	
	public XPathData(FileDecorator decorator) {
	
		this.decorator = decorator;
	}
	
	/**
	 * The concrete class extends FileDecorator therefore we implement the decorate() method here.
	 */
	@Override
	public FileHandler decorate() {
		
		FileHandler fHandler = decorator.decorate();
		
		try {
			
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(fHandler.getFContent()));
			
			XPathFactory pathFactory = XPathFactory.newInstance();
			XPath path = pathFactory.newXPath();
			
			XPathExpression expr = path.compile(fHandler.getXPath());
			NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			
			String newContent;
			
			newContent = "Result: \n\n";
			
			for(int i = 0; i < nodeList.getLength(); i++)
				newContent += "[" + i + "]: " + ((Node)nodeList.item(i)).getNodeValue().trim() + "\n";
			
			fHandler.setFContent(newContent.getBytes());
			
		} catch (SAXException e) {
			
			System.out.println("Error: " + e.getMessage());
			
		} catch (IOException e) {
			
			System.out.println("Error: " + e.getMessage());
			
		} finally {
			
			return fHandler;
		}
	}

}