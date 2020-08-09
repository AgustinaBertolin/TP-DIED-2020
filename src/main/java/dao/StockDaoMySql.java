package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.BD;
import dominio.Stock;

public class StockDaoMySql implements StockDao {

	private static final String UPDATE_STOCK =
			" UPDATE `tp_integrador`.`stock` SET ID_PLANTA = ?, INSUMO = ?, CANTIDAD = ?, PUNTO_REPOSICION = ?" + 
			" WHERE ID_REGISTRO = ?";
	
	private static final String INSERT_STOCK =
			" INSERT INTO `tp_integrador`.`stock` (ID_REGISTRO, ID_PLANTA, INSUMO, CANTIDAD, PUNTO_REPOSICION) VALUES (?,?,?,?,?)";
	
	private static final String SELECT_ID = 
			" SELECT * FROM `tp_integrador`.`stock`"+
			" WHERE ID_PLANTA = ?";
	
	private static final String SELECT_ALL = 
			" SELECT * FROM `tp_integrador`.`stock`";
	
	private static final String DELETE_STOCK =
			" DELETE FROM `tp_integrador`.`stock`"+
			" WHERE ID = ?";
	
	public Stock saveOrUpdate(Stock s, Integer id_planta) {

		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		
		try {
			if(s.getIdRegistro() !=null && s.getIdRegistro() >0) {
				
				System.out.println("EJECUTA UPDATE");
				
				pstmt= conn.prepareStatement(UPDATE_STOCK);
				pstmt.setInt(1, id_planta);
				pstmt.setInt(2, s.getInsumo().getId()); 
				pstmt.setInt(3, s.getCantidad());
				pstmt.setInt(4, s.getPuntoDeReposicion());
				pstmt.setInt(5,  s.getIdRegistro());
			
			}else {
				
				System.out.println("EJECUTA INSERT");
				
				pstmt= conn.prepareStatement(INSERT_STOCK);
				pstmt.setInt(1,  s.getIdRegistro());
				pstmt.setInt(2, id_planta);
				pstmt.setInt(3, s.getInsumo().getId()); 
				pstmt.setInt(4, s.getCantidad());
				pstmt.setInt(5, s.getPuntoDeReposicion());
				
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
		return s;
	}

	public List<Stock> buscarPorIdPlanta(Integer id) {
		List<Stock> lista = new ArrayList<Stock>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt= conn.prepareStatement(SELECT_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Stock s = new Stock();
				s.setIdRegistro(rs.getInt("ID_REGISTRO"));
				s.setCantidad(rs.getInt("CANTIDAD"));
				s.setPuntoDeReposicion(rs.getInt("PUNTO_REPOSICION"));
				
				//INSUMO
				InsumoDaoMySql insumoDao = new InsumoDaoMySql();
				s.setInsumo(insumoDao.buscarPorId(rs.getInt("INSUMO")));
				//
				
				lista.add(s);
				
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

	public void borrar(Integer id) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		
		try {
			pstmt= conn.prepareStatement(DELETE_STOCK);
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

	public List<Stock> buscarTodos() {
		List<Stock> lista = new ArrayList<Stock>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt= conn.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Stock s = new Stock();
				s.setIdRegistro(rs.getInt("ID_REGISTRO"));
				s.setCantidad(rs.getInt("CANTIDAD"));
				s.setPuntoDeReposicion(rs.getInt("PUNTO_REPOSICION"));
				
				//INSUMO
				InsumoDaoMySql insumoDao = new InsumoDaoMySql();
				s.setInsumo(insumoDao.buscarPorId(rs.getInt("INSUMO")));
				//
				
				lista.add(s);
				
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
