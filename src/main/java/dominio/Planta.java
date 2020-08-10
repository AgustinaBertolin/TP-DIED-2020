package dominio;

import java.util.ArrayList;
import java.util.List;

public class Planta {

	public enum TipoPlanta {
		Acopio_Puerto, Produccion, Acopio_Final; 
	}
	
	private Integer id;
	private String nombre;
	private List<Camion> camionesDisponibles;
	private List<Pedido> pedidosRealizados;
	private List<Stock> stock;
	private TipoPlanta tipoPlanta;
	
	public Planta(){
		this.camionesDisponibles = new ArrayList<Camion>();
		this.pedidosRealizados = new ArrayList<Pedido>();
		this.stock = new ArrayList<Stock>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Camion> getCamionesDisponibles() {
		return camionesDisponibles;
	}
	
	public void setCamionesDisponibles(List<Camion> camionesDisponibles) {
		this.camionesDisponibles = camionesDisponibles;
	}

	public List<Pedido> getPedidosRealizados() {
		return pedidosRealizados;
	}

	public void setPedidosRealizados(List<Pedido> pedidosRealizados) {
		this.pedidosRealizados = pedidosRealizados;
	}

	public List<Stock> getStock() {
		return stock;
	}

	public void setStock(List<Stock> stock) {
		this.stock = stock;
	}

	public TipoPlanta getTipoPlanta() {
		return tipoPlanta;
	}

	public void setTipoPlanta(TipoPlanta tipoPlanta) {
		this.tipoPlanta = tipoPlanta;
	}
	
	public void agregarStock(Stock s) {
		this.stock.add(s);
	}
	
	public void addPedido(Pedido p) {
		this.pedidosRealizados.add(p);
	}
	
}
