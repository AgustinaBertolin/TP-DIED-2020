package servicios;

import java.util.List;

import dao.CamionDao;
import dao.CamionDaoMySql;
import dominio.Camion;

public class CamionService {

	private CamionDao camionDao = new CamionDaoMySql();

	public Camion crearCamion(Camion c, boolean update) {
			return this.camionDao.saveOrUpdate(c, update);
	}
	
	public List<Camion> buscarTodos() {
		return camionDao.buscarTodos();
	}
	
	public void borrarCamion(Camion c) {
		this.camionDao.borrar(c.getId());
	}
	
	public Camion buscarCamion(Integer id) {  //que atributos????
		return this.camionDao.buscarPorId(id);
	}
}
