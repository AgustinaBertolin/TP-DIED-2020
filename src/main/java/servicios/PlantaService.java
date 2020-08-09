package servicios;

import java.util.List;

import dao.PlantaDao;
import dao.PlantaDaoMySql;
import dominio.Planta;

public class PlantaService {

	private PlantaDao plantaDao = new PlantaDaoMySql();

	public Planta crearPlanta(Planta p) {
		// si hay alguna regla de negocio que indque que no se 
		// puede agregar un camion si no se cumplen determinadas
		// condiciones en otras entidades o reglas 
		// se valida aquí
		return this.plantaDao.saveOrUpdate(p);
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
