package dominio;

import java.util.ArrayList;
import java.util.List;

public class MapaRutas {

	List<Planta> plantas;
	List<Ruta> rutas;
	
	public MapaRutas() {
		this.plantas = new ArrayList<Planta>();
		this.rutas = new ArrayList<Ruta>();
	}
	public List<List<Ruta>> caminos(Planta destino,Planta origen){
		List<List<Ruta>> caminosAgregados=new ArrayList<List<Ruta>>();
		List<Ruta> camino= new ArrayList<Ruta>();
		while((camino=camino(destino,origen,new ArrayList<Ruta>(),caminosAgregados))!=null) {
			caminosAgregados.add(camino);
		}
		
		return caminosAgregados;
		
	}
	public List<Ruta> camino(Planta destino, Planta origen,List<Ruta> caminoTemporal,List<List<Ruta>> caminosAgregados){
		if(origen==destino)
			return caminoTemporal;
		for(Planta p: adyacentes(origen)) {
			caminoTemporal.add(ruta(origen,p));
			List<Ruta> resultado= camino(destino,p,caminoTemporal,caminosAgregados);
			if(resultado==null) caminoTemporal.remove(ruta(origen,p));
			else if(caminosAgregados.contains(resultado)) {
				caminoTemporal.remove(ruta(origen,p));
			}
			else return resultado;
		}
		return null;
		
	}
	public List<Planta> adyacentes(Planta origen){
		List<Planta> adyacentes= new ArrayList<Planta>();
		for(Ruta r : this.rutas) {
			if(r.getOrigen()==origen) {
				adyacentes.add(r.getDestino());
			}
		}
		return adyacentes;
	}
	public Ruta ruta(Planta origen, Planta destino) {
		for(Ruta ruta : rutas) {
			if(ruta.getOrigen()==origen&&ruta.getDestino()==destino)
				return ruta;
		}
		return null;
	}
	public int kmCamino(List<Ruta> camino) {
		int i=0;
		for(Ruta r : camino) {
			i+=r.getDistanciaKM();
		}
		return i;
	}
	public int tiempoCamino(List<Ruta> camino) {
		int i=0;
		for(Ruta r : camino) {
			//i+=r.getDuracion();
		}
		return i;
	}
	public List<List<Ruta>> caminoMenosKm(int minimo,List<List<Ruta>> caminos){
		List<List<Ruta>> copia= new ArrayList<List<Ruta>>(caminos);
		for(List<Ruta> camino : copia) {
			if(kmCamino(camino)<minimo) {
				copia.remove(camino);
			}
		}
		return copia;
	}
	public List<List<Ruta>> caminoMenosTiempo(int minimo,List<List<Ruta>> caminos){
		List<List<Ruta>> copia= new ArrayList<List<Ruta>>(caminos);
		for(List<Ruta> camino : copia) {
			if(tiempoCamino(camino)<minimo) {
				copia.remove(camino);
			}
		}
		return copia;
	}
	}