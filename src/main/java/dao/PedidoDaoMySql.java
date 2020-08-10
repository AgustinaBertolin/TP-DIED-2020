package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.BD;
import dominio.Envio;
import dominio.Item;
import dominio.Pedido;
import dominio.Pedido.Estado;

public class PedidoDaoMySql implements PedidoDao {
	
	private static final String SELECT_ALL_PEDIDO =
			" SELECT * FROM `tp_integrador`.`pedido`";
	
	private static final String SELECT_ALL_PEDIDO_PLANTA =
			" SELECT * FROM `tp_integrador`.`pedido`"+
			" WHERE PLANTA_DESTINO = ?";
	
	private static final String SELECT_ID_ITEM =  //BUSCAR INSUMOS?
			" SELECT * FROM `tp_integrador`.`item`"+
			" WHERE NUMERO_ORDEN = ?";
	
	private static final String SELECT_ID_ENVIO = //BUSCAR RUTA Y CAMION??
			" SELECT * FROM `tp_integrador`.`envio`"+
			" WHERE NUMERO_ORDEN = ?";
	
	private static final String SELECT_ESTADO_PEDIDO = 
			"SELECT * FROM `tp_integrador`.`pedido`"+
			" WHERE ESTADO = ?";
	
	private static final String UPDATE_PEDIDO =
			" UPDATE `tp_integrador`.`pedido` SET PLANTA_DESTINO = ?, FECHA_SOLICITUD =? ,FECHA_ENTREGA = ?, ESTADO = ?" + 
			" WHERE NUMERO_ORDEN = ?";

	private static final String UPDATE_ITEM =
			" UPDATE `tp_integrador`.`item` SET INSUMO = ?, CANTIDAD = ?"+
			" WHERE NUMERO_ORDEN = ?";
	
	private static final String UPDATE_ENVIO =
			" UPDATE `tp_integrador`.`envio` RUTA = ?, CAMION = ?, COSTO = ?"+
			" WHERE NUMERO_ORDEN = ? ";
	
	private static final String INSERT_ITEM =
			" INSERT INTO `tp_integrador`.`item` (NUMERO_ORDEN, INSUMO, CANTIDAD) VALUES (?, ?, ?)";
	
	private static final String INSERT_ENVIO =
			" INSERT INTO `tp_integrador`.`envio` (NUMERO_ORDEN, RUTA, CAMION, COSTO) VALUES (?, ?, ?)";
	
	private static final String INSERT_PEDIDO =
			" INSERT INTO `tp_integrador`.`pedido` (PLANTA_DESTINO, FECHA_SOLICITUD, FECHA_ENTREGA, ESTADO) VALUES (?, ?, ?, ?)";
	
	private static final String DELETE_PEDIDO =
			" DELETE FROM `tp_integrador`.`pedido`"+
			" WHERE NUMERO_ORDEN = ?";
	
