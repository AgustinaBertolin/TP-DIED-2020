package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.BD;
import dominio.Insumo;
import dominio.Insumo.UnidadDeMedida;
import dominio.InsumoGeneral;
import dominio.InsumoLiquido;

public class InsumoDaoMySql implements InsumoDao {

	private static final String SELECT_ALL_INSUMO_GENERAL = 
			"SELECT * FROM INSUMO, INSUMO_GENERAL"+
			"WHERE INSUMO.ID = INSUMO_GENERAL.ID";

	private static final String SELECT_ALL_INSUMO_LIQUIDO = 
			"SELECT * FROM INSUMO, INSUMO_LIQUIDO"+
			"WHERE INSUMO.ID = INSUMO_LIQUIDO.ID";
	
	private static final String INSERT_INSUMO =
			"INSERT INTO INSUMO (DESCRIPCION, UNIDAD_DE_MEDIDA, COSTO) VALUES (?,?,?)";
	
	private static final String INSERT_INSUMO_GENERAL = 
			"INSERT INTO INSUMO_GENERAL (ID, PESO) VALUES (?,?)";
	
	private static final String INSERT_INSUMO_LIQUIDO = 
			"INSERT INTO INSUMO_LIQUIDO (ID, DENSIDAD) VALUES (?,?)";
	
	private static final String UPDATE_INSUMO =
			" UPDATE INSUMO SET DESCRIPCION = ?, UNIDAD_DE_MEDIDA =? ,COSTO = ?"
			+ " WHERE ID = ?";
	
	private static final String UPDATE_INSUMO_GENERAL = 
			"UPDATE INSUMO_GENERAL SET PESO = ?"
			+ " WHERE ID = ?";

	private static final String UPDATE_INSUMO_LIQUIDO = 
			"UPDATE INSUMO_LIQUIDO SET DENSIDAD = ?"
			+ " WHERE ID = ?";
	
	private static final String SELECT_ID =
			"SELECT ID, DESCRIPCION, UNIDAD_DE_MEDIDA, COSTO, PESO, DENSIDAD FROM INSUMO, INSUMO_GENERAL, INSUMO_LIQUIDO " +
			"WHERE ID = ?";
	
	private static final String DELETE_INSUMO = 
			"DELETE FROM INSUMO " +
			"WHERE ID = ?";
	
	public Insumo saveOrUpdate(Insumo i) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		try {
			if(i.getId()!=null && i.getId()>0) {
				System.out.println("EJECUTA UPDATE");
				pstmt= conn.prepareStatement(UPDATE_INSUMO);
				pstmt.setString(1, i.getDescripcion());
				pstmt.setString(2, i.getUnidadDeMedida().toString());
				pstmt.setDouble(3, i.getCosto());
				pstmt.setInt(4, i.getId());
				
				if(i instanceof InsumoGeneral) {
					pstmt2 = conn.prepareStatement(UPDATE_INSUMO_GENERAL);
					pstmt2.setDouble(1, ((InsumoGeneral) i).getPeso());
					pstmt2.setInt(2, i.getId());
				}
				else {
					pstmt2 = conn.prepareStatement(UPDATE_INSUMO_LIQUIDO);
					pstmt2.setDouble(1, ((InsumoLiquido) i).getDensidad());
					pstmt2.setInt(2, i.getId());
				}
			}else {
				System.out.println("EJECUTA INSERT");
				pstmt= conn.prepareStatement(INSERT_INSUMO);
				pstmt.setString(1, i.getDescripcion());
				pstmt.setString(2, i.getUnidadDeMedida().toString());
				pstmt.setDouble(3, i.getCosto());
				if(i instanceof InsumoGeneral) {
					pstmt2 = conn.prepareStatement(INSERT_INSUMO_GENERAL);
					pstmt2.setInt(1, i.getId());
					pstmt2.setDouble(2, ((InsumoGeneral) i).getPeso());
				}
				else {
					pstmt2 = conn.prepareStatement(INSERT_INSUMO_LIQUIDO);
					pstmt2.setInt(1, i.getId());
					pstmt2.setDouble(2, ((InsumoLiquido) i).getDensidad());
				}
			}
			pstmt.executeUpdate();
			pstmt2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt2!=null) pstmt2.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	public Insumo buscarPorId(Integer id) {
		Insumo i = null;
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getObject("PESO") != null) {
					i = new InsumoGeneral();
					((InsumoGeneral) i).setPeso(rs.getDouble("PESO"));

				}
				else {
					i = new InsumoLiquido();
					((InsumoLiquido) i).setDensidad(rs.getDouble("DENSIDAD"));
				}
				i.setId(rs.getInt("ID"));
				i.setDescripcion(rs.getString("DESCRIPCION"));
				i.setCosto(rs.getDouble("COSTO"));
				i.setUnidadDeMedida(UnidadDeMedida.valueOf(rs.getString("UNIDAD_DE_MEDIDA")));
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
		return i.getId() != null && i.getId() > 0?  i : null;
	}

	public void borrarInsumo(Integer id) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		try {
			pstmt= conn.prepareStatement(DELETE_INSUMO);
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

	public List<Insumo> buscarTodos() {
		List<Insumo> lista = new ArrayList<Insumo>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		try {
			pstmt= conn.prepareStatement(SELECT_ALL_INSUMO_GENERAL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				InsumoGeneral i = new InsumoGeneral();
				i.setPeso(rs.getDouble("PESO"));
				i.setId(rs.getInt("ID"));
				i.setDescripcion(rs.getString("DESCRIPCION"));
				i.setUnidadDeMedida(UnidadDeMedida.valueOf(rs.getString("UNIDAD_DE_MEDIDA")));
				i.setCosto(rs.getDouble("COSTO"));
				lista.add(i);
			}			
			
			pstmt2 = conn.prepareStatement(SELECT_ALL_INSUMO_LIQUIDO);
			rs2 = pstmt2.executeQuery();
			while(rs2.next()) {
				InsumoLiquido i = new InsumoLiquido();
				i.setDensidad(rs2.getDouble("DENSIDAD"));
				i.setId(rs2.getInt("ID"));
				i.setDescripcion(rs2.getString("DESCRIPCION"));
				i.setUnidadDeMedida(UnidadDeMedida.valueOf(rs2.getString("UNIDAD_DE_MEDIDA")));
				i.setCosto(rs2.getDouble("COSTO"));
				lista.add(i);
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
