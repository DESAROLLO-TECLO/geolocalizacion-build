package mx.com.teclo.smm.wsw.util.bd;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionUtilBd implements Serializable{

	
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectionUtilBd.class);
	
	@Autowired
	private DataSource ds;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1057071286404925235L;

	
	/**
	 * Descripci�n: M�todo para abrir conexi�n a la 
	 * base de datos utilizando el jndi-name que se 
	 * encuentra en application.properties 
	 * @author Jorge Luis
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection conectarBD() throws SQLException,NamingException{
		Connection con = null;
		try {
			con = ds.getConnection();
			return con;
		} catch (SQLException e) {
			logger.info("Ocurri� un error al conectar a la Base de Datos -> Message: " + e.getMessage() + " -> Causa: " + e.getCause());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Descripci�n: M�todo para cerrar la conexi�n 
	 * a la base de datos 
	 * @author Jorge Luis
	 * @param Connection
	 * @throws SQLException
	 */
   public void cerrarBD(Connection cn) throws SQLException{
       if (cn != null) {
           try {
               cn.close();
           } catch (SQLException ex) {
               logger.info("Ocurri� un error al cerrar la conexi�n de Base de Datos -> Message: " + ex.getMessage() + " -> Causa: " + ex.getCause());
               ex.printStackTrace();
           }
       }
   }
}