	public Pedido saveOrUpdate(Pedido p) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		try {
			if(p.getNroOrden()!=null && p.getNroOrden()>0) {
				System.out.println("EJECUTA UPDATE");
				pstmt= conn.prepareStatement(UPDATE_PEDIDO);
				pstmt.setInt(1, p.getDestino().getId());
				pstmt.setObject(2, p.getFechaSolicitud()); 
				pstmt.setObject(3, p.getFechaEntrega());
				pstmt.setString(4, p.getEstado().toString());
				pstmt.setInt(5,  p.getNroOrden());
				
				for(Item i: p.getItems()) {
					if(i.getId() != null && i.getId() > 0) {
						pstmt2 = conn.prepareStatement(UPDATE_ITEM);
						pstmt2.setInt(1, i.getInsumo().getId());
						pstmt2.setInt(2, i.getCantidad());
						pstmt2.setInt(3, p.getNroOrden());
					}
					else {
						pstmt2 = conn.prepareStatement(INSERT_ITEM);
						pstmt2.setInt(1, p.getNroOrden());
						pstmt2.setInt(2,  i.getInsumo().getId());
						pstmt2.setInt(3,  i.getCantidad());
					}
				}
				
				if(p.getEstado() == Estado.PROCESADA) {
					if(p.getEnvio().getId() != null && p.getEnvio().getId() > 0) {
						pstmt3 = conn.prepareStatement(UPDATE_ENVIO);
						pstmt3.setInt(1, p.getEnvio().getRutaElegida().getId());
						pstmt3.setInt(2,  p.getEnvio().getCamionAsignado().getId());
						pstmt3.setDouble(3, p.getEnvio().getCosto());
						pstmt3.setInt(4, p.getNroOrden());
					}
					else {
						pstmt3 = conn.prepareStatement(INSERT_ENVIO);
						pstmt3.setInt(1, p.getNroOrden());
						pstmt3.setInt(2, p.getEnvio().getRutaElegida().getId());
						pstmt3.setInt(3,  p.getEnvio().getCamionAsignado().getId());
						pstmt3.setDouble(4, p.getEnvio().getCosto());
					}
						
				}
				pstmt.executeUpdate();
			}else {
				System.out.println("EJECUTA INSERT");
				pstmt= conn.prepareStatement(INSERT_PEDIDO);
				pstmt.setInt(1, p.getDestino().getId());
				pstmt.setObject(2, p.getFechaSolicitud()); 
				pstmt.setObject(3, p.getFechaEntrega());
				pstmt.setString(4, p.getEstado().toString());
				pstmt.executeUpdate();
				
				pstmt3 = conn.prepareStatement(SELECT_ALL_PEDIDO);
				rs = pstmt3.executeQuery();
				List<Integer> aux = new ArrayList<Integer>();
				while(rs.next()) {
					aux.add(rs.getInt("NUMERO_ORDEN"));
				}
				
				for(Item i: p.getItems()) {
						pstmt2 = conn.prepareStatement(INSERT_ITEM);
						pstmt2.setInt(1, aux.get(aux.size()-1)); 
						pstmt2.setInt(2,  i.getInsumo().getId());
						pstmt2.setInt(3,  i.getCantidad());
				}
			}
			pstmt2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt3!=null) pstmt3.close();
				if(pstmt2!=null) pstmt2.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
				if(conn!=null) conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	public void borrarPedido(Integer numero_orden) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		try {
			pstmt= conn.prepareStatement(DELETE_PEDIDO);
			pstmt.setInt(1, numero_orden);
			pstmt.execute();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Pedido> buscarTodos() {
		List<Pedido> lista = new ArrayList<Pedido>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ALL_PEDIDO);
			rs = pstmt.executeQuery();
			Pedido p = new Pedido();
			while(rs.next()) {
				p.setNroOrden(rs.getInt("NUMERO_ORDEN"));
				
				//PLANTA
				PlantaDaoMySql plantaDao = new PlantaDaoMySql();
				p.setDestino(plantaDao.buscarPorId(rs.getInt("PLANTA_DESTINO")));
				//
				
				p.setFechaSolicitud(rs.getDate("FECHA_SOLICITUD").toLocalDate());
				p.setFechaEntrega(rs.getDate("FECHA_ENTREGA").toLocalDate());
				p.setEstado(Estado.valueOf(rs.getString("ESTADO")));
				p.setItems(this.buscarPorId(p.getNroOrden()));
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				try{
					pstmt2 = conn.prepareStatement(SELECT_ID_ENVIO);
					rs2 = pstmt2.executeQuery();
					while(rs2.next()) {
						Envio e = new Envio();
						e.setId(rs2.getInt("ID"));
						e.setCosto(rs2.getDouble("COSTO"));
						
						//CAMION
						CamionDaoMySql camionDao = new CamionDaoMySql();
						e.setCamionAsignado(camionDao.buscarPorId(rs2.getInt("CAMION")));
						//
						
						//RUTA
						RutaDaoMySql rutaDao = new RutaDaoMySql();
						e.setRutaElegida(rutaDao.buscarPorId(rs2.getInt("RUTA")));
						//
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					try {
						if(rs2!=null) rs2.close();
						if(pstmt2!=null) pstmt2.close();				
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}	
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		System.out.println("Resultado "+lista);
		return lista;
	}

	private List<Item> buscarPorId(Integer id) {
		List<Item> lista = new ArrayList<Item>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ID_ITEM);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			Item i = new Item();
			while(rs.next()) {
				i.setId(rs.getInt("ID"));
				
				//INSUMO
				InsumoDaoMySql insumoDao = new InsumoDaoMySql();
				i.setInsumo(insumoDao.buscarPorId(rs.getInt("INSUMO")));
				//
				
				i.setCantidad(rs.getInt("CANTIDAD"));
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		return lista;
	}
	
	public List<Pedido> buscarPorEstado(Estado estado) {
		List<Pedido> lista = new ArrayList<Pedido>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ESTADO_PEDIDO);
			pstmt.setString(1, estado.toString());
			rs = pstmt.executeQuery();
			Pedido p = new Pedido();
			
			while(rs.next()) {
				p.setNroOrden(rs.getInt("NUMERO_ORDEN"));
				
				//PLANTA
				PlantaDaoMySql plantaDao = new PlantaDaoMySql();
				p.setDestino(plantaDao.buscarPorId(rs.getInt("PLANTA_DESTINO")));
				//
				
				p.setFechaSolicitud(rs.getDate("FECHA_SOLICITUD").toLocalDate());
				p.setFechaEntrega(rs.getDate("FECHA_ENTREGA").toLocalDate());
				p.setEstado(Estado.valueOf(rs.getString("ESTADO")));
				p.setItems(this.buscarPorId(p.getNroOrden()));
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				try{
					pstmt2 = conn.prepareStatement(SELECT_ID_ENVIO);
					rs2 = pstmt2.executeQuery();
					while(rs2.next()) {
						Envio e = new Envio();
						e.setId(rs2.getInt("ID"));
						e.setCosto(rs2.getDouble("COSTO"));
						
						//CAMION
						CamionDaoMySql camionDao = new CamionDaoMySql();
						e.setCamionAsignado(camionDao.buscarPorId(rs2.getInt("CAMION")));
						//
						
						//RUTA
						RutaDaoMySql rutaDao = new RutaDaoMySql();
						e.setRutaElegida(rutaDao.buscarPorId(rs2.getInt("RUTA")));
						//
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					try {
						if(rs2!=null) rs2.close();
						if(pstmt2!=null) pstmt2.close();				
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}	
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		System.out.println("Resultado "+lista);
		return lista;
	}

	public List<Pedido> buscarTodosPorPlanta(Integer id_planta) {
		List<Pedido> lista = new ArrayList<Pedido>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ALL_PEDIDO_PLANTA);
			pstmt.setInt(1, id_planta);
			rs = pstmt.executeQuery();
			Pedido p = new Pedido();
			while(rs.next()) {
				p.setNroOrden(rs.getInt("NUMERO_ORDEN"));
				
				//PLANTA
				PlantaDaoMySql plantaDao = new PlantaDaoMySql();
				p.setDestino(plantaDao.buscarPorId(rs.getInt("PLANTA_DESTINO")));
				//
				
				p.setFechaSolicitud(rs.getDate("FECHA_SOLICITUD").toLocalDate());
				p.setFechaEntrega(rs.getDate("FECHA_ENTREGA").toLocalDate());
				p.setEstado(Estado.valueOf(rs.getString("ESTADO")));
				p.setItems(this.buscarPorId(p.getNroOrden()));
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				try{
					pstmt2 = conn.prepareStatement(SELECT_ID_ENVIO);
					rs2 = pstmt2.executeQuery();
					while(rs2.next()) {
						Envio e = new Envio();
						e.setId(rs2.getInt("ID"));
						e.setCosto(rs2.getDouble("COSTO"));
						
						//CAMION
						CamionDaoMySql camionDao = new CamionDaoMySql();
						e.setCamionAsignado(camionDao.buscarPorId(rs2.getInt("CAMION")));
						//
						
						//RUTA
						RutaDaoMySql rutaDao = new RutaDaoMySql();
						e.setRutaElegida(rutaDao.buscarPorId(rs2.getInt("RUTA")));
						//
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					try {
						if(rs2!=null) rs2.close();
						if(pstmt2!=null) pstmt2.close();				
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}	
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		System.out.println("Resultado "+lista);
		return lista;
	}

}
