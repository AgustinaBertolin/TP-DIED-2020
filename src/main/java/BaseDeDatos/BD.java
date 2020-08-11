package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {
	private static final String url ="jdbc:mysql://localhost:3306/tp_integrador?useSSL=false";
	private static final String user="root";
	private static final String pass="root";
	
	private static boolean _TABLAS_CREADAS = false;
	
	private static final String TABLA_CREATE_CAMION = //BIEN
			"CREATE TABLE  IF NOT EXISTS `tp_integrador`.`camion` ( " +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `PATENTE` VARCHAR(14) NULL, " +
			"		  `MARCA` VARCHAR(25) NULL, " +
			"		  `MODELO` VARCHAR(25) NULL, " +
			"		  `KM` DECIMAL(12,2) NULL, " +
			"		  `COSTO_KM` DECIMAL(12,2) NULL, " +
			"		  `COSTO_HORA` DECIMAL(12,2) NULL, " +
			"		  `FECHA_COMPRA` DATETIME NULL, " +
			"		  PRIMARY KEY (`ID`)) ;";

	private static final String TABLA_CREATE_PLANTA = //BIEN
			"CREATE TABLE IF NOT EXISTS `tp_integrador`.`planta` (" +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `NOMBRE` VARCHAR(20) NULL, " +
			"		  `TIPO` VARCHAR(15) NULL, " +
			"		  PRIMARY KEY  (`ID`)) ;";
	
	private static final String TABLA_CREATE_CAMION_PLANTA = //bien
			"CREATE TABLE  IF NOT EXISTS `tp_integrador`.`camion_planta` ( " +
			"		  `ID_PLANTA` INT NOT NULL, " +
			"		  `ID_CAMION` INT NOT NULL , " +
			"		  PRIMARY KEY  (`ID_PLANTA`, `ID_CAMION`), "+
			"		  FOREIGN KEY (`ID_PLANTA`) REFERENCES `tp_integrador`.`planta`(`ID`) ON DELETE CASCADE," +
			"		  FOREIGN KEY (`ID_CAMION`) REFERENCES `tp_integrador`.`camion` (`ID`) ON DELETE CASCADE);";
	
	private static final String TABLA_CREATE_INSUMO = //bien
			"CREATE TABLE  IF NOT EXISTS `tp_integrador`.`insumo` ( "+
			"		  `ID` INT NOT NULL AUTO_INCREMENT, "+
			"		  `DESCRIPCION` VARCHAR(20) NOT NULL, "+
			"		  `UNIDAD_DE_MEDIDA` VARCHAR(10) NOT NULL, "+
			"		  `COSTO` DECIMAL(12,2) NULL, "+
			"		  `PESO` DECIMAL(12,2) NULL, " +
			"		  `DENSIDAD` DECIMAL(12,2) NULL, " +
			"		  PRIMARY KEY (`ID`)); ";
	
	private static final String TABLA_CREATE_ITEM = //BIEN
			"CREATE TABLE  IF NOT EXISTS `tp_integrador`.`item` ( " +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `NUMERO_ORDEN` INT NOT NULL , " +
			"		  `INSUMO` INT NOT NULL, " +
			"		  `CANTIDAD` INT NULL, " +
			"		  PRIMARY KEY (`ID`), " +
			"		  FOREIGN KEY (`NUMERO_ORDEN`) REFERENCES `tp_integrador`.`pedido` (`NUMERO_ORDEN`) ON DELETE CASCADE, " +
			"		  FOREIGN KEY (`INSUMO`) REFERENCES `tp_integrador`.`insumo` (`ID`) ON DELETE CASCADE);";
	
	private static final String TABLA_CREATE_PEDIDO = //BIEN
			"CREATE TABLE  IF NOT EXISTS `tp_integrador`.`pedido` ( " +
			"		  `NUMERO_ORDEN` INT NOT NULL AUTO_INCREMENT, " +
			"		  `PLANTA_DESTINO` INT NOT NULL, " +
			"		  `FECHA_SOLICITUD` DATE NULL, " +
			"		  `FECHA_ENTREGA` DATE NULL, " +
			"		  `ESTADO` varchar(10) NOT NULL, " +
			"		  FOREIGN KEY (`PLANTA_DESTINO`) REFERENCES `tp_integrador`.`planta` (`ID`) ON DELETE CASCADE, " +
			"		  PRIMARY KEY (`NUMERO_ORDEN`)) ;";
	
	private static final String TABLA_CREATE_ENVIO = //BIEN
			"CREATE TABLE  IF NOT EXISTS `tp_integrador`.`envio` ( " +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `NUMERO_ORDEN` INT NOT NULL, " +
			"		  `RUTA` INT NOT NULL, " +
			"		  `CAMION` INT NOT NULL, " +
			"		  `COSTO` DECIMAL(12,2) NULL, " +
			"		  PRIMARY KEY (`ID`), "+
			"		  FOREIGN KEY (`NUMERO_ORDEN`) REFERENCES `tp_integrador`.`pedido` (`NUMERO_ORDEN`) ON DELETE CASCADE, " +
			"		  FOREIGN KEY (`CAMION`) REFERENCES `tp_integrador`.`camion` (`ID`) ON DELETE CASCADE, " +
			"		  FOREIGN KEY (`RUTA`) REFERENCES `tp_integrador`.`ruta` (`ID`) ON DELETE CASCADE);";
	
	private static final String TABLA_CREATE_RUTA = //bien
			"CREATE TABLE  IF NOT EXISTS `tp_integrador`.`ruta` ( " +
			"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
			"		  `PLANTA_ORIGEN` INT NOT NULL, " +
			"		  `PLANTA_DESTINO` INT NOT NULL, " + 
			"		  `DISTANCIA_KM` DECIMAL(12,2) NULL, " +
			"		  `DURACION` INT NULL, " + //VER
			"		  `PESO_MAXIMO_POR_DIA` DECIMAL(12,2) NULL, " +
			"		  PRIMARY KEY (`ID`) ,"+
			"		  FOREIGN KEY (`PLANTA_DESTINO`) REFERENCES `tp_integrador`.`planta` (`ID`) ON DELETE CASCADE, " +
			"		  FOREIGN KEY (`PLANTA_ORIGEN`) REFERENCES `tp_integrador`.`planta` (`ID`) ON DELETE CASCADE); " ;
	
	private static final String TABLA_CREATE_STOCK = 
			"CREATE TABLE  IF NOT EXISTS `tp_integrador`.`stock` ( " +
			"		  `ID_REGISTRO` INT NOT NULL AUTO_INCREMENT, " +
			"		  `ID_PLANTA` INT NOT NULL, " +
			"		  `INSUMO` INT NOT NULL, " +
			"		  `CANTIDAD` INT NULL, " +
			"		  `PUNTO_REPOSICION` INT NULL, " +
			"		  PRIMARY KEY (`ID_REGISTRO`, `ID_PLANTA`, `INSUMO`), " +
			"		  FOREIGN KEY (`ID_PLANTA`) REFERENCES `tp_integrador`.`planta` (`ID`) ON DELETE CASCADE," +
			"		  FOREIGN KEY (`INSUMO`) REFERENCES `tp_integrador`.`insumo` (`ID`) ON DELETE CASCADE);";

	private static final String CREATE_TABLA_ID =
			"CREATE TABLE  IF NOT EXISTS `tp_integrador`.`tabla_id` ( " +
					"		  `ID` INT NOT NULL AUTO_INCREMENT, " +
					"		  `ID_PEDIDO` INT NOT NULL, " +
					"		  `ID_CAMION` INT NOT NULL, " +
					"		  `ID_PLANTA` INT NOT NULL, " +
					"		  `ID_INSUMO` INT NOT NULL, " +
					"		  `ID_RUTA` INT NOT NULL, " +
					"		  PRIMARY KEY (`ID`)); ";
	
	private static final String TABLA_CREATE_RUTA_PEDIDO = //bien
			"CREATE TABLE  IF NOT EXISTS `tp_integrador`.`ruta_pedido` ( " +
			"		  `ID_RUTA` INT NOT NULL, " +
			"		  `ID_PEDIDO` INT NOT NULL , " +
			"		  PRIMARY KEY  (`ID_RUTA`, `ID_PEDIDO`), "+
			"		  FOREIGN KEY (`ID_RUTA`) REFERENCES `tp_integrador`.`ruta`(`ID`) ON DELETE CASCADE," +
			"		  FOREIGN KEY (`ID_PEDIDO`) REFERENCES `tp_integrador`.`pedido` (`NUMERO_ORDEN`) ON DELETE CASCADE);";

	
	private BD(){
			// no se pueden crear instancias de esta clase
	}
	
	private static void verificarCrearTablas() {
		if(!_TABLAS_CREADAS) {
			Connection conn = BD.crearConexion();
			Statement stmt = null;
			Statement stmt1 = null;
			Statement stmt2 = null;
			Statement stmt3 = null;
			Statement stmt4 = null;
			Statement stmt5 = null;
			Statement stmt6 = null;
			Statement stmt7 = null;
			Statement stmt8 = null;
			Statement stmt9 = null;
			Statement stmt0 = null;
			try {
				stmt = conn.createStatement();
				stmt1 = conn.createStatement();
				stmt2 = conn.createStatement();
				stmt3 = conn.createStatement();
				stmt4 = conn.createStatement();
				stmt5 = conn.createStatement();
				stmt6 = conn.createStatement();
				stmt7 = conn.createStatement();
				stmt8 = conn.createStatement();
				stmt9 = conn.createStatement();
				stmt0 = conn.createStatement();

				boolean tablaCamion = stmt.execute(TABLA_CREATE_CAMION); 
				boolean tablaPlanta = stmt1.execute(TABLA_CREATE_PLANTA);
				boolean tablaCamionPlanta = stmt2.execute(TABLA_CREATE_CAMION_PLANTA);
				boolean tablaInsumo = stmt3.execute(TABLA_CREATE_INSUMO);
				boolean tablaRuta = stmt4.execute(TABLA_CREATE_RUTA);
				boolean tablaPedido = stmt5.execute(TABLA_CREATE_PEDIDO);
				boolean tablaItem = stmt6.execute(TABLA_CREATE_ITEM);
				boolean tablaEnvio = stmt7.execute(TABLA_CREATE_ENVIO);
				boolean tablaStock = stmt8.execute(TABLA_CREATE_STOCK);
				boolean tablaID = stmt9.execute(CREATE_TABLA_ID);
				boolean tablaRutaPedido = stmt0.execute(TABLA_CREATE_RUTA_PEDIDO);
				
				_TABLAS_CREADAS = tablaCamion && 
								  tablaPlanta && 
								  tablaCamionPlanta && 
								  tablaInsumo &&  
								  tablaRuta && 
								  tablaPedido && 
								  tablaItem && 
								  tablaEnvio &&
								  tablaID &&
								  tablaRutaPedido &&
								  tablaStock;
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
					try {
						if(stmt!=null) stmt.close();
						if(stmt1!=null) stmt1.close();
						if(stmt2!=null) stmt2.close();
						if(stmt3!=null) stmt3.close();
						if(stmt4!=null) stmt4.close();
						if(stmt5!=null) stmt5.close();
						if(stmt6!=null) stmt6.close();
						if(stmt7!=null) stmt7.close();
						if(stmt8!=null) stmt8.close();
						if(stmt9!=null) stmt9.close();
						if(stmt0!=null) stmt0.close();
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