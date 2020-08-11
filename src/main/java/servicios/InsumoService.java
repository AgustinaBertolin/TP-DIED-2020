package servicios;

import java.util.List;

import dao.InsumoDaoMySql;
import dominio.Insumo;

public class InsumoService {

	private InsumoDaoMySql insumoDao = new InsumoDaoMySql();
	
	public Insumo saveOrUpdate(Insumo i, boolean update) {
		return this.insumoDao.saveOrUpdate(i, update);
	}
	
	public Insumo buscarPorId(Integer id) {
		return this.insumoDao.buscarPorId(id);
	}
	
	public void borrarInsumo(Integer id) {
		this.insumoDao.borrarInsumo(id);
	}
	
	public List<Insumo> buscarTodos(){
		return this.insumoDao.buscarTodos();
	}
}
