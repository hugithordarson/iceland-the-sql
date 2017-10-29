package althingi.xml.sources;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import althingi.xml.adapters.LocalDateAdapter;

public class ImportNefndarmenn {

	@XmlRootElement( name = "nefndarmenn" )
	public static class Result {

		@XmlElement( name = "nefnd" )
		public List<Nefnd> nefndir = new ArrayList<>();

		public static class Nefnd {
			public String heiti;

			@XmlElement( name = "nefndarmaður" )
			public List<Nefndarmaður> nefndarmenn = new ArrayList<>();

			public static class Nefndarmaður {

				@XmlAttribute
				public long id;

				public String nafn;
				public String staða;

				@XmlJavaTypeAdapter( value = LocalDateAdapter.class )
				public LocalDate nefndasetahófst;

				@XmlJavaTypeAdapter( value = LocalDateAdapter.class )
				public LocalDate nefndasetulauk;
			}
		}
	}
}