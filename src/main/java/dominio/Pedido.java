package dominio;

import java.time.LocalDateTime;

public class Pedido {
	
	public enum Estado{
		CREADA, PROCESADA, ENTREGADA, CANCELADO
	}
	
	private Integer nroOrden;
	private Planta destino;
	private LocalDateTime fechaSolicitud;
	private LocalDateTime fechaEntrega;
	private Estado estado;

}
