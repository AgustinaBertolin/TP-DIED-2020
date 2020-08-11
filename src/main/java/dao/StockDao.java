package dao;

import java.sql.Connection;
import java.util.List;



import dominio.Stock;

public interface StockDao {

	public Stock saveOrUpdate(Stock s, Integer id_planta);
	public List<Stock> buscarPorIdPlanta(Integer id);
	public void borrar(Integer id);
	public List<Stock> buscarTodos();
	
}
