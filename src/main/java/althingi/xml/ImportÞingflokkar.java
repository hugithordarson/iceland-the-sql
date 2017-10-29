package althingi.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class ImportÞingflokkar {

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