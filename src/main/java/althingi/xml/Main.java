package althingi.xml;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;

import althingi.data.Committee;
import althingi.data.CommitteeMembership;
import althingi.data.Party;
import althingi.data.Person;
import althingi.db.Core;
import althingi.xml.sources.ImportNefndarmenn;
import althingi.xml.sources.ImportNefndir;
import althingi.xml.sources.ImportÞingflokkar;
import althingi.xml.sources.ImportÞingmenn;

public class Main {

	public static void main( String[] args ) {

		importÞingflokkar( XMLUtil.urlToResult( Sources.URL_ÞINGFLOKKAR, ImportÞingflokkar.Result.class ) );
		importNefndir( XMLUtil.urlToResult( Sources.URL_NEFNDIR, ImportNefndir.Result.class ) );
		importÞingmenn( XMLUtil.urlToResult( Sources.URL_ÞINGMENN, ImportÞingmenn.Result.class ) );
		importNefndarmenn( XMLUtil.urlToResult( Sources.URL_NEFNDARMENN, ImportNefndarmenn.Result.class ) );

		List<CommitteeMembership> list = ObjectSelect
				.query( CommitteeMembership.class )
				.select( Core.newContext() );

		for( CommitteeMembership o : list ) {
			System.out.println( o.getPerson().getName() + " : " + o.getCommittee().getName() + " : " + o.getDateFrom() + " : " + o.getDateTo() );
		}
	}

	private static void importÞingmenn( ImportÞingmenn.Result result ) {
		ObjectContext oc = Core.newContext();

		result.þingmenn.forEach( in -> {
			Person p = oc.newObject( Person.class );
			p.setOriginalID( in.id );
			p.setName( in.nafn );
			p.setBirthDate( in.fæðingardagur );
		} );

		oc.commitChanges();
	}

	private static void importNefndarmenn( ImportNefndarmenn.Result result ) {
		ObjectContext oc = Core.newContext();

		result.nefndir.forEach( nefnd -> {
			nefnd.nefndarmenn.forEach( nefndarmaður -> {
				CommitteeMembership cm = oc.newObject( CommitteeMembership.class );
				cm.setPerson( ObjectSelect.query( Person.class ).where( Person.ORIGINAL_ID.eq( nefndarmaður.id ) ).selectOne( oc ) );
				cm.setCommittee( ObjectSelect.query( Committee.class ).where( Committee.ORIGINAL_ID.eq( nefnd.id ) ).selectOne( oc ) );
				cm.setStatus( nefndarmaður.staða );
				cm.setDateFrom( nefndarmaður.nefndasetahófst );
				cm.setDateTo( nefndarmaður.nefndasetulauk );
			} );
		} );

		oc.commitChanges();
	}

	private static void importÞingflokkar( ImportÞingflokkar.Result result ) {
		ObjectContext oc = Core.newContext();

		result.þingflokkar.forEach( in -> {
			Party p = oc.newObject( Party.class );
			p.setOriginalID( in.id );
			p.setName( in.heiti );
			p.setAbbreviationLong( in.skammstafanir.löngskammstöfun );
			p.setAbbreviationShort( in.skammstafanir.stuttskammstöfun );
			p.setFirstParliamentNumber( in.tímabil.fyrstaþing );
			p.setLastParliamentNumber( in.tímabil.síðastaþing );
		} );

		oc.commitChanges();
	}

	private static void importNefndir( ImportNefndir.Result result ) {
		ObjectContext oc = Core.newContext();

		result.nefndir.forEach( in -> {
			Committee p = oc.newObject( Committee.class );
			p.setOriginalID( in.id );
			p.setName( in.heiti );
			p.setAbbreviationLong( in.skammstafanir.löngskammstöfun );
			p.setAbbreviationShort( in.skammstafanir.stuttskammstöfun );
			p.setFirstParliamentNumber( in.tímabil.fyrstaþing );
			p.setLastParliamentNumber( in.tímabil.síðastaþing );
		} );

		oc.commitChanges();
	}
}