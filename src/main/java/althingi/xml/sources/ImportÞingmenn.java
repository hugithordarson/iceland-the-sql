package althingi.xml.sources;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import althingi.xml.adapters.LocalDateAdapter;

public class ImportÞingmenn {

	@XmlRootElement( name = "þingmannalisti" )
	public static class Result {

		@XmlElement( name = "þingmaður" )
		public List<Þingmaður> þingmenn = new ArrayList<>();

		public static class Þingmaður {

			@XmlAttribute
			public String id;

			public String nafn;

			@XmlJavaTypeAdapter( value = LocalDateAdapter.class )
			public LocalDate fæðingardagur;
		}
	}
}