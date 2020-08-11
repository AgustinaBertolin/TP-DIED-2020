package dominio;

import java.util.List;

public class Envio {
	
	private Integer id;
	private Camion camionAsignado;
	private List<Ruta> rutaElegida;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Camion getCamionAsignado() {
		return camionAsignado;
	}
	public void setCamionAsignado(Camion camionAsignado) {
		this.camionAsignado = camionAsignado;
	}
	public List<Ruta> getRutaElegida() {
		return rutaElegida;
	}
	public void setRutaElegida(List<Ruta> rutaElegida) {
		this.rutaElegida = rutaElegida;
	}
	public Double getCosto() {
		return this.camionAsignado.getCostoPorHora() == null? (new MapaRutas()).tiempoCamino(this.rutaElegida) * this.camionAsignado.getCostoPorHora() : (new MapaRutas()).kmCamino(this.rutaElegida) * this.camionAsignado.getCostoPorKM();
	}

}
