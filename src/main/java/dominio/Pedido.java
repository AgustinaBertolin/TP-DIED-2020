package dominio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
	
	public enum Estado{
		CREADA, PROCESADA, ENTREGADA, CANCELADO
	}
	
	private Integer nroOrden;
	private Planta destino;
	private Date fechaSolicitud;
	private Date fechaEntrega;
	private Estado estado;
	private List<Item> items;
	private Envio envio;
	
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
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
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

}
