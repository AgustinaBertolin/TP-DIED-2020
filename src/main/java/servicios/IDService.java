package servicios;

import java.util.List;

import dao.IdDao;
import dao.IdDaoMySql;

public class IDService {

	private IdDao idDao = new IdDaoMySql();
	
	public List<Integer> getIds(){
		return this.idDao.getIDs();
	}
}
