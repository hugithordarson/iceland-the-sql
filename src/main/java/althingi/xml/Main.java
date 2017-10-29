package althingi.xml;

import althingi.data.Party;
import althingi.db.Core;

public class Main {

	public static void main( String[] args ) {
		Party p = Core.newContext().newObject( Party.class );
		p.setName( "Smu" );
		p.getObjectContext().commitChanges();

		/*
		ImportÞingflokkar.Þingflokkar result = XMLUtil.urlToResult( Sources.URL_ÞINGFLOKKAR, ImportÞingflokkar.Þingflokkar.class );

		result.þingflokkar.forEach( þingflokkur -> {
			System.out.println( þingflokkur );
		} );
		*/
	}
}