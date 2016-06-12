package stokic.decorator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import Util.FileHandler;

/**
 * This class adds to every tag the 'author' attribute which holds your name(OS name in this case)
 * 
 * @author Stefan Stokic
 * @version 0.1
 */
public class XMLAddAuthor extends FileDecorator {

	private FileDecorator decorator;

	public XMLAddAuthor(FileDecorator decorator) {

		this.decorator = decorator;
	}

	/**
	 * The concrete class extends FileDecorator therefore we implement the decorate() method here.
	 */
	@Override
	public FileHandler decorate() {

		FileHandler fHandler = decorator.decorate();

		String author = System.getProperty("user.name");

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(fHandler.getFContent()));

			NodeList nodeList = doc.getElementsByTagName("*");
			for(int i = 0; i < nodeList.getLength(); i++) {

				if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {

					Element el = (Element) nodeList.item(i);

					if(el.hasAttribute("author")) {

						String tmp = el.getAttribute("author") + "," + author;
						el.setAttribute("author", tmp);

					} else {

						el.setAttribute("author", author);
					}
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.transform(new DOMSource(doc), new StreamResult(baos));
			
			fHandler.setFContent(baos.toByteArray());

		} catch (SAXException e) {

			System.out.println("Error: " + e.getMessage());

		} catch (IOException e) {

			System.out.println("Error: " + e.getMessage());

		} finally {

			return fHandler;
		}
	}
}