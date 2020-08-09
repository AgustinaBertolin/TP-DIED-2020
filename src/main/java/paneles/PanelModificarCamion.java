package paneles;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PanelModificarCamion extends JPanel{
	JFormattedTextField patente;
	JFormattedTextField marca;
	JFormattedTextField costoHora;
	JFormattedTextField costoKm;
	JFormattedTextField fechaCompra;
	int idCamion;
	public PanelModificarCamion() {
		super();
		patente= new JFormattedTextField("patente");
		marca= new JFormattedTextField("marca");
		costoHora= new JFormattedTextField("costoHora");
		costoKm= new JFormattedTextField("costoKm");
		fechaCompra= new JFormattedTextField("fechaCompra");
		Dimension dim= new Dimension(70,30);
		patente.setPreferredSize(dim);
		marca.setPreferredSize(dim);
		costoHora.setPreferredSize(dim);
		costoKm.setPreferredSize(dim);
		fechaCompra.setPreferredSize(dim);
		this.add(patente);
		this.add(marca);
		this.add(costoKm);
		this.add(costoHora);
		this.add(fechaCompra);
		JButton botonAceptar= new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Ver si se llenaron los datos y estan bien
				//Modificar en base de datos
				//limpiar los campos por si se quiere crear/modificar otro camion
				//Vuelve a la pantalla de los camiones
				TestPaneles frame = (TestPaneles) SwingUtilities.getRoot((Component) e.getSource());
				frame.getDeck().previous(frame.getContentPane());
			}
			
		});
		this.add(botonAceptar);
	}
	public void setValores(Object id,Object patente,Object marca,Object costoH,Object costoKm,Object fechaCompra) {
		this.idCamion=Integer.parseInt((String)id);
		this.patente.setValue(patente);
		this.marca.setValue(marca);
		this.costoHora.setValue(costoH);
		this.costoKm.setValue(costoKm);
		this.fechaCompra.setValue(fechaCompra);
	}

}
