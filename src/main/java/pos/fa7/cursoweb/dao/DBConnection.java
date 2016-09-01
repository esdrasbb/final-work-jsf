package pos.fa7.cursoweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnection {

	private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);

	private static final String DB_FILE_NAME = "/tmp/db_file";

	public static Connection getConnection() {

		Connection connection;
		try {
			Class.forName("org.hsqldb.jdbcDriver");

			connection = DriverManager.getConnection("jdbc:hsqldb:file" + DB_FILE_NAME + ";shutdown=true", "sa", "");
			connection.setAutoCommit(true);
		} catch (Exception e) {
			logger.error("Error on get database connection!!", e);
			throw new RuntimeException("Error on get database connection");
		}

		return connection;

	}

	public static synchronized void initDB() {
		try (Connection con = DBConnection.getConnection(); Statement stmt = con.createStatement();) {
			stmt.executeUpdate("DROP TABLE user");
		} catch (SQLException e) {
			// always drop
		}

		try (Connection con = DBConnection.getConnection(); Statement stmt = con.createStatement();) {
			stmt.executeUpdate("CREATE TABLE user (id INTEGER IDENTITY PRIMARY KEY, cep VARCHAR(9), cpf VARCHAR(11), "
					+ "email VARCHAR(100), nome_completo VARCHAR(200), nome_reduzido VARCHAR(100), senha VARCHAR(30), "
					+ "data_nascimento TIMESTAMP)");
		} catch (SQLException e) {
			// always create
		}
	}
}