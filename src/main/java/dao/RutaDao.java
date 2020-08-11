package dao;

import java.util.List;

import dominio.Ruta;

public interface RutaDao {

	public Ruta saveOrUpdate(Ruta r, boolean update);
	public Ruta buscarPorId(Integer id);
	public void borrar(Integer id);
	public List<Ruta> buscarTodos();
	public List<Ruta> buscarTodosPorPedido(Integer id);
}
