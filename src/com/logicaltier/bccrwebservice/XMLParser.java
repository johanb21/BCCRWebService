package com.logicaltier.bccrwebservice;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import android.util.Log;

public class XMLParser {

	//Investigar httpclient
	private XMLParser() { }

	public static String parseDocument(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		String result = IndicadorEconomico.ERROR_CONEXION;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			Document doc = builder.parse(is);
			Node node = doc.getElementsByTagName("NUM_VALOR").item(0);
			result = node.getFirstChild().getNodeValue();
			if(!result.equals(IndicadorEconomico.ERROR_CONEXION)) {
				result = result.substring(0, 6);
			}
		} catch (Exception e) {
			Log.e("parseDocument e", e.toString());
		}
		return result;
	}
}
