package dominio;

import java.util.List;

public class Planta {

	private Integer id;
	private String nombre;
	private List<Camion> camionesDisponibles;
	private List<Pedido> pedidosRealizados;
	private List<Stock> stock;
	
	Planta(){
		
	}
}
