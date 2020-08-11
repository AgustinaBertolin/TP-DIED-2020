package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.BD;

public class IdDaoMySql implements IdDao {
	
	private static final String INSERT_ID =
			"INSERT INTO `tp_integrador`.`tabla_id` (ID_PEDIDO, ID_CAMION, ID_PLANTA, ID_INSUMO, ID_RUTA) VALUES (?,?,?,?,?)";

	private static final String SELECT_ID =
			"SELECT ID_PEDIDO, ID_CAMION, ID_PLANTA, ID_INSUMO, ID_RUTA FROM `tp_integrador`.`tabla_id` " +
			"WHERE ID = ?";

	@Override
	public List<Integer> getIDs() {
		List<Integer> r = new ArrayList<Integer>();
		Connection conn = BD.getConexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			boolean i = false;
			pstmt= conn.prepareStatement(SELECT_ID);
			pstmt.setInt(1, 1);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				r.add(rs.getInt("ID_PLANTA"));
				r.add(rs.getInt("ID_CAMION"));
				r.add(rs.getInt("ID_PEDIDO"));
				r.add(rs.getInt("ID_INSUMO"));
				r.add(rs.getInt("ID_RUTA"));
				i = true;
			}		
			if(!i) {
				r.add(0);
				r.add(0);
				r.add(0);
				r.add(0);
				r.add(0);
				pstmt2 = conn.prepareStatement(INSERT_ID);
				pstmt2.setInt(1, 0);
				pstmt2.setInt(2, 0);
				pstmt2.setInt(3, 0);
				pstmt2.setInt(4, 0);
				pstmt2.setInt(5, 0);
				pstmt2.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try { 
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(pstmt2!=null) pstmt.close();
				if(conn!=null) conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		return r;
	}

}
