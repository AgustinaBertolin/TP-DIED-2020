package dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
	
	public enum Estado{
		CREADA, PROCESADA, ENTREGADA, CANCELADO
	}
	
	private Integer nroOrden;
	private Planta destino;
	private LocalDate fechaSolicitud;
	private LocalDate fechaEntrega;
	private Estado estado;
	private List<Item> items;
	private Envio envio;
	private boolean guardado = false;
	
	public Pedido() {
		this.items = new ArrayList<Item>();
	}
	
	public Integer getNroOrden() {
		return nroOrden;
	}
	public void setNroOrden(Integer nroOrden) {
		this.nroOrden = nroOrden;
	}
	public Planta getDestino() {
		return destino;
	}
	public void setDestino(Planta destino) {
		this.destino = destino;
	}
	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(LocalDate fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Envio getEnvio() {
		return envio;
	}

	public void setEnvio(Envio envio) {
		this.envio = envio;
	}
	
	public void addItem(Item i) {
		this.items.add(i);
	}

	public boolean isGuardado() {
		return guardado;
	}

	public void setGuardado(boolean guardado) {
		this.guardado = guardado;
	}
	
	

}
