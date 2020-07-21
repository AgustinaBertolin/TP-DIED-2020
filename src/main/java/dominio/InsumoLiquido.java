package dominio;

public class InsumoLiquido extends Insumo {
	
	private Double densidad;

	public Double getDensidad() {
		return densidad;
	}

	public void setDensidad(Double densidad) {
		this.densidad = densidad;
	}

	@Override
	public Double pesoPorUnidad() {
		return this.densidad * 1000;
	}

}
