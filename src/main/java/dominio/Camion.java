package dominio;

import java.time.LocalDateTime;

public class Camion {

	private Integer id;
	private String patente;
	private String marca;
	private String modelo;
	private Double kmRecorridos;
	private Double costoPorKM;
	private Double costoPorHora;
	private LocalDateTime fechaDeCompra;
	private boolean guardado = false;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPatente() {
		return patente;
	}
	
	public void setPatente(String patente) {
		this.patente = patente;
	}

	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Double getKmRecorridos() {
		return kmRecorridos;
	}

	public void setKmRecorridos(Double kmRecorridos) {
		this.kmRecorridos = kmRecorridos;
	}

	public Double getCostoPorKM() {
		return costoPorKM;
	}

	public void setCostoPorKM(Double costoPorKM) {
		this.costoPorKM = costoPorKM;
	}

	public Double getCostoPorHora() {
		return costoPorHora;
	}

	public void setCostoPorHora(Double costoPorHora) {
		this.costoPorHora = costoPorHora;
	}

	public LocalDateTime getFechaDeCompra() {
		return fechaDeCompra;
	}

	public void setFechaDeCompra(LocalDateTime fechaDeCompra) {
		this.fechaDeCompra = fechaDeCompra;
	}

	public boolean isGuardado() {
		return guardado;
	}

	public void setGuardado(boolean guardado) {
		this.guardado = guardado;
	}
	
}
