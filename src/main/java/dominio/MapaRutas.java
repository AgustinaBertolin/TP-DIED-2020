package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.transform.stream.StreamResult;

public class MapaRutas {

	List<Planta> plantas;
	List<Ruta> rutas;
	
	public MapaRutas() {
		this.plantas = new ArrayList<Planta>();
		this.rutas = new ArrayList<Ruta>();
	}
	public void setPlantas(List<Planta> p) {
		plantas=new ArrayList<Planta>(p);
	}
	public void setRutas(List<Ruta> r) {
		rutas=new ArrayList<Ruta>(r);
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
			if((ruta.getOrigen()==origen)&&(ruta.getDestino()==destino))
				return ruta;
		}
		
		//esto nunca deberia pasar
		return null;
	}
	public double kmCamino(List<Ruta> camino) {
		double i=0;
		for(Ruta r : camino) {
			i+=r.getDistanciaKM();
		}
		return i;
	}
	public double tiempoCamino(List<Ruta> camino) {
		double i=0;
		for(Ruta r : camino) {
			i+=r.getDuracion();
		}
		return i;
	}
	public List<List<Ruta>> caminosValidos(Planta destino,Planta origen){
		List<List<Ruta>> caminos=caminos(destino,origen);
		if(caminos.size()==0) return caminos;
		double minimoKm=kmCamino(caminos.get(0));
		double minimoTiempo=tiempoCamino(caminos.get(0));
		for(List<Ruta> camino : caminos) {
			if(tiempoCamino(camino)<minimoKm){
				minimoTiempo=tiempoCamino(camino);
			}
			if(kmCamino(camino)<minimoKm){
				minimoKm=kmCamino(camino);
			}
		}
		List<List<Ruta>> resultado=caminoMenosKm(minimoKm,caminos);
		resultado.addAll(caminoMenosTiempo(minimoTiempo,caminos));
		return resultado;
	}
	public List<List<Ruta>> caminoMenosKm(double minimo,List<List<Ruta>> caminos){
		List<List<Ruta>> copia= new ArrayList<List<Ruta>>(caminos);
		for(List<Ruta> camino : copia) {
			if(kmCamino(camino)>minimo) {
				copia.remove(camino);
			}
		}
		return copia;
	}
	public List<List<Ruta>> caminoMenosTiempo(double minimo,List<List<Ruta>> caminos){
		List<List<Ruta>> copia= new ArrayList<List<Ruta>>(caminos);
		for(List<Ruta> camino : copia) {
			if(tiempoCamino(camino)>minimo) {
				copia.remove(camino);
			}
		}
		return copia;
	}
	public double flujoMaximo(Planta destino, Planta origen) {
		List<List<Ruta>> caminos=caminos(origen,destino);
		List<List<Ruta>> copias=new ArrayList<List<Ruta>>();
		for(List<Ruta> r : caminos) {
			copias.add(copiaRuta(r,copias));
		}
		double resultado=0;
		for(List<Ruta> r : caminos) {
				resultado+=flujo(r);
			}
		return resultado;
	}
	public double flujo(List<Ruta> camino) {
		double resultado=camino.get(0).getPesoMaxPorDia();
		for(Ruta r : camino) {
			if(r.getPesoMaxPorDia()<resultado) resultado= r.getPesoMaxPorDia();
		}
		for(Ruta r: camino) {
			r.setPesoMaxPorDia(r.getPesoMaxPorDia()-resultado);
		}
		return resultado;
	}
	public List<Ruta> copiaRuta(List<Ruta> camino,List<List<Ruta>> copiasHechas){
		List<Ruta> copia= new ArrayList<Ruta>();
		Ruta copiaRuta= new Ruta();
		for(Ruta r: camino) {
			if(contieneRuta(copiasHechas.stream().flatMap(List::stream).collect(Collectors.toList()),r)) 
				for(Ruta r2 : copiasHechas.stream().flatMap(List::stream).collect(Collectors.toList())) {
					if(r2.getId()==r.getId()) {
						copia.add(r2);
					}
				}
			else {
			copiaRuta.setPesoMaxPorDia(r.getPesoMaxPorDia());
			copiaRuta.setId(r.getId());
			copia.add(copiaRuta);
			copiaRuta=new Ruta();
			}
		}
		return copia;
	}
	public boolean contieneRuta(List<Ruta> rutas,Ruta ruta) {
		for(Ruta r : rutas) {
			if(r.getId()==ruta.getId()) return true;
		}
		return false;
	}
	public int pageRank(Planta planta) {
		int resultado=0;
		for(Planta p : plantas) {
			if(p!=planta) {
				resultado+=caminos(planta,p).size();
			}
		}
		return resultado;
	}
	}