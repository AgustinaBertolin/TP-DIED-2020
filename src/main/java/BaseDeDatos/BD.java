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
			"		  PRIMARY KEY (`ID`)) ";

	private static final String TABLA_CREATE_PLANTA = 
			"CREATE TABLE IF NOT EXISTS `died`.`planta` (" +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `NOMBRE` VARCHAR(20) NULL, " +
			"		  PRIMARY KEY  (`ID`)) ";
	
	private static final String TABLA_CREATE_INSUMO = 
			"CREATE TABLE  IF NOT EXISTS `died`.`insumo` ( "+
			"		  `DESCRIPCION` VARCHAR(30) NOT NULL, "+
			"		  `UNIDAD_DE_MEDIDA` VARCHAR(10) NOT NULL, "+
			"		  `COSTO` DECIMAL(12,2) NULL, "+
			"		  PRIMARY KEY (`DESCRIPCION`)) ";
	
	private static final String TABLA_CREATE_INSUMO_GENERAL = 
			"CREATE TABLE  IF NOT EXISTS `died`.`insumo_general` ( " +
			"		  `DESCRIPCION` VARCHAR(30) NULL, " +
			"		  `PESO` DECIMAL(12,2) NULL, " +
			"		  PRIMARY KEY (`DESCRIPCION`), " +
			"		  SECONDARY KEY (`DESCRIPCION`) REFERENCES `died`.`insumo` (`DESCRIPCION`) ON DELETE CASCADE) ";
	
	private static final String TABLA_CREATE_INSUMO_LIQUIDO = 
			"CREATE TABLE  IF NOT EXISTS `died`.`insumo_liquido` ( " +
			"		  `DESCRIPCION` VARCHAR(30) NULL, " +
			"		  `DENSIDAD` DECIMAL(12,2) NULL, " +
			"		  PRIMARY KEY (`DESCRIPCION`), " +
			"		  SECONDARY KEY (`DESCRIPCION`) REFERENCES `died`.`insumo` (`DESCRIPCION`) ON DELETE CASCADE) ";
	
	private static final String TABLA_CREATE_ITEM = 
			"CREATE TABLE  IF NOT EXISTS `died`.`item` ( " +
			"		  `NUMERO_ORDEN` INT NOT NULL , " +
			"		  `INSUMO` VARCHAR(30) NOT NULL, " +
			"		  `CANTIDAD` INT NULL, " +
			"		  PRIMARY KEY (`NUMERO_ORDEN`, `INSUMO`), " +
			"		  SECONDARY KEY (`NUMERO_ORDEN`) REFERENCES `died`.`pedido` (`NUMERO_ORDEN`) ON DELETE CASCADE, " +
			"		  SECONDARY KEY (`INSUMO`) REFERENCES `died`.`insumo` (`DESCRIPCION`) ON DELETE CASCADE)";
	
	private static final String TABLA_CREATE_PEDIDO = 
			"CREATE TABLE  IF NOT EXISTS `died`.`pedido` ( " +
			"		  `NUMERO_ORDEN` INT NOT NULL AUTO_INCREMENT, " +
			"		  `PLANTA_DESTINO` INT NOT NULL, " +
			"		  `FECHA_SOLICITUD` DATE NULL, " +
			"		  `FECHA_ENTREGA` DATE NULL, " +
			"		  `ESTADO` VARCHAR(10) NOT NULL, " + //VER K ONDA
			"		  `ENVIO` INT NULL, " +
			"		  `COSTO_HORA` DECIMAL(12,2) NULL, " +
			"		  `FECHA_COMPRA` DATETIME NULL, " +
			"		  PRIMARY KEY (`ID`)) ";
	
	private static final String TABLA_CREATE_RUTA = 
			"CREATE TABLE  IF NOT EXISTS `died`.`ruta` ( " +
			"		  `PLANTA_ORIGEN` INT NOT NULL, " +
			"		  `PLANTA_DESTINO` INT NOT NULL, " + //VER RUTA.
			"		  `MARCA` VARCHAR(45) NULL, " +
			"		  `MODELO` VARCHAR(45) NULL, " +
			"		  `KM` VARCHAR(45) NULL, " +
			"		  `COSTO_KM` DECIMAL(12,2) NULL, " +
			"		  `COSTO_HORA` DECIMAL(12,2) NULL, " +
			"		  `FECHA_COMPRA` DATETIME NULL, " +
			"		  PRIMARY KEY (`ID`)) ";
	
	private static final String TABLA_CREATE_STOCK = 
			"CREATE TABLE  IF NOT EXISTS `died`.`stock` ( " +
			"		  `ID_REGISTRO` INT NOT NULL AUTO_INCREMENT, " +
			"		  `ID_PLANTA` INT NOT NULL, " +
			"		  `INSUMO` VARCHAR(30) NOT NULL, " +
			"		  `CANTIDAD` INT NULL, " +
			"		  `PUNTO_REPOSICION` INT NULL, " +
			"		  PRIMARY KEY (`ID_REGISTRO`, `ID_PLANTA`, `INSUMO`) " +
			"		  SECONDARY KEY (`ID_PLANTA`) REFERENCES `died`.`planta` (`ID`) ON DELETE CASCADE" +
			"		  SECONDARY KEY (`INSUMO`) REFERENCES `died`.`insumo` (`DESCRIPCION`) ON DELETE CASCADE)";

	private BD(){
			// no se pueden crear instancias de esta clase
	}
	
	private static void verificarCrearTablas() {
		if(!_TABLAS_CREADAS) {
			Connection conn = BD.crearConexion();
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				boolean tablaCamionCreada = stmt.execute(TABLA_CREATE_CAMION); //SE PUEDEN HACER VARIOS STATEMENT EN UN SOLO TRY? SIN CERRAR EL STATEMENT
				boolean tablaPlantaCreada = stmt.execute(TABLA_CREATE_PLANTA);
				boolean tablaInsumoCreada = stmt.execute(TABLA_CREATE_INSUMO);
				boolean tablaInsumoGeneralCreada = stmt.execute(TABLA_CREATE_INSUMO_GENERAL);
				boolean tablaInsumoLiquidoCreada = stmt.execute(TABLA_CREATE_INSUMO_LIQUIDO);
				boolean tablaStockCreada = stmt.execute(TABLA_CREATE_STOCK);
				boolean tablaRutaCreada = stmt.execute(TABLA_CREATE_RUTA);
				boolean tablaPedidoCreada = stmt.execute(TABLA_CREATE_PEDIDO);
				boolean tablaItemCreada = stmt.execute(TABLA_CREATE_ITEM);
				_TABLAS_CREADAS = tablaCamionCreada && 
								  tablaPlantaCreada && 
								  tablaInsumoCreada && 
								  tablaInsumoGeneralCreada &&
								  tablaInsumoLiquidoCreada &&
								  tablaStockCreada &&
								  tablaRutaCreada &&
								  tablaPedidoCreada &&
								  tablaItemCreada;
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