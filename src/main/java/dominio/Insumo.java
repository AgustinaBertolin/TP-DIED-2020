package dominio;

public abstract class Insumo {

	public enum UnidadDeMedida{
		
	}
	
	protected String descripcion;
	protected UnidadDeMedida unidadDeMedida;
	protected Double costo;
}
