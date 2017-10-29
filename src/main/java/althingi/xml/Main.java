package althingi.xml;

import java.io.IOException;

import org.apache.http.client.fluent.Request;

public class Main {

	public static void main( String[] args ) {
		try {
			String xmlString = Request
					.Get( Sources.URL_ÞINGFLOKKAR )
					.execute()
					.returnContent()
					.asString();

			ImportÞingflokkar.Þingflokkar result = XMLUtil.unmarshal( xmlString, ImportÞingflokkar.Þingflokkar.class );

			result.þingflokkar.forEach( þingflokkur -> {
				System.out.println( þingflokkur );
			} );
		}
		catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}
}