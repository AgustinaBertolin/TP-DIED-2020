package paneles;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dominio.MapaRutas;
import dominio.Planta;
import dominio.Ruta;

@SuppressWarnings("serial")
public class PanelAnalisis extends JPanel{
	JTable tabla;
	DefaultTableModel modeloCaminos;
	DefaultTableModel modeloPageRank;
	JButton botonFlujo;
	JButton boton;
	ActionListener listenerPageRank=new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			tabla.setModel(modeloPageRank);
			boton.setText("Caminos");
			boton.removeActionListener(listenerPageRank);
			boton.addActionListener(listenerCaminos);
		}
		
	};
	ActionListener listenerCaminos=new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			tabla.setModel(modeloCaminos);
			boton.setText("PageRanks");
			boton.removeActionListener(listenerCaminos);
			boton.addActionListener(listenerPageRank);
		}
		
	};
	JComboBox<String> listaPlantas1=new JComboBox<String>();
	JComboBox<String> listaPlantas2=new JComboBox<String>();
	DefaultComboBoxModel<String> modeloListaPlantas1;
	DefaultComboBoxModel<String> modeloListaPlantas2;
	
	Component[] dialogo= {
			new JLabel("Planta Origen:"),
			listaPlantas1,
			new JLabel("Planta Destino: "),
			listaPlantas2
			
	};
	public PanelAnalisis(List<Planta> plantas,List<Ruta> rutas) {
		Object[] fila= {"Origen","Destino","Distancia minima","Tiempo minimo"
				
		};
		Object[] fila2= {"Id","Planta","PageRank"
				
		};
		MapaRutas mapa=new MapaRutas();
		mapa.setPlantas(plantas);
		mapa.setRutas(rutas);
		modeloCaminos= new DefaultTableModel();
		modeloPageRank=new DefaultTableModel();
		modeloPageRank.addColumn("id");
		modeloPageRank.addColumn("planta");
		modeloPageRank.addColumn("pageRank");
		modeloPageRank.addRow(fila2);
		modeloCaminos.addColumn("plantaOrigen");
		modeloCaminos.addColumn("planta Destino");
		modeloCaminos.addColumn("distanciaKm");
		modeloCaminos.addColumn("distanciaTiempo");
		modeloCaminos.addRow(fila);
		modeloListaPlantas1=new DefaultComboBoxModel<String>();
		modeloListaPlantas2=new DefaultComboBoxModel<String>();
		double km=-1;
		double tiempo=-1;
		for(Planta p : plantas) {
			modeloListaPlantas1.addElement(p.getNombre());
			modeloListaPlantas2.addElement(p.getNombre());
			for(Planta p2 : plantas) {
				if(p.getId()!=p2.getId()) {
					for(List<Ruta> r : mapa.caminosValidos(p2,p)){
						if(km==-1||mapa.kmCamino(r)<km) {
							km=mapa.kmCamino(r);
						}
						if(tiempo==-1||mapa.tiempoCamino(r)<tiempo) {
							tiempo=mapa.tiempoCamino(r);
						}
					}
					if(km!=-1) {
					fila[0]=p.getNombre();
					fila[1]=p2.getNombre();
					fila[2]=Double.toString(km);
					fila[3]=Double.toString(tiempo);
					modeloCaminos.addRow(fila);
					km=-1;
					tiempo=-1;
					}
				}
			}
		}
		listaPlantas1.setModel(modeloListaPlantas1);
		listaPlantas2.setModel(modeloListaPlantas2);
		List<Planta> pageRank= new ArrayList<Planta>(plantas);
		pageRank.sort((Planta a,Planta b)->Integer.compare(mapa.pageRank(b),mapa.pageRank(a)));
		for(Planta p : pageRank) {
			fila2[0]=p.getId();
			fila2[1]=p.getNombre();
			fila2[2]=Integer.toString(mapa.pageRank(p));
			modeloPageRank.addRow(fila2);
		}
		tabla= new JTable(modeloCaminos);
		botonFlujo=new JButton("Calcular Flujo");
		botonFlujo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(plantas==null) {
					JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado");
					return;
				}
				int resultado=JOptionPane.showConfirmDialog(null,dialogo,"Flujo",JOptionPane.OK_CANCEL_OPTION);
				if(resultado==JOptionPane.OK_OPTION) {
					Planta origen;
					Planta destino;
					
					origen=plantas.stream().filter(p->p.getNombre()==modeloListaPlantas1.getElementAt(listaPlantas1.getSelectedIndex())).collect(Collectors.toList()).get(0);
					destino=plantas.stream().filter(p->p.getNombre()==modeloListaPlantas2.getElementAt(listaPlantas2.getSelectedIndex())).collect(Collectors.toList()).get(0);
					if(listaPlantas1.getSelectedItem()==listaPlantas2.getSelectedItem()) {
						JOptionPane.showMessageDialog(null, "Error: Debe seleccionar 2 plantas distintas.");
					}
					else if(mapa.caminos(destino, origen)==null){
						JOptionPane.showMessageDialog(null, "No hay caminos entre las plantas seleccionadas");
					}
					else {
						JOptionPane.showMessageDialog(null, "El flujo maximo entre las plantas seleccionada es de "+Double.toString(mapa.flujoMaximo(destino,origen))+" kg.");
					}
					}
				
			}
			
		});
		boton=new JButton("PageRanks");
		boton.addActionListener(listenerPageRank);
		this.add(boton);
		this.add(tabla);
		this.add(botonFlujo);
	}
}
