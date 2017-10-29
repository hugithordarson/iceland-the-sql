package althingi.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.http.client.fluent.Request;
import org.xml.sax.InputSource;

public class ATClient {

	private static final String URL_ÞINGFLOKKAR = "https://www.althingi.is/altext/xml/thingflokkar/";
	private static final String URL_ÞINGMENN = "https://www.althingi.is/altext/xml/thingmenn/";
	private static final String URL_NEFNDIR = "https://www.althingi.is/altext/xml/nefndir/";

	public static void main( String[] args ) {
		try {
			String xmlString = Request
					.Get( URL_ÞINGFLOKKAR )
					.execute()
					.returnContent()
					.asString();

			unmarshal( xmlString );
		}
		catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	private static void unmarshal( String xmlString ) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance( Þingflokkar.class );
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Þingflokkar result = (Þingflokkar)jaxbUnmarshaller.unmarshal( new InputSource( new StringReader( xmlString ) ) );

			result.þingflokkar.forEach( þingflokkur -> {
				System.out.println( þingflokkur );
			} );
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	@XmlRootElement
	public static class Þingflokkar {

		@XmlElement( name = "þingflokkur" )
		public List<Þingflokkur> þingflokkar = new ArrayList<>();

		public static class Þingflokkur {

			@XmlAttribute
			public String id;

			@XmlJavaTypeAdapter( value = StringTrimAdapter.class, type = String.class )
			public String heiti;
			public Skammstafanir skammstafanir;
			public Tímabil tímabil;

			@Override
			public String toString() {
				return "Þingflokkur [id=" + id + ", heiti=" + heiti + ", skammstafanir=" + skammstafanir + ", tímabil=" + tímabil + "]";
			}
		}

		public static class Skammstafanir {
			public String stuttskammstöfun;
			public String löngskammstöfun;

			@Override
			public String toString() {
				return "Skammstafanir [stuttskammstöfun=" + stuttskammstöfun + ", löngskammstöfun=" + löngskammstöfun + "]";
			}
		}

		public static class Tímabil {
			public int fyrstaþing;
			public int síðastaþing;

			@Override
			public String toString() {
				return "Tímabil [fyrstaþing=" + fyrstaþing + ", síðastaþing=" + síðastaþing + "]";
			}
		}

		@Override
		public String toString() {
			return "Þingflokkar [þingflokkar=" + þingflokkar + "]";
		}
	}

	public static class StringTrimAdapter extends XmlAdapter<String, String> {

		@Override
		public String unmarshal( String v ) throws Exception {
			if( v == null ) {
				return null;
			}
			return v.trim();
		}

		@Override
		public String marshal( String v ) throws Exception {
			if( v == null ) {
				return null;
			}
			return v.trim();
		}
	}
}