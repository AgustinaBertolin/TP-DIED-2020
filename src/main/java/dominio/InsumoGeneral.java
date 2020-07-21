package dominio;

public class InsumoGeneral extends Insumo {
	
	private Double peso;

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public Double pesoPorUnidad() {
		return this.peso;
	}

}
