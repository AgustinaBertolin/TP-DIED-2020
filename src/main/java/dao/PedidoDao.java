package dao;

import java.util.List;

import dominio.Pedido;
import dominio.Pedido.Estado;

public interface PedidoDao {

	public Pedido saveOrUpdate(Pedido p);
	public List<Pedido> buscarPorEstado(Estado estado);
	public void borrarPedido(Integer id);
	public List<Pedido> buscarTodos();
	public List<Pedido> buscarTodosPorPlanta(Integer id_planta);
	
}
