package dominio;

public class Stock {

	private Integer idRegistro;
	private int cantidad;
	private int puntoDeReposicion;
	private Insumo insumo;
	
	public Integer getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public int getPuntoDeReposicion() {
		return puntoDeReposicion;
	}
	public void setPuntoDeReposicion(int puntoDeReposicion) {
		this.puntoDeReposicion = puntoDeReposicion;
	}
	public Insumo getInsumo() {
		return insumo;
	}
	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}
	
}
