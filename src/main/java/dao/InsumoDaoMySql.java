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
			"SELECT * FROM `tp_integrador`.`insumo`";

	private static final String INSERT_INSUMO =
			"INSERT INTO `tp_integrador`.`insumo` (DESCRIPCION, UNIDAD_DE_MEDIDA, COSTO, PESO, DENSIDAD) VALUES (?,?,?,?,?)";
	
	private static final String UPDATE_INSUMO =
			" UPDATE `tp_integrador`.`insumo` SET DESCRIPCION = ?, UNIDAD_DE_MEDIDA =? ,COSTO = ?, SET PESO = ?, SET DENSIDAD = ?,"
			+ " WHERE ID = ?";
		
	private static final String SELECT_ID =
			"SELECT * FROM `tp_integrador`.`insumo`" +
			"WHERE ID = ?";
	
	private static final String DELETE_INSUMO = 
			"DELETE FROM `tp_integrador`.`insumo` " +
			"WHERE ID = ?";
	
	private static final String UPDATE_ID =
			" UPDATE `tp_integrador`.`tabla_id` SET ID_INSUMO = ?"
			+ " WHERE ID = 1";
	
	public Insumo saveOrUpdate(Insumo i, boolean update) {
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		try {
			if(update) {
				System.out.println("EJECUTA UPDATE");
				pstmt= conn.prepareStatement(UPDATE_INSUMO);
				pstmt.setString(1, i.getDescripcion());
				pstmt.setString(2, i.getUnidadDeMedida().toString());
				pstmt.setDouble(3, i.getCosto());
				pstmt.setInt(6, i.getId());
				
				if(i instanceof InsumoGeneral) {
					pstmt.setDouble(4, ((InsumoGeneral) i).getPeso());
					pstmt.setDouble(5, 0);
				}
				else {
					pstmt.setDouble(5, ((InsumoLiquido) i).getDensidad());
					pstmt.setDouble(4, 0);
				}
			}else {
				System.out.println("EJECUTA INSERT");
				pstmt= conn.prepareStatement(INSERT_INSUMO);
				pstmt.setString(1, i.getDescripcion());
				pstmt.setString(2, i.getUnidadDeMedida().toString());
				pstmt.setDouble(3, i.getCosto());
				if(i instanceof InsumoGeneral) {
					pstmt.setDouble(4, ((InsumoGeneral) i).getPeso());
					pstmt.setDouble(5, 0);
				}
				else {
					pstmt.setDouble(5, ((InsumoLiquido) i).getDensidad());
					pstmt.setDouble(4, 0);
				}
				
				pstmt2= conn.prepareStatement(UPDATE_ID);
				pstmt2.setInt(1, i.getId());
		
			}
			pstmt.executeUpdate();
			pstmt2.executeUpdate();
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
				if(rs.getDouble("PESO") != 0d) {
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
		try {
			pstmt= conn.prepareStatement(SELECT_ALL_INSUMO_GENERAL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Insumo i;
				if(rs.getDouble("PESO") != 0d) {
					i = new InsumoGeneral();
					((InsumoGeneral)i).setPeso(rs.getDouble("PESO"));
				}
				else {
					i = new InsumoLiquido();
					((InsumoLiquido)i).setDensidad(rs.getDouble("DENSIDAD"));
				}
				i.setId(rs.getInt("ID"));
				i.setDescripcion(rs.getString("DESCRIPCION"));
				i.setUnidadDeMedida(UnidadDeMedida.valueOf(rs.getString("UNIDAD_DE_MEDIDA")));
				i.setCosto(rs.getDouble("COSTO"));
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
