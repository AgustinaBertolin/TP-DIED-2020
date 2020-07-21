package dao;

import java.util.List;

import dominio.Planta;

public interface PlantaDao {

	public Planta saveOrUpdate(Planta c);
	public Planta buscarPorId(Integer id);
	public void borrar(Integer id);
	public List<Planta> buscarTodos();
}
