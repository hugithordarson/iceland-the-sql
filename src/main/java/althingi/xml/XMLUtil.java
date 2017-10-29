package althingi.xml;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.client.fluent.Request;
import org.xml.sax.InputSource;

public class XMLUtil {

	public static <E> E xmlToResult( String xmlString, Class<E> resultClass ) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance( resultClass );
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (E)jaxbUnmarshaller.unmarshal( new InputSource( new StringReader( xmlString ) ) );
		}
		catch( Exception e ) {
			throw new RuntimeException( e );
		}
	}

	public static <E> E urlToResult( String urlString, Class<E> resultClass ) {
		try {
			String xmlString = Request
					.Get( urlString )
					.execute()
					.returnContent()
					.asString();

			JAXBContext jaxbContext = JAXBContext.newInstance( resultClass );
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (E)jaxbUnmarshaller.unmarshal( new InputSource( new StringReader( xmlString ) ) );
		}
		catch( Exception e ) {
			throw new RuntimeException( e );
		}
	}

}
