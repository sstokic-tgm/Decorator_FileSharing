package Util;

import java.io.Serializable;

/**
 * Class which handles the file data. You need to set each attribute of the class and send the class as an
 * object over the network.
 * On the end point you read the object again and use the attributes for your individual needs.
 * 
 * @author Stefan Stokic
 * @version 0.3
 */
public class FileHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected String m_sDstDir;
	protected String m_sSrcDir;
	protected String m_sFName;
	protected long m_lFSize;
	protected byte[] m_arrFContent;
	
	protected String m_sXPath;
	
	protected String m_sReturnMsg;
	protected Modification[] m_enumModification;
	
	
	public FileHandler() {}


	public String getDstDir() {
		
		return m_sDstDir;
	}


	public void setDstDir(String dstDir) {
		
		this.m_sDstDir = dstDir;
	}


	public String getSrcDir() {
		
		return m_sSrcDir;
	}


	public void setSrcDir(String srcDir) {
		
		this.m_sSrcDir = srcDir;
	}


	public String getFName() {
		
		return m_sFName;
	}


	public void setFName(String fName) {
		
		this.m_sFName = fName;
	}


	public long getFSize() {
		
		return m_lFSize;
	}


	public void setFSize(long fSize) {
		
		this.m_lFSize = fSize;
	}


	public byte[] getFContent() {
		
		return m_arrFContent;
	}


	public void setFContent(byte[] fContent) {
		
		this.m_arrFContent = fContent;
	}


	public String getXPath() {
		
		return m_sXPath;
	}
	
	public void setXPath(String xPath) {
		
		this.m_sXPath = xPath;
	}
	
	public String getReturnMsg() {
		
		return m_sReturnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		
		this.m_sReturnMsg = returnMsg;
	}
	
	public Modification[] getModification() {
		
		return m_enumModification;
	}
	
	public void setModification(Modification ...modification) {
		
		this.m_enumModification = modification;
	}
}