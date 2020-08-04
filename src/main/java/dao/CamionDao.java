package dao;

import java.util.List;

import dominio.Camion;

public interface CamionDao {

	public Camion saveOrUpdate(Camion c);
	public Camion buscarPorId(Integer id);
	public void borrar(Integer id);
	public List<Camion> buscarTodos();
	public List<Camion> buscarTodosPorPlanta(Integer id_planta);
	
}
