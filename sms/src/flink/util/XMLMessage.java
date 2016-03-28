package flink.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/*
 * XML报文类
 */
public class XMLMessage {
	private static final Logger logger = Logger.getLogger(XMLMessage.class);

	public static Element getField(Element parent, String fieldName) {
		Collection fields = getFields(parent, fieldName);
		Iterator iter = fields.iterator();
		if (iter.hasNext()) {
			return (Element) iter.next();
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static Collection getFields(Element parent, String fieldName) {
		Collection result = new ArrayList();
		NodeList childs = parent.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			if (childs.item(i) instanceof Element) {
				Element child = (Element) childs.item(i);
				if (child.getNodeName().equals(fieldName)) {
					result.add(child);
				}
			}
		}
		return result;
	}

	public static String getFieldValue(Element parent, String fieldName) {
		Element field = getField(parent, fieldName);
		if (field == null)
			return null;
		Text text = getTextNode(field);
		if (text == null)
			return "";
		return text.getNodeValue().trim();
	}

	public static Text getTextNode(Element element) {
		NodeList list = element.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node instanceof Text) {
				return (Text) node;
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		String msg = "<?xml version=\"1.0\" encoding=\"GB2312\"?>"
				+ "<Message>" + " <MsgName>Order</MsgName>"
				+ " <SeqNo>1234</SeqNo>" + " <Sender>XXXXXXXXXXXX</Sender>"
				+ " <MobileNo>13828838899</MobileNo>"
				+ " <Amount>120.00</Amount>"
				+ " <TimesTamp>20060801231020</TimesTamp>"
				+ " <Signature>1234ABCD</Signature>" + "</Message>";
		XMLMessage xmlMsg = XMLMessage.parse(msg.getBytes("GBK"));
		// String msg1 = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
		// "<Response>" +
		// "<Signature>" +
		// "abcdefgh" +
		// "</Signature>" +
		// "<Timestamp>2006-06-02 10:05:28" +
		// "</Timestamp>" +
		// "</Response>";
		// XMLMessage xmlMsg1 = XMLMessage.parse(msg1.getBytes("GBK"));

		// xmlMsg.addElement(XMLMessage.getField(xmlMsg.root(), "Timestamp"),
		// xmlMsg1.root());
		Element root = xmlMsg.root();
		root.setAttribute("test", "test");

		System.out.println(xmlMsg.toString());
	}

	public static String nodeToString(Node node, boolean indent)
			throws TransformerConfigurationException, TransformerException {
		StringWriter sout = new StringWriter();
		StreamResult result = new StreamResult(sout);
		DOMSource domSource = new DOMSource(node);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		Properties properties = t.getOutputProperties();

		/*
		 * 使用GBK，会导致输出的汉字变成类似&#26417;&#38229;&#22522;的代码
		 * 所以采用GB2312输出XML报文，然后再将GB2312替换成GBK
		 */
		properties.setProperty(OutputKeys.ENCODING, "GB2312");

		// 缩进输出
		properties.setProperty(OutputKeys.INDENT, indent ? "yes" : "no");

		t.setOutputProperties(properties);
		t.transform(domSource, result);

		String ret = sout.toString();
		// 将GB2312替换为GBK
		int ind = ret.indexOf("GB2312");
		StringBuffer buffer = new StringBuffer(ret.length());
		buffer.append(ret.substring(0, ind));
		buffer.append("GBK");
		buffer.append(ret.substring(ind + 6));
		ret = new String(buffer);
		return ret;
	}

	public static XMLMessage parse(byte[] msg) {
		try {
			DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = bf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(msg));
			XMLMessage result = new XMLMessage();
			result.xmlDoc = doc;
			return result;
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	public Document xmlDoc;

	protected XMLMessage() {
	}

	public XMLMessage(String name) {
		try {
			DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = bf.newDocumentBuilder();
			xmlDoc = db.newDocument();
			Element root = xmlDoc.createElement(name);
			xmlDoc.appendChild(root);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	// child可以来自于另一个XML报文
	public void addElement(Element parent, Element child) {
		String childName = child.getTagName();
		Text textNode = getTextNode(child);
		String value;
		if (textNode == null) {
			value = "";
		} else {
			value = textNode.getNodeValue();
		}
		Element newChild = addField(parent, childName, value);

		NodeList nodeList = child.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				addElement(newChild, (Element) node);
			}
		}
	}

	public Element addField(Element parent, String fieldName, String fieldValue) {
		if (fieldValue == null) {
			fieldValue = "";
		}
		Element field = xmlDoc.createElement(fieldName);
		Text value = xmlDoc.createTextNode(fieldValue);
		field.appendChild(value);
		parent.appendChild(field);
		return field;
	}

	public Date getFieldDateValue(Element parent, String fieldName,
			String dateFormat) throws ParseException {
		String value = getFieldValue(parent, fieldName);
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.parse(value);
	}

	public Double getFieldDoubleValue(Element parent, String fieldName) {
		String value = getFieldValue(parent, fieldName);
		return Double.valueOf(value);
	}

	public Integer getFieldIntValue(Element parent, String fieldName) {
		String value = getFieldValue(parent, fieldName);
		return Integer.valueOf(value);
	}

	public Long getFieldLongValue(Element parent, String fieldName) {
		String value = getFieldValue(parent, fieldName);
		return Long.valueOf(value);
	}

	@SuppressWarnings("unchecked")
	public Vector getFields() {
		Vector vet = null;
		Element element = null;
		try {
			vet = new Vector();
			element = root();
			String strNodeName = (element.getNodeName()).toUpperCase();
			if (strNodeName.equals("MESSAGE")) {
				vet.add(Integer.valueOf(1));
				vet.add(putListItem(element));
			} else {
				throw new Exception("报文格式错误：报文根节点名称错误");
			}
		} catch (Exception e) {
			vet.clear();
			vet.add(new Integer(0));
			logger.debug(e, e);
		}
		return vet;
	}

	public String getName() {
		if (xmlDoc == null)
			return null;
		Element root = xmlDoc.getDocumentElement();
		if (root == null)
			return null;
		return root.getTagName();
	}

	public String getNodeValue(Element field) {
		if (field == null)
			return "";
		Text text = getTextNode(field);
		if (text == null)
			return "";
		return text.getNodeValue();
	}

	@SuppressWarnings("unchecked")
	public Hashtable putListItem(Element node) {
		Hashtable hash = null;
		NodeList childs = null;
		try {
			hash = new Hashtable();
			if (node == null) {
				return null;
			}
			childs = node.getChildNodes();
			for (int i = 0; i < childs.getLength(); i++) {
				if (childs.item(i) instanceof Element) {
					Element child = (Element) childs.item(i);
					String strNodeName = child.getNodeName();
					String strNodeValue = getNodeValue(child);
					if (!hash.containsKey(strNodeName)) {
						hash.put(strNodeName, strNodeValue.trim());
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return hash;
	}

	public Element root() {
		return xmlDoc.getDocumentElement();
	}

	public void setFieldValue(Element parent, String fieldName,
			String fieldValue) {
		Collection fields = getFields(parent, fieldName);
		Iterator iter = fields.iterator();
		if (iter.hasNext()) {
			Element field = (Element) iter.next();
			Text textNode = xmlDoc.createTextNode(fieldValue);
			Text oldTextNode = getTextNode(field);
			field.replaceChild(textNode, oldTextNode);
		} else {
			addField(parent, fieldName, fieldValue);
		}
	}

	@Override
	public String toString() {
		return toString(true);
	}

	public String toString(boolean indent) {
		try {
			return nodeToString(xmlDoc, indent);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return null;
	}

}
