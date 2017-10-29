package althingi.xml.sources;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import althingi.xml.StringTrimAdapter;

public class ImportNefndir {

	@XmlRootElement( name = "nefndir" )
	public static class Result {

		@XmlElement( name = "nefnd" )
		public List<Nefnd> nefndir = new ArrayList<>();

		public static class Nefnd {

			@XmlAttribute
			public long id;

			@XmlJavaTypeAdapter( value = StringTrimAdapter.class, type = String.class )
			public String heiti;
			public Skammstafanir skammstafanir;
			public Tímabil tímabil;
		}

		public static class Skammstafanir {
			public String stuttskammstöfun;
			public String löngskammstöfun;
		}

		public static class Tímabil {
			public long fyrstaþing;
			public long síðastaþing;
		}
	}
}