package servicios;

import java.util.List;

import dao.PedidoDaoMySql;
import dominio.Pedido;
import dominio.Pedido.Estado;

public class PedidoService {

	private PedidoDaoMySql pedidoDao = new PedidoDaoMySql();
	
	public Pedido saveOrUpdate(Pedido p) {
		return this.pedidoDao.saveOrUpdate(p);
	}
	
	public List<Pedido> buscarPorEstado(Estado estado){
		return this.pedidoDao.buscarPorEstado(estado);
	}
	
	public void borrarPedido(Integer id) {
		this.pedidoDao.borrarPedido(id);
	}
	
	public List<Pedido> buscarTodos(){
		return this.pedidoDao.buscarTodos();
	}
	
	public List<Pedido> buscarTodosPorPlanta(Integer id_planta){
		return this.pedidoDao.buscarTodosPorPlanta(id_planta);
	}
	
}
