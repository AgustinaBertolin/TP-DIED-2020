package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {
	private static final String url ="jdbc:mysql://localhost:3306/died?useSSL=false";
	private static final String user="root";
	private static final String pass="root";
	
	private static boolean _TABLAS_CREADAS = false;
	
	private static final String TABLA_CREATE_CAMION = 
			"CREATE TABLE  IF NOT EXISTS `died`.`camion` ( " +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `PATENTE` VARCHAR(14) NULL, " +
			"		  `MARCA` VARCHAR(45) NULL, " +
			"		  `MODELO` VARCHAR(45) NULL, " +
			"		  `KM` VARCHAR(45) NULL, " +
			"		  `COSTO_KM` DECIMAL(12,2) NULL, " +
			"		  `COSTO_HORA` DECIMAL(12,2) NULL, " +
			"		  `FECHA_COMPRA` DATETIME NULL, " +
			"		  PRIMARY KEY (`ID`)) ;";

	private static final String TABLA_CREATE_PLANTA = 
			"CREATE TABLE IF NOT EXISTS `died`.`planta` (" +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `NOMBRE` VARCHAR(20) NULL, " +
			"		  PRIMARY KEY  (`ID`)) ;";
	
	private static final String TABLA_CREATE_CAMION_PLANTA = 
			"CREATE TABLE  IF NOT EXISTS `died`.`camion_planta` ( " +
			"		  `ID_PLANTA` INT NOT NULL, " +
			"		  `ID_CAMION` INT NOT NULL , " +
			"		  PRIMARY KEY  (`ID_PLANTA`, `ID_CAMION`) "+
			"		  SECONDARY KEY (`ID_PLANTA`) REFERENCES `died`.`planta` (`ID`) ON DELETE CASCADE, " +
			"		  SECONDARY KEY (`ID_CAMION`) REFERENCES `died`.`camion` (`ID`) ON DELETE CASCADE);";
	
	private static final String TABLA_CREATE_INSUMO = 
			"CREATE TABLE  IF NOT EXISTS `died`.`insumo` ( "+
			"		  `ID` INT NOT NULL AUTO_INCREMENT, "+
			"		  `DESCRIPCION` VARCHAR(30) NOT NULL, "+
			"		  `UNIDAD_DE_MEDIDA` ENUM (`KILO`, `PIEZA`, `GRAMO`, `METRO`, `LITRO`, `M3`, `M2`) NOT NULL, "+
			"		  `COSTO` DECIMAL(12,2) NULL, "+
			"		  PRIMARY KEY (`ID`)); ";
	
	private static final String TABLA_CREATE_INSUMO_GENERAL = 
			"CREATE TABLE  IF NOT EXISTS `died`.`insumo_general` ( " +
			"		  `ID` INT NOT NULL, " +
			"		  `PESO` DECIMAL(12,2) NULL, " +
			"		  PRIMARY KEY (`ID`), " +
			"		  SECONDARY KEY (`ID`) REFERENCES `died`.`insumo` (`ID`) ON DELETE CASCADE) ;";
	
	private static final String TABLA_CREATE_INSUMO_LIQUIDO = 
			"CREATE TABLE  IF NOT EXISTS `died`.`insumo_liquido` ( " +
			"		  `ID` INT NOT NULL, " +
			"		  `DENSIDAD` DECIMAL(12,2) NULL, " +
			"		  PRIMARY KEY (`ID`), " +
			"		  SECONDARY KEY (`ID`) REFERENCES `died`.`insumo` (`ID`) ON DELETE CASCADE) ;";
	
	private static final String TABLA_CREATE_ITEM = 
			"CREATE TABLE  IF NOT EXISTS `died`.`item` ( " +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `NUMERO_ORDEN` INT NOT NULL , " +
			"		  `INSUMO` INT NOT NULL, " +
			"		  `CANTIDAD` INT NULL, " +
			"		  PRIMARY KEY (`ID`), " +
			"		  SECONDARY KEY (`NUMERO_ORDEN`) REFERENCES `died`.`pedido` (`NUMERO_ORDEN`) ON DELETE CASCADE, " +
			"		  SECONDARY KEY (`INSUMO`) REFERENCES `died`.`insumo` (`ID`) ON DELETE CASCADE);";
	
	private static final String TABLA_CREATE_PEDIDO = 
			"CREATE TABLE  IF NOT EXISTS `died`.`pedido` ( " +
			"		  `NUMERO_ORDEN` INT NOT NULL AUTO_INCREMENT, " +
			"		  `PLANTA_DESTINO` INT NOT NULL, " +
			"		  `FECHA_SOLICITUD` DATE NULL, " +
			"		  `FECHA_ENTREGA` DATE NULL, " +
			"		  `ESTADO` ENUM (`CREADA`, `PROCESADA`, `ENTREGADA`, `CANCELADO`) NOT NULL, " + 
			"		  PRIMARY KEY (`ID`)) ;";
	
	private static final String TABLA_CREATE_ENVIO = 
			"CREATE TABLE  IF NOT EXISTS `died`.`envio` ( " +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `NUMERO_ORDEN` INT NOT NULL, " +
			"		  `RUTA` INT NOT NULL, " +
			"		  `CAMION` INT NOT NULL, " +
			"		  `COSTO` DECIMAL(12,2) NULL, " +
			"		  PRIMARY KEY (`ID`)) "+
			"		  SECONDARY KEY (`NUMERO_ORDEN`) REFERENCES `died`.`pedido` (`NUMERO_ORDEN`) ON DELETE CASCADE, " +
			"		  SECONDARY KEY (`CAMION`) REFERENCES `died`.`camion` (`ID`) ON DELETE CASCADE, " +
			"		  SECONDARY KEY (`RUTA`) REFERENCES `died`.`ruta` (`ID`) ON DELETE CASCADE);";
	
	private static final String TABLA_CREATE_RUTA = 
			"CREATE TABLE  IF NOT EXISTS `died`.`ruta` ( " +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `PLANTA_ORIGEN` INT NOT NULL, " +
			"		  `PLANTA_DESTINO` INT NOT NULL, " + 
			"		  `DISTANCIA_KM` DECIMAL(12,2) NULL, " +
			"		  `DURACION` DATE NULL, " + //VER
			"		  `PESO_MAXIMO_POR_DIA` DECIMAL(12,2) NULL, " +
			"		  PRIMARY KEY (`ID`)) ;";
	
	private static final String TABLA_CREATE_STOCK = 
			"CREATE TABLE  IF NOT EXISTS `died`.`stock` ( " +
			"		  `ID_REGISTRO` INT NOT NULL AUTO_INCREMENT, " +
			"		  `ID_PLANTA` INT NOT NULL, " +
			"		  `INSUMO` VARCHAR(30) NOT NULL, " +
			"		  `CANTIDAD` INT NULL, " +
			"		  `PUNTO_REPOSICION` INT NULL, " +
			"		  PRIMARY KEY (`ID_REGISTRO`, `ID_PLANTA`, `INSUMO`) " +
			"		  SECONDARY KEY (`ID_PLANTA`) REFERENCES `died`.`planta` (`ID`) ON DELETE CASCADE" +
			"		  SECONDARY KEY (`INSUMO`) REFERENCES `died`.`insumo` (`ID`) ON DELETE CASCADE);";

	private static final String TABLA_CREATE_TODAS_PRUEBA = 
			TABLA_CREATE_CAMION +
			TABLA_CREATE_PLANTA +
			TABLA_CREATE_CAMION_PLANTA +
			TABLA_CREATE_INSUMO +
			TABLA_CREATE_INSUMO_LIQUIDO +
			TABLA_CREATE_INSUMO_GENERAL +
			TABLA_CREATE_RUTA +
			TABLA_CREATE_STOCK +
			TABLA_CREATE_PEDIDO +
			TABLA_CREATE_ITEM +
			TABLA_CREATE_ENVIO;
			
	private BD(){
			// no se pueden crear instancias de esta clase
	}
	
	private static void verificarCrearTablas() {
		if(!_TABLAS_CREADAS) {
			Connection conn = BD.crearConexion();
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				boolean tablasCreadas = stmt.execute(TABLA_CREATE_TODAS_PRUEBA); 
				_TABLAS_CREADAS = tablasCreadas;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
					try {
						if(stmt!=null) stmt.close();
						if(conn!=null) conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}

	private static Connection crearConexion(){
		Connection conn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn= DriverManager.getConnection(url,user,pass);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getConexion() {
		verificarCrearTablas();
		return crearConexion();
	}
	
}