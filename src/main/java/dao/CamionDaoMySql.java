package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.BD;
import dominio.Camion;


public class CamionDaoMySql implements CamionDao {


	private static final String SELECT_ALL_CAMION =
			"SELECT ID,PATENTE,MARCA,MODELO,KM FROM `tp_integrador`.`camion`";
	
	private static final String INSERT_CAMION =
			"INSERT INTO `tp_integrador`.`camion` (PATENTE,MARCA,MODELO,KM) VALUES (?,?,?,?)";
	
	private static final String UPDATE_CAMION =
			" UPDATE `tp_integrador`.`camion` SET PATENTE = ?, MARCA =? ,MODELO = ? , KM =? "
			+ " WHERE ID = ?";
	
	private static final String SELECT_ID =
			"SELECT ID, PATENTE, MARCA, MODELO, KM FROM `tp_integrador`.`camion` " +
			"WHERE ID = ?";
	
	private static final String SELECT_ALL_PLANTA =
			"SELECT ID, PATENTE, MARCA, MODELO, KM FROM `tp_integrador`.`camion`, `tp_integrador`.`camion_planta` " +
			"WHERE `tp_integrador`.`camion`.ID = `tp_integrador`.`camion_planta`.ID_CAMION AND ID_PLANTA = ?";
	
	private static final String DELETE_CAMION = 
			"DELETE FROM `tp_integrador`.`camion` " +
			"WHERE ID = ?";
	
	private static final String UPDATE_ID =
			" UPDATE `tp_integrador`.`tabla_id` SET ID_CAMION = ?"
			+ " WHERE ID = 1";
	
	public Camion saveOrUpdate(Camion c, boolean update) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		try {
			if(update) {
				System.out.println("EJECUTA UPDATE");
				pstmt= conn.prepareStatement(UPDATE_CAMION);
				pstmt.setString(1, c.getPatente());
				pstmt.setString(2, c.getMarca());
				pstmt.setString(3, c.getModelo());
				pstmt.setDouble(4, c.getKmRecorridos());
				pstmt.setInt(5, c.getId());
			}else {
				System.out.println("EJECUTA INSERT");
				pstmt= conn.prepareStatement(INSERT_CAMION);
				pstmt.setString(1, c.getPatente());
				pstmt.setString(2, c.getMarca());
				pstmt.setString(3, c.getModelo());
				pstmt.setDouble(4, c.getKmRecorridos());
				
				pstmt2= conn.prepareStatement(UPDATE_ID);
				pstmt2.setInt(1, c.getId());
			}
			pstmt.executeUpdate();
			pstmt2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(pstmt2!=null) pstmt.close();
				if(conn!=null) conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return c;
	}

	public Camion buscarPorId(Integer id) {
		Camion c = new Camion();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				c.setId(rs.getInt("ID"));
				c.setMarca(rs.getString("MARCA"));
				c.setModelo(rs.getString("MODELO"));
				c.setPatente(rs.getString("PATENTE"));
				c.setKmRecorridos(rs.getDouble("KM"));
				c.setGuardado(true);
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
		return c.getId() != null && c.getId() > 0?  c : null;
	}

	public void borrar(Integer id) {
		// TODO Auto-generated method stub
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		try {
			pstmt= conn.prepareStatement(DELETE_CAMION);
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

	public List<Camion> buscarTodos() {
		List<Camion> lista = new ArrayList<Camion>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ALL_CAMION);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Camion c = new Camion();
				c.setId(rs.getInt("ID"));
				c.setMarca(rs.getString("MARCA"));
				c.setModelo(rs.getString("MODELO"));
				c.setPatente(rs.getString("PATENTE"));
				c.setKmRecorridos(rs.getDouble("KM"));
				c.setGuardado(true);
				lista.add(c);
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

	public List<Camion> buscarTodosPorPlanta(Integer id_planta) {
		List<Camion> lista = new ArrayList<Camion>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ALL_PLANTA);
			pstmt.setInt(1, id_planta);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Camion c = new Camion();
				c.setId(rs.getInt("ID"));
				c.setMarca(rs.getString("MARCA"));
				c.setModelo(rs.getString("MODELO"));
				c.setPatente(rs.getString("PATENTE"));
				c.setKmRecorridos(rs.getDouble("KM"));
				c.setGuardado(true);
				lista.add(c);
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
