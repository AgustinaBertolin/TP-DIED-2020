package dao;

import java.util.List;

import dominio.Insumo;

public interface InsumoDao {

	public Insumo saveOrUpdate(Insumo i, boolean update);
	public Insumo buscarPorId(Integer id);
	public void borrarInsumo(Integer id);
	public List<Insumo> buscarTodos();
}
