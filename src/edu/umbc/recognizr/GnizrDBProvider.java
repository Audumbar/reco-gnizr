/**
 * 
 */
package edu.umbc.recognizr;

import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
/**
 * @author audumbar
 *
 */
public class GnizrDBProvider {

	private BasicDataSource dataSource;
	
	public GnizrDBProvider() {
		init();
	}
	
	private void init() {
		if (dataSource == null) {
			dataSource = new BasicDataSource();
			dataSource.setUsername("gnizr");
			dataSource.setPassword("gnizr");
			dataSource.setUrl("jdbc:mysql://localhost/gnizr_db");
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.addConnectionProperty("characterEncoding", "UTF-8");
			dataSource.addConnectionProperty("useUnicode", "TRUE");		
		}
	}
	
	protected IDatabaseConnection getConnection() throws Exception {
		IDatabaseConnection dc = new DatabaseConnection(dataSource.getConnection());
		DatabaseConfig config = dc.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new MySqlDataTypeFactory());
		return dc;
	}
	

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
