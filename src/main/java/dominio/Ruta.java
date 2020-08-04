package dominio;

import java.sql.Time;

public class Ruta {
	
	private Integer id;
	private Planta origen;
	private Planta destino;
	private Double distanciaKM;
	private Time duracion; 
	private Double pesoMaxPorDia;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Planta getOrigen() {
		return origen;
	}
	public void setOrigen(Planta origen) {
		this.origen = origen;
	}
	public Planta getDestino() {
		return destino;
	}
	public void setDestino(Planta destino) {
		this.destino = destino;
	}
	public Double getDistanciaKM() {
		return distanciaKM;
	}
	public void setDistanciaKM(Double distanciaKM) {
		this.distanciaKM = distanciaKM;
	}
	public Time getDuracion() {
		return duracion;
	}
	public void setDuracion(Time duracion) {
		this.duracion = duracion;
	}
	public Double getPesoMaxPorDia() {
		return pesoMaxPorDia;
	}
	public void setPesoMaxPorDia(Double pesoMaxPorDia) {
		this.pesoMaxPorDia = pesoMaxPorDia;
	}

	
}
