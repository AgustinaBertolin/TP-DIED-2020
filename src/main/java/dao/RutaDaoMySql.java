package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.BD;
import dominio.Ruta;

public class RutaDaoMySql implements RutaDao {

	private static final String UPDATE_RUTA =
			" UPDATE RUTA SET PLANTA_DESTINO = ?, PLANTA_ORIGEN = ?, DISTANCIA_KM = ?, DURACION = ?, PESO_MAXIMO_POR_DIA = ?" + 
			" WHERE ID = ?";
	
	private static final String INSERT_RUTA =
			" INSERT INTO RUTA (ID, PLANTA_DESTINO, PLANTA_ORIGEN, DISTANCIA_KM, DURACION, PESO_MAXIMO_POR_DIA) VALUES (?,?,?,?,?,?)";
	
	private static final String DELETE_RUTA =
			" DELETE FROM RUTA"+
			" WHERE ID = ?";
	
	private static final String SELECT_ID = 
			" SELECT * FROM RUTA"+
			" WHERE ID = ?";
	
	private static final String SELECT_ALL_RUTA =
			" SELECT * FROM RUTA";
	
	public Ruta saveOrUpdate(Ruta r) {
		
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		
		try {
			if(r.getId()!=null && r.getId()>0) {
				
				System.out.println("EJECUTA UPDATE");
				
				pstmt= conn.prepareStatement(UPDATE_RUTA);
				pstmt.setInt(1, r.getDestino().getId());
				pstmt.setInt(2, r.getOrigen().getId()); 
				pstmt.setDouble(3, r.getDistanciaKM());
				pstmt.setTime(4, r.getDuracion());
				pstmt.setDouble(5,  r.getPesoMaxPorDia());
				pstmt.setInt(6,  r.getId());
			
			}else {
				
				System.out.println("EJECUTA INSERT");
				
				pstmt= conn.prepareStatement(INSERT_RUTA);
				pstmt.setInt(1,  r.getId());
				pstmt.setInt(2, r.getDestino().getId());
				pstmt.setInt(3, r.getOrigen().getId()); 
				pstmt.setDouble(4, r.getDistanciaKM());
				pstmt.setTime(5, r.getDuracion());
				pstmt.setDouble(6,  r.getPesoMaxPorDia());
				
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
		return r;
	}

	public Ruta buscarPorId(Integer id) {
		Ruta r = null;
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt= conn.prepareStatement(SELECT_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				r = new Ruta();
				r.setId(rs.getInt("ID"));
				
				//PLANTAS
				PlantaDaoMySql plantaDao = new PlantaDaoMySql();
				r.setDestino(plantaDao.buscarPorId(rs.getInt("PLANTA_DESTINO")));
				r.setOrigen(plantaDao.buscarPorId(rs.getInt("PLANTA_ORIGEN")));
				//
				
				r.setDistanciaKM(rs.getDouble("DISTANCIA_KM"));
				r.setDuracion(rs.getTime("DURACION"));
				r.setPesoMaxPorDia(rs.getDouble("PESO_MAXIMO_POR_DIA"));
				
				
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
		System.out.println("Resultado "+r);
		return r;
	}

	public void borrar(Integer id) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		
		try {
			pstmt= conn.prepareStatement(DELETE_RUTA);
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

	public List<Ruta> buscarTodos() {
		List<Ruta> lista = new ArrayList<Ruta>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt= conn.prepareStatement(SELECT_ALL_RUTA);
			rs = pstmt.executeQuery();
			Ruta r = new Ruta();
			
			while(rs.next()) {
				
				r.setId(rs.getInt("ID"));
				
				//PLANTAS
				PlantaDaoMySql plantaDao = new PlantaDaoMySql();
				r.setDestino(plantaDao.buscarPorId(rs.getInt("PLANTA_DESTINO")));
				r.setOrigen(plantaDao.buscarPorId(rs.getInt("PLANTA_ORIGEN")));
				//
				
				r.setDistanciaKM(rs.getDouble("DISTANCIA_KM"));
				r.setDuracion(rs.getTime("DURACION"));
				r.setPesoMaxPorDia(rs.getDouble("PESO_MAXIMO_POR_DIA"));
				
				lista.add(r);
				
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
