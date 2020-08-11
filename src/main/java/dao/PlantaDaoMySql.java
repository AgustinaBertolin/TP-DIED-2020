package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.BD;
import dominio.Camion;
import dominio.Pedido;
import dominio.Planta;
import dominio.Planta.TipoPlanta;
import dominio.Stock;

public class PlantaDaoMySql implements PlantaDao {

	private static final String SELECT_ALL_PLANTA =
			"SELECT * FROM `tp_integrador`.`planta`";
	
	private static final String INSERT_PLANTA =
			"INSERT INTO `tp_integrador`.`planta` (NOMBRE,TIPO) VALUES (?,?)";

	private static final String INSERT_CAMION_PLANTA =
			"INSERT INTO `tp_integrador`.`camion_planta` (ID_PLANTA,ID_CAMION) VALUES (?,?)";
	
	private static final String UPDATE_PLANTA =
			" UPDATE `tp_integrador`.`planta` SET NOMBRE = ?, TIPO = ?"
			+ " WHERE ID = ?";
	
	private static final String UPDATE_PLANTA_CAMION =
			" UPDATE `tp_integrador`.`camion_planta` SET ID_CAMION = ?"
			+ " WHERE ID_PLANTA = ?";
	
	private static final String SELECT_ID =
			"SELECT * FROM `tp_integrador`.`planta` " +
			"WHERE ID = ?";
	
	private static final String DELETE_PLANTA = 
			"DELETE FROM `tp_integrador`.`planta` " +
			"WHERE ID = ?";
	
	private static final String UPDATE_ID =
			" UPDATE `tp_integrador`.`tabla_id` SET ID_PLANTA = ?"
			+ " WHERE ID = 1";
			
	
	public Planta saveOrUpdate(Planta p, boolean update) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		try {
			
			StockDaoMySql stockDao = new StockDaoMySql();
			PedidoDaoMySql pedidoDao = new PedidoDaoMySql();
			CamionDaoMySql camionDao = new CamionDaoMySql();
			
			if(update) {
				System.out.println("EJECUTA UPDATE");
				pstmt= conn.prepareStatement(UPDATE_PLANTA);
				pstmt.setString(1, p.getNombre());
				pstmt.setString(2, p.getTipoPlanta().toString());
				pstmt.setInt(3, p.getId());
				pstmt3= conn.prepareStatement(UPDATE_PLANTA_CAMION);
				pstmt3.setInt(2,  p.getId());
				
				for(Stock s: p.getStock()) {
					stockDao.saveOrUpdate(s, p.getId());
				}
				
				for(Pedido pe: p.getPedidosRealizados()) {
					pedidoDao.saveOrUpdate(pe, pe.isGuardado());
				}
				
				for(Camion c: p.getCamionesDisponibles()) {
					camionDao.saveOrUpdate(c, c.isGuardado());
					pstmt3.setInt(1, c.getId());
					pstmt3.executeUpdate();
					c.setGuardado(true);
				}
				
			}else {
				System.out.println("EJECUTA INSERT");
				pstmt= conn.prepareStatement(INSERT_PLANTA);
				pstmt.setString(1, p.getNombre());
				pstmt.setString(2, p.getTipoPlanta().toString());
				pstmt3= conn.prepareStatement(INSERT_CAMION_PLANTA);
				pstmt3.setInt(1,  p.getId());
				
				for(Stock s: p.getStock()) {
					stockDao.saveOrUpdate(s, p.getId());
				}
				for(Pedido pe: p.getPedidosRealizados()) {
					pedidoDao.saveOrUpdate(pe, pe.isGuardado());
				}
				
				for(Camion c: p.getCamionesDisponibles()) {
					camionDao.saveOrUpdate(c, c.isGuardado());
					pstmt3.setInt(2, c.getId());
					pstmt3.executeUpdate();
					c.setGuardado(true);
				}
				pstmt2= conn.prepareStatement(UPDATE_ID);
				pstmt2.setInt(1, p.getId());
				pstmt2.executeUpdate();
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(pstmt2!=null) pstmt2.close();
				if(conn!=null) conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	public Planta buscarPorId(Integer id) {
		Planta p = new Planta();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			StockDaoMySql stockDao = new StockDaoMySql();
			PedidoDaoMySql pedidoDao = new PedidoDaoMySql();
			CamionDaoMySql camionDao = new CamionDaoMySql();
			
			while(rs.next()) {
				p.setId(rs.getInt("ID"));
				p.setNombre(rs.getString("NOMBRE"));
				p.setTipoPlanta(TipoPlanta.valueOf(rs.getString("TIPO")));
			}			
			p.setStock(stockDao.buscarPorIdPlanta(p.getId())); 
			p.setCamionesDisponibles(camionDao.buscarTodosPorPlanta(p.getId()));
			p.setPedidosRealizados(pedidoDao.buscarTodosPorPlanta(p.getId()));
			for(Pedido e: p.getPedidosRealizados()) {
				e.setDestino(p);
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
		return p.getId() != null && p.getId() > 0?  p : null;
	}

	public void borrar(Integer id) {

		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		
		try {
			
			pstmt= conn.prepareStatement(DELETE_PLANTA);
			pstmt.setInt(1, id);
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

	public List<Planta> buscarTodos() {
		List<Planta> lista = new ArrayList<Planta>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ALL_PLANTA);
			rs = pstmt.executeQuery();
			StockDaoMySql stockDao = new StockDaoMySql();
			PedidoDaoMySql pedidoDao = new PedidoDaoMySql();
			CamionDaoMySql camionDao = new CamionDaoMySql();
			
			while(rs.next()) {
				Planta p = new Planta();
				p.setId(rs.getInt("ID"));
				p.setNombre(rs.getString("NOMBRE"));
				p.setTipoPlanta(TipoPlanta.valueOf(rs.getString("TIPO")));
				p.setStock(stockDao.buscarPorIdPlanta(p.getId()));
				p.setCamionesDisponibles(camionDao.buscarTodosPorPlanta(p.getId()));
				p.setPedidosRealizados(pedidoDao.buscarTodosPorPlanta(p.getId()));
				
				for(Pedido e: p.getPedidosRealizados()) {
					e.setDestino(p);
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
