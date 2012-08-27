package urn.ebay.apis.eBLBaseComponents;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import java.io.FileInputStream;
import java.io.StringReader;
import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Information about Coupled Payment transactions. 
 */
public class CoupledPaymentInfoType{


	/**
	 * ID received in the Coupled Payment Request
	 */ 
	private String CoupledPaymentRequestID;

	/**
	 * ID that uniquely identifies this CoupledPayment. Generated
	 * by PP in Response
	 *@Required	 
	 */ 
	private String CoupledPaymentID;

	

	/**
	 * Default Constructor
	 */
	public CoupledPaymentInfoType (){
	}	

	/**
	 * Getter for CoupledPaymentRequestID
	 */
	 public String getCoupledPaymentRequestID() {
	 	return CoupledPaymentRequestID;
	 }
	 
	/**
	 * Setter for CoupledPaymentRequestID
	 */
	 public void setCoupledPaymentRequestID(String CoupledPaymentRequestID) {
	 	this.CoupledPaymentRequestID = CoupledPaymentRequestID;
	 }
	 
	/**
	 * Getter for CoupledPaymentID
	 */
	 public String getCoupledPaymentID() {
	 	return CoupledPaymentID;
	 }
	 
	/**
	 * Setter for CoupledPaymentID
	 */
	 public void setCoupledPaymentID(String CoupledPaymentID) {
	 	this.CoupledPaymentID = CoupledPaymentID;
	 }
	 


	private  boolean isWhitespaceNode(Node n) {
		if (n.getNodeType() == Node.TEXT_NODE) {
			String val = n.getNodeValue();
			return val.trim().length() == 0;
		} else if (n.getNodeType() == Node.ELEMENT_NODE ){
			return (n.getChildNodes().getLength() == 0);
		} else {
			return false;
		}
	}
	
	private String convertToXML(Node n){
		String name = n.getNodeName();
		short type = n.getNodeType();
		if (Node.CDATA_SECTION_NODE == type) {
			return "<![CDATA[" + n.getNodeValue() + "]]&gt;";
		}
		if (name.startsWith("#")) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<").append(name);
		NamedNodeMap attrs = n.getAttributes();
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				Node attr = attrs.item(i);
				sb.append(" ").append(attr.getNodeName()).append("=\"").append(attr.getNodeValue()).append("\"");
			}
		}
		String textContent = null;
		NodeList children = n.getChildNodes();
		if (children.getLength() == 0) {
			if (((textContent = n.getTextContent())) != null && (!"".equals(textContent))) {
				sb.append(textContent).append("</").append(name).append(">");
			} else {
				sb.append("/>");
			}
		} else {
			sb.append(">");
			boolean hasValidChildren = false;
			for (int i = 0; i < children.getLength(); i++) {
				String childToString = convertToXML(children.item(i));
				if (!"".equals(childToString)) {
					sb.append(childToString);
					hasValidChildren = true;
				}
			}
			if (!hasValidChildren && ((textContent = n.getTextContent()) != null)) {
				sb.append(textContent);
			}
			sb.append("</").append(name).append(">");
		}
		return sb.toString();
	}
	
	public CoupledPaymentInfoType(Object xmlSoap) throws IOException, SAXException, ParserConfigurationException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader((String)xmlSoap));
		Document document = builder.parse(inStream);
		NodeList nodeList= null;
		
		String xmlString = "";
		if (document.getElementsByTagName("CoupledPaymentRequestID").getLength() != 0) {
			if(!isWhitespaceNode(document.getElementsByTagName("CoupledPaymentRequestID").item(0))) {
				this.CoupledPaymentRequestID = (String)document.getElementsByTagName("CoupledPaymentRequestID").item(0).getTextContent();
			}
		}
	
		if (document.getElementsByTagName("CoupledPaymentID").getLength() != 0) {
			if(!isWhitespaceNode(document.getElementsByTagName("CoupledPaymentID").item(0))) {
				this.CoupledPaymentID = (String)document.getElementsByTagName("CoupledPaymentID").item(0).getTextContent();
			}
		}
	
	}
 
}