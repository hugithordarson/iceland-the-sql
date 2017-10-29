package althingi.xml;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;

import althingi.data.Party;
import althingi.db.Core;

public class Main {

	public static void main( String[] args ) {

		ImportÞingflokkar.Þingflokkar result = XMLUtil.urlToResult( Sources.URL_ÞINGFLOKKAR, ImportÞingflokkar.Þingflokkar.class );

		ObjectContext oc = Core.newContext();

		result.þingflokkar.forEach( þingflokkur -> {
			Party p = oc.newObject( Party.class );
			p.setOriginalID( þingflokkur.id );
			p.setName( þingflokkur.heiti );
			p.setAbbreviationLong( þingflokkur.skammstafanir.löngskammstöfun );
			p.setAbbreviationShort( þingflokkur.skammstafanir.stuttskammstöfun );
			p.setFirstParliamentNumber( þingflokkur.tímabil.fyrstaþing );
			p.setLastParliamentNumber( þingflokkur.tímabil.síðastaþing );
		} );

		oc.commitChanges();

		List<Party> list = ObjectSelect
				.query( Party.class )
				.select( Core.newContext() );

		for( Party party : list ) {
			System.out.println( party.getName() );
		}
	}
}