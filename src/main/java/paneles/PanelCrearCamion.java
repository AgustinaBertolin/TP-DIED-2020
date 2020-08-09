package paneles;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.*;

public class PanelCrearCamion extends JPanel{
	JFormattedTextField id;
	JFormattedTextField patente;
	JFormattedTextField marca;
	JFormattedTextField costoHora;
	JFormattedTextField costoKm;
	JFormattedTextField fechaCompra;
	int idInicial = -1;
public PanelCrearCamion() {
	super();
	id= new JFormattedTextField("id");
	patente= new JFormattedTextField("patente");
	marca= new JFormattedTextField("marca");
	costoHora= new JFormattedTextField("costoHora");
	costoKm= new JFormattedTextField("costoKm");
	fechaCompra= new JFormattedTextField("fechaCompra");
	Dimension dim= new Dimension(70,30);
	id.setPreferredSize(dim);
	patente.setPreferredSize(dim);
	marca.setPreferredSize(dim);
	costoHora.setPreferredSize(dim);
	costoKm.setPreferredSize(dim);
	fechaCompra.setPreferredSize(dim);
	this.add(id);
	this.add(patente);
	this.add(marca);
	this.add(costoKm);
	this.add(costoHora);
	this.add(fechaCompra);
	JButton botonAceptar= new JButton("Aceptar");
	botonAceptar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//Ver si se llenaron los datos y estan bien
			//Guardar en base de datos
			//limpiar los campos por si se quiere crear otro camion
		}
		
	});
	this.add(botonAceptar);
}

}
