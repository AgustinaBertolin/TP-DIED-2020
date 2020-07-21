package dominio;

public class Envio {
	
	private Integer id;
	private Camion camionAsignado;
	private Ruta rutaElegida;
	private Double costo;
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
	public Ruta getRutaElegida() {
		return rutaElegida;
	}
	public void setRutaElegida(Ruta rutaElegida) {
		this.rutaElegida = rutaElegida;
	}
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}

}
