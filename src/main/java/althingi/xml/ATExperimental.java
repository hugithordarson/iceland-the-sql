package althingi.xml;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ATExperimental {

	public static void parserXMLString( String xmlString ) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse( new InputSource( new StringReader( xmlString ) ) );

			// optional, but recommended
			// read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println( "Root element : " + doc.getDocumentElement().getNodeName() );

			NodeList congressmen = doc.getElementsByTagName( "þingflokkur" );

			System.out.println( "----------------------------" );

			for( int temp = 0 ; temp < congressmen.getLength() ; temp++ ) {

				Node nNode = congressmen.item( temp );

				System.out.println( "\nCurrent Element :" + nNode.getNodeName() );

				if( nNode.getNodeType() == Node.ELEMENT_NODE ) {
					Element eElement = (Element)nNode;
					System.out.println( "Staff id : " + eElement.getAttribute( "id" ) );
					System.out.println( "Heiti : " + eElement.getElementsByTagName( "heiti" ).item( 0 ).getTextContent() );
				}
			}
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
}