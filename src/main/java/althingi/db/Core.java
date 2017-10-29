package althingi.db;

import javax.sql.DataSource;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.DataNodeDescriptor;
import org.apache.cayenne.configuration.server.DataSourceFactory;
import org.apache.cayenne.configuration.server.ServerRuntime;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Core {

	private static ServerRuntime _serverRuntime;

	private static ServerRuntime serverRuntime() {
		if( _serverRuntime == null ) {
			_serverRuntime = ServerRuntime
					.builder()
					.addConfigs( "cayenne-althingi.xml" )
					.addModule( binder -> binder.bind( DataSourceFactory.class ).to( ProjectDataSourceFactory.class ) )
					.build();
		}

		return _serverRuntime;
	}

	public static ObjectContext newContext() {
		return serverRuntime().newContext();
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