package althingi.xml;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;

import althingi.data.Committee;
import althingi.data.Party;
import althingi.db.Core;
import althingi.xml.sources.ImportNefndir;
import althingi.xml.sources.ImportÞingflokkar;

public class Main {

	public static void main( String[] args ) {

		importÞingflokkar( XMLUtil.urlToResult( Sources.URL_ÞINGFLOKKAR, ImportÞingflokkar.Result.class ) );
		importNefndir( XMLUtil.urlToResult( Sources.URL_NEFNDIR, ImportNefndir.Result.class ) );

		List<Committee> list = ObjectSelect
				.query( Committee.class )
				.select( Core.newContext() );

		for( Committee party : list ) {
			System.out.println( party.getName() );
		}
	}

	private static void importÞingflokkar( ImportÞingflokkar.Result result ) {
		ObjectContext oc = Core.newContext();

		result.þingflokkar.forEach( fromXML -> {
			Party p = oc.newObject( Party.class );
			p.setOriginalID( fromXML.id );
			p.setName( fromXML.heiti );
			p.setAbbreviationLong( fromXML.skammstafanir.löngskammstöfun );
			p.setAbbreviationShort( fromXML.skammstafanir.stuttskammstöfun );
			p.setFirstParliamentNumber( fromXML.tímabil.fyrstaþing );
			p.setLastParliamentNumber( fromXML.tímabil.síðastaþing );
		} );

		oc.commitChanges();
	}

	private static void importNefndir( ImportNefndir.Result result ) {
		ObjectContext oc = Core.newContext();

		result.nefndir.forEach( fromXML -> {
			Committee p = oc.newObject( Committee.class );
			p.setOriginalID( fromXML.id );
			p.setName( fromXML.heiti );
			p.setAbbreviationLong( fromXML.skammstafanir.löngskammstöfun );
			p.setAbbreviationShort( fromXML.skammstafanir.stuttskammstöfun );
			p.setFirstParliamentNumber( fromXML.tímabil.fyrstaþing );
			p.setLastParliamentNumber( fromXML.tímabil.síðastaþing );
		} );

		oc.commitChanges();
	}
}