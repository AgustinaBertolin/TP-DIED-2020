package dominio;

public abstract class Insumo {

	public enum UnidadDeMedida{
		KILO, PIEZA, GRAMO, METRO, LITRO, M3, M2;
	}
	
	protected Integer id;
	protected String descripcion;
	protected UnidadDeMedida unidadDeMedida;
	protected Double costo;
	protected Integer cantidad = 0;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public UnidadDeMedida getUnidadDeMedida() {
		return unidadDeMedida;
	}
	public void setUnidadDeMedida(UnidadDeMedida unidadDeMedida2) {
		this.unidadDeMedida = unidadDeMedida2;
	}
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public void sumCantidad(Integer sum) {
		this.cantidad += sum;
	}
	
	public abstract Double pesoPorUnidad();
	
	
}
