package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.BD;
import dominio.Envio;
import dominio.Item;
import dominio.Pedido;
import dominio.Pedido.Estado;
import dominio.Ruta;

public class PedidoDaoMySql implements PedidoDao {
	
	private static final String UPDATE_RUTA_PEDIDO =
			"UPDATE ID_RUTA FROM `tp_integrador`.`ruta_pedido` " +
			"WHERE  ID_PEDIDO = ?";
	
	private static final String INSERT_RUTA_PEDIDO =
			" INSERT INTO `tp_integrador`.`ruta_pedido` (ID_PEDIDO, ID_RUTA) VALUES (?, ?)";
	
	private static final String SELECT_ALL_PEDIDO_PLANTA =
			" SELECT * FROM `tp_integrador`.`pedido`"+
			" WHERE PLANTA_DESTINO = ?";
	
	private static final String SELECT_ID_ITEM =  //BUSCAR INSUMOS?
			" SELECT * FROM `tp_integrador`.`item`"+
			" WHERE NUMERO_ORDEN = ?";
	
	private static final String SELECT_ID_ENVIO = //BUSCAR RUTA Y CAMION??
			" SELECT * FROM `tp_integrador`.`envio`"+
			" WHERE NUMERO_ORDEN = ?";
	
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
	
	private static final String UPDATE_ID =
			" UPDATE `tp_integrador`.`tabla_id` SET ID_PEDIDO = ?"
			+ " WHERE ID = 1";
	
	public Pedido saveOrUpdate(Pedido p, boolean update) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		try {
			if(update) {
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
					pstmt2.executeUpdate();
				}
				 
				if(p.getEstado() == Estado.PROCESADA) {
					if(p.getEnvio().getId() != null && p.getEnvio().getId() > 0) {
						pstmt4 = conn.prepareStatement(UPDATE_RUTA_PEDIDO);
						pstmt4.setInt(2, p.getNroOrden());
						for(Ruta r: p.getEnvio().getRutaElegida()) {
							pstmt4.setInt(1, r.getId());
							pstmt4.executeUpdate();
						}
						pstmt3 = conn.prepareStatement(UPDATE_ENVIO);
						pstmt3.setInt(2,  p.getEnvio().getCamionAsignado().getId());
						pstmt3.setDouble(3, p.getEnvio().getCosto());
						pstmt3.setInt(4, p.getNroOrden());
					}
					else {
						pstmt3 = conn.prepareStatement(INSERT_ENVIO);
						pstmt3.setInt(1, p.getNroOrden());
						pstmt4 = conn.prepareStatement(INSERT_RUTA_PEDIDO);
						pstmt4.setInt(1, p.getNroOrden());
						for(Ruta r: p.getEnvio().getRutaElegida()) {
							pstmt4.setInt(2, r.getId());
							pstmt4.executeUpdate();
						}
						pstmt3.setInt(3,  p.getEnvio().getCamionAsignado().getId());
						pstmt3.setDouble(4, p.getEnvio().getCosto());
					}
						
					pstmt3.executeUpdate();
				}
			}else {
				System.out.println("EJECUTA INSERT");
				pstmt= conn.prepareStatement(INSERT_PEDIDO);
				pstmt.setInt(1, p.getDestino().getId());
				pstmt.setObject(2, p.getFechaSolicitud()); 
				pstmt.setObject(3, p.getFechaEntrega());
				pstmt.setString(4, p.getEstado().toString());
				
				for(Item i: p.getItems()) {
						pstmt2 = conn.prepareStatement(INSERT_ITEM);
						pstmt2.setInt(1, p.getNroOrden()); 
						pstmt2.setInt(2,  i.getInsumo().getId());
						pstmt2.setInt(3,  i.getCantidad());
						pstmt2.executeUpdate();
				}
				
				pstmt3= conn.prepareStatement(UPDATE_ID);
				pstmt3.setInt(1, p.getNroOrden());
				pstmt3.executeUpdate();
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt4!=null) pstmt4.close();
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
				p.setFechaSolicitud(rs.getDate("FECHA_SOLICITUD").toLocalDate());
				p.setFechaEntrega(rs.getDate("FECHA_ENTREGA").toLocalDate());
				p.setEstado(Estado.valueOf(rs.getString("ESTADO")));
				p.setItems(this.buscarPorId(p.getNroOrden()));
				p.setGuardado(true);
				
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				try{
					pstmt2 = conn.prepareStatement(SELECT_ID_ENVIO);
					pstmt2.setInt(1, p.getNroOrden());
					rs2 = pstmt2.executeQuery();
					
					while(rs2.next()) {
						Envio e = new Envio();
						e.setId(rs2.getInt("ID"));
						
						//CAMION
						CamionDaoMySql camionDao = new CamionDaoMySql();
						e.setCamionAsignado(camionDao.buscarPorId(rs2.getInt("CAMION")));
						//
						
						//RUTA
						RutaDaoMySql rutaDao = new RutaDaoMySql();
						e.setRutaElegida(rutaDao.buscarTodosPorPedido(p.getNroOrden()));
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
				lista.add(p);
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
