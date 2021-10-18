package base_de_datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AdministradorBD {

	protected Connection getConnection() {
		Connection con = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost/postgres","postgres","java");
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
}
