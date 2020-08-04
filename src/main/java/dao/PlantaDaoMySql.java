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
import dominio.Stock;

public class PlantaDaoMySql implements PlantaDao {

	private static final String SELECT_ALL_PLANTA =
			"SELECT ID,NOMBRE FROM PLANTA";
	
	private static final String INSERT_PLANTA =
			"INSERT INTO PLANTA (ID,NOMBRE) VALUES (?,?)";
	
	private static final String UPDATE_PLANTA =
			" UPDATE PLANTA SET NOMBRE = ?"
			+ " WHERE ID = ?";
	
	private static final String SELECT_ID =
			"SELECT ID, NOMBRE FROM PLANTA " +
			"WHERE ID = ?";
	
	private static final String DELETE_PLANTA = 
			"DELETE FROM PLANTA " +
			"WHERE ID = ?";
	
	public Planta saveOrUpdate(Planta p) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		try {
			
			StockDaoMySql stockDao = new StockDaoMySql();
			PedidoDaoMySql pedidoDao = new PedidoDaoMySql();
			CamionDaoMySql camionDao = new CamionDaoMySql();
			
			if(p.getId()!=null && p.getId()>0) {
				System.out.println("EJECUTA UPDATE");
				pstmt= conn.prepareStatement(UPDATE_PLANTA);
				pstmt.setString(2, p.getNombre());
				
				for(Stock s: p.getStock()) {
					stockDao.saveOrUpdate(s, p.getId());
				}
				
				for(Pedido pe: p.getPedidosRealizados()) {
					pedidoDao.saveOrUpdate(pe);
				}
				
				for(Camion c: p.getCamionesDisponibles()) {
					camionDao.saveOrUpdate(c);
				}
				
			}else {
				System.out.println("EJECUTA INSERT");
				pstmt= conn.prepareStatement(INSERT_PLANTA);
				pstmt.setInt(1, p.getId());
				pstmt.setString(2, p.getNombre());
				
				for(Stock s: p.getStock()) {
					stockDao.saveOrUpdate(s, p.getId());
				}
				
			}
			pstmt.executeUpdate();
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
				p.setStock(stockDao.buscarPorIdPlanta(p.getId()));
				p.setCamionesDisponibles(camionDao.buscarTodosPorPlanta(p.getId()));
				p.setPedidosRealizados(pedidoDao.buscarTodosPorPlanta(p.getId()));
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
				p.setStock(stockDao.buscarPorIdPlanta(p.getId()));
				p.setCamionesDisponibles(camionDao.buscarTodosPorPlanta(p.getId()));
				p.setPedidosRealizados(pedidoDao.buscarTodosPorPlanta(p.getId()));
				
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
