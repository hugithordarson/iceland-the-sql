package althingi.db;

import javax.sql.DataSource;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.DataNodeDescriptor;
import org.apache.cayenne.configuration.server.DataSourceFactory;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.configuration.server.ServerRuntimeBuilder;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Core {

	private static ServerRuntime _runtime;

	public static ServerRuntime runtime() {
		if( _runtime == null ) {
			ServerRuntimeBuilder b = ServerRuntime.builder();
			b = b.addConfigs( "cayenne-althingi.xml" );
			b = b.addModule( binder -> binder.bind( DataSourceFactory.class ).to( ProjectDataSourceFactory.class ) );
			_runtime = b.build();
		}

		return _runtime;
	}

	public static ObjectContext newContext() {
		return runtime().newContext();
	}

	public static class ProjectDataSourceFactory implements DataSourceFactory {

		@Override
		public DataSource getDataSource( DataNodeDescriptor nodeDescriptor ) throws Exception {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl( "jdbc:h2:mem:althingi" );
			config.setDriverClassName( "org.h2.Driver" );
			return new HikariDataSource( config );
		}
	}
}