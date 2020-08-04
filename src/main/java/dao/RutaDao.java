package dao;

import java.util.List;

import dominio.Ruta;

public interface RutaDao {

	public Ruta saveOrUpdate(Ruta r);
	public Ruta buscarPorId(Integer id);
	public void borrar(Integer id);
	public List<Ruta> buscarTodos();
	
}
