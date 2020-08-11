package Died.tp;

import java.util.ArrayList;
import java.util.List;

import dominio.MapaRutas;
import dominio.Planta;
import dominio.Ruta;
import junit.framework.TestCase;

public class Test06 extends TestCase {

	public void testPunto6()
    {
    	Planta p1=new Planta();
	    Planta p2=new Planta();
	    Planta p3=new Planta();
	    Planta p4=new Planta();
	    Planta p5=new Planta();
	    Ruta r1=new Ruta();
	    Ruta r2=new Ruta();
	    Ruta r3=new Ruta();
	    Ruta r4=new Ruta();
	    Ruta r5=new Ruta();
	    p1.setNombre("p1");
	    p2.setNombre("p2");
	    p3.setNombre("p3");
	    p4.setNombre("p4");
	    p5.setNombre("p5");
	    
	    r1.setOrigen(p1);
	    r1.setDestino(p2);
	    r2.setOrigen(p2);
	    r2.setDestino(p3);
	    r3.setOrigen(p3);
	    r3.setDestino(p4);
	    r4.setOrigen(p4);
	    r4.setDestino(p5);
	    r5.setOrigen(p1);
	    r5.setDestino(p5);
	    r1.setId(1);
	    r2.setId(2);
	    r3.setId(3);
	    r4.setId(4);
	    r5.setId(5);
	    r1.setPesoMaxPorDia(1.0);
	    r2.setPesoMaxPorDia(1.0);
	    r3.setPesoMaxPorDia(1.0);
	    r4.setPesoMaxPorDia(1.0);
	    r5.setPesoMaxPorDia(2.0);
	    List<Planta> plantas=new ArrayList<Planta>();
	    plantas.add(p1);
	    plantas.add(p2);
	    plantas.add(p3);
	    plantas.add(p4);
	    plantas.add(p5);
	    List<Ruta> rutas=new ArrayList<Ruta>();
	    rutas.add(r1);
	    rutas.add(r2);
	    rutas.add(r3);
	    rutas.add(r4);
	    rutas.add(r5);
	    MapaRutas mapa=new MapaRutas();
	    mapa.setPlantas(plantas);
	    mapa.setRutas(rutas);
	    assertTrue(mapa.pageRank(p5)==5);
	    assertTrue(mapa.flujoMaximo(p5, p1)==3.0);
    }
}
