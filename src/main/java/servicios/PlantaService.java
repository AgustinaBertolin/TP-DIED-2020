package servicios;

import java.util.List;

import dao.PlantaDao;
import dao.PlantaDaoMySql;
import dominio.Planta;

public class PlantaService {

	private PlantaDao plantaDao = new PlantaDaoMySql();

	public Planta crearPlanta(Planta p, boolean update) {
		return this.plantaDao.saveOrUpdate(p, update);
	}
	
	public List<Planta> buscarTodos() {
		return plantaDao.buscarTodos();
	}
	
	public void borrarPlanta(Integer id) {
		this.plantaDao.borrar(id);
	}
	
	public Planta buscarPlanta(Integer id) {  //que atributos????
		return this.plantaDao.buscarPorId(id);
	}
}
