package servicios;

import java.util.List;

import dao.PedidoDaoMySql;
import dominio.Pedido;

public class PedidoService {

	private PedidoDaoMySql pedidoDao = new PedidoDaoMySql();
	
	public Pedido saveOrUpdate(Pedido p, boolean update) {
		return this.pedidoDao.saveOrUpdate(p, update);
	}
	
	public void borrarPedido(Integer id) {
		this.pedidoDao.borrarPedido(id);
	}
	
	public List<Pedido> buscarTodosPorPlanta(Integer id_planta){
		return this.pedidoDao.buscarTodosPorPlanta(id_planta);
	}
	
}
