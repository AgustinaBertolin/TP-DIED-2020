package servicios;

import java.util.List;

import dao.RutaDaoMySql;
import dominio.Ruta;

public class RutaService {

	RutaDaoMySql rutaDao = new RutaDaoMySql();
	
	public Ruta saveOrUpdate(Ruta r, boolean update) {
		return this.rutaDao.saveOrUpdate(r, update);
	}
	
	public Ruta buscarPorId(Integer id) {
		return this.rutaDao.buscarPorId(id);
	}
	
	public void borrar(Integer id) {
		this.rutaDao.borrar(id);
	}
	
	public List<Ruta> buscarTodos(){
		return this.rutaDao.buscarTodos();
	}
	
	public List<Ruta> buscarTodosPorPedido(Integer id){
		return this.rutaDao.buscarTodosPorPedido(id);
	}
	
}
