package paneles;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dominio.Camion;


public class PanelCamiones extends JPanel{
	JTable tabla;
	DefaultTableModel modelo;
	JFormattedTextField id;
	JFormattedTextField patente;
	JFormattedTextField marca;
	JFormattedTextField costoHora;
	JFormattedTextField costoKm;
	JSpinner fechaCompra;
	JButton botonFiltrar;
	JButton botonModificar;
	JButton botonBorrar;
	JButton botonCrear;
	JCheckBox checkFecha;
	JFormattedTextField patente2=new JFormattedTextField();
	JFormattedTextField marca2=new JFormattedTextField();
	JFormattedTextField costoHora2=new JFormattedTextField();
	JFormattedTextField costoKm2=new JFormattedTextField();
	JSpinner fechaCompra2= new JSpinner();
	Component[] dialog= {
			new JLabel("Patente: "),
			patente2,
			new JLabel("Marca: "),
			marca2,
			new JLabel("Costo Hora: "),
			costoHora2,
			new JLabel("Costo Km: "),
			costoKm2,
			new JLabel("Fecha de compra: "),
			fechaCompra2};
	//Tengo que tener este panel para poder cambiar los valores del panel cuando quiero cambiar algo
	PanelModificarCamion panelModificar;
	List<Camion> camiones;
public PanelCamiones(PanelModificarCamion panelModif, List<Camion> camions) {
	super();
	camiones = camions;
	modelo= new DefaultTableModel();
	modelo.addColumn("id");
	modelo.addColumn("patente");
	modelo.addColumn("marca");
	modelo.addColumn("costoHora");
	modelo.addColumn("costoKm");
	modelo.addColumn("fechaCompra");
	//test, dsps hay que usar este array y llenarlo con los datos
	//esto no se hace en el constructor, se tiene que cargar cada vez que se cambia a este panel o se filtra
	Object[] fila= {"Id","Patente","Marca","Costo por hora","Costo por km",new SimpleDateFormat("dd/MM/yyyy").format(new Date())
			
	};
	for(int i=0; i<5;i++) {
	for(Camion c:camiones){
		modelo.addRow(fila);
		fila[0]=c.getId();
		fila[1]=c.getPatente();
		fila[2]=c.getMarca();
		fila[3]=c.getCostoPorHora();
		fila[4]=c.getCostoPorKM();
		fila[5]=c.getFechaDeCompra();
	}
	//llenar filas con la base de datos
	tabla= new JTable(modelo);
	id= new JFormattedTextField("id");
	patente= new JFormattedTextField("patente");
	marca= new JFormattedTextField("marca");
	costoHora= new JFormattedTextField("costoHora");
	costoKm= new JFormattedTextField("costoKm");
	checkFecha= new JCheckBox();
	Calendar calendar = Calendar.getInstance();
	Date initDate = (Date) calendar.getTime();
	calendar.add(Calendar.YEAR, -100);
	Date earliestDate = (Date) calendar.getTime();
	SpinnerModel dateModel = new SpinnerDateModel(initDate,
	                                 earliestDate,
	                                 initDate,
	                                 Calendar.YEAR);
	fechaCompra= new JSpinner(dateModel);
	fechaCompra2.setModel(dateModel);
	fechaCompra.setEditor(new JSpinner.DateEditor(fechaCompra, "dd/MM/yyyy"));
	fechaCompra2.setEditor(new JSpinner.DateEditor(fechaCompra2, "dd/MM/yyyy"));
	Dimension dim= new Dimension(70,20);
	id.setPreferredSize(dim);
	patente.setPreferredSize(dim);
	marca.setPreferredSize(dim);
	costoHora.setPreferredSize(dim);
	costoKm.setPreferredSize(dim);
	this.add(new JLabel("Camiones"), null);
	add(new JLabel("Id: "));
	this.add(id);
	add(new JLabel("Patente: "));
	this.add(patente);
	add(new JLabel("Marca: "));
	this.add(marca);
	add(new JLabel("Costo Hora: "));
	this.add(costoHora);
	add(new JLabel("Costo Km: "));
	this.add(costoKm);
	add(new JLabel("Fecha de compra: "));
	this.add(checkFecha);
	this.add(fechaCompra);
	botonFiltrar= new JButton("Filtrar");
	botonFiltrar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			filtrar();
		}
		
	});
	this.add(botonFiltrar);
	this.add(tabla);
	panelModificar= panelModif;
	botonCrear= new JButton("Nuevo");
	botonCrear.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int resultado= JOptionPane.showConfirmDialog(null,dialog,"Nuevo Camion",JOptionPane.OK_CANCEL_OPTION);
			if(resultado== JOptionPane.OK_OPTION) {
				//confirmo que los datos esten bien
				if(patente2.getText()!=null && marca2.getText()!=null&&costoHora2.getText()!=null&&costoKm2.getText()!=null)
									try {
										Float.parseFloat((String) costoHora2.getText());
										Float.parseFloat((String) costoKm2.getText());
										//el valor de id se tiene que asignar por la bd, estoy usando el valor de la seleccionada ahora
										Object[] fila= {modelo.getValueAt(tabla.getSelectedRow(), 0)
												,patente2.getText()
												,marca2.getText()
												,costoHora2.getText()
												,costoKm2.getText()
												,new SimpleDateFormat("dd/MM/yyyy").format(fechaCompra2.getValue())};
										modelo.addRow(fila);
									}
									catch(Exception error) {
										JOptionPane.showMessageDialog(null, "Error: por favor ingrese valores numerales para los costos.");
									}
				else JOptionPane.showMessageDialog(null, "Error: por favor complete todos los campos.");
				//cargar datos
				//limpio

			}
			patente2.setText(null);
			marca2.setText(null);
			costoHora2.setText(null);
			costoKm2.setText(null);
			
		}
		
	});
	this.add(botonCrear);
	botonModificar= new JButton("Modificar");
	botonModificar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(tabla.getSelectedRow()==-1) {
				JOptionPane.showMessageDialog(null, "Error: por favor seleccione que camion desea modificar.");
				return;
			}
			
			//			Seteo los valores del panel con los de la fila seleccionada
						panelModificar.setValores
						(modelo.getValueAt(tabla.getSelectedRow(), 0)
								,modelo.getValueAt(tabla.getSelectedRow(), 1)
								,modelo.getValueAt(tabla.getSelectedRow(), 2)
								,modelo.getValueAt(tabla.getSelectedRow(), 3)
								,modelo.getValueAt(tabla.getSelectedRow(), 4)
								,modelo.getValueAt(tabla.getSelectedRow(), 5));
			//			Esto consigue el frame(?), con eso hay que cambiar el panel al de modificar(acceder al CardLayout y hacer un next?)
						TestPaneles frame = (TestPaneles) SwingUtilities.getRoot((Component) e.getSource());
						frame.getDeck().next(frame.getContentPane());
			//
			
			patente2.setValue(modelo.getValueAt(tabla.getSelectedRow(), 1));
			marca2.setValue(modelo.getValueAt(tabla.getSelectedRow(), 2));
			costoHora2.setValue(modelo.getValueAt(tabla.getSelectedRow(), 3));
			costoKm2.setValue(modelo.getValueAt(tabla.getSelectedRow(), 4));
			try {
				fechaCompra2.setValue(new SimpleDateFormat("dd/MM/yyyy").parse((String) modelo.getValueAt(tabla.getSelectedRow(), 5)));
			} catch (ParseException e1) {
				//esto nunca deberia pasar
			}
			int resultado= JOptionPane.showConfirmDialog(null,dialog,"Modificar Camion",JOptionPane.OK_CANCEL_OPTION);
			if(resultado== JOptionPane.OK_OPTION) {
				if(patente2.getText()!=null && marca2.getText()!=null&&costoHora2.getText()!=null&&costoKm2.getText()!=null)
					try {
						Float.parseFloat((String) costoHora2.getText());
						Float.parseFloat((String) costoKm2.getText());
						//cargar datos
					modelo.setValueAt(patente2.getText(),tabla.getSelectedRow(), 1);
					modelo.setValueAt(marca2.getText(),tabla.getSelectedRow(), 2);
					modelo.setValueAt(costoHora2.getText(),tabla.getSelectedRow(), 3);
					modelo.setValueAt(costoKm2.getText(),tabla.getSelectedRow(), 4);
					modelo.setValueAt(new SimpleDateFormat("dd/MM/yyyy").format(fechaCompra2.getValue()),tabla.getSelectedRow(), 5);
					}
					catch(Exception error) {
						//ventana de aviso 
						JOptionPane.showMessageDialog(null, "Error: por favor ingrese valores numerales para los costos.");
					}
				else JOptionPane.showMessageDialog(null, "Error: por favor complete todos los campos.");
			}
			//limpio
			patente2.setText(null);
			marca2.setText(null);
			costoHora2.setText(null);
			costoKm2.setText(null);
		}
		
	});
	this.add(botonModificar);
	botonBorrar= new JButton("Borrar");
	botonBorrar.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			//popup de confirmacion(?)
			int confirm= JOptionPane.showConfirmDialog(null, "Esta seguro de que quiere borrar el camion seleccionado?","Confirmacion",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			//si se rechaza no se hace nada
			//si se confirma agarro el valor de id y lo borro de la base de datos y la tabla
			if(confirm== JOptionPane.YES_OPTION) {
				//borrar de la bd
				modelo.removeRow(tabla.getSelectedRow());
			}
		}
		
	});
	this.add(botonBorrar);
	}
}

//podemos usar filtrar para que se muestren todos los camiones de la empresa de nuevo si seteamos en null todo(?)
public void filtrar() {
	
	modelo.setRowCount(0);
	Object[] fila= {"12","Patente","Marca","Costo por hora","Costo por km","Fecha de compra"
			};
	//confirmar si esta bien:
	camiones.stream().filter(e->(e.getId()== Integer.parseInt(id.getText().toString())||id.getText()==null)&&(e.getPatente()==patente.getValue()||patente.getValue()==null)
		&&(e.getMarca()==marca.getValue()||marca.getValue()==null)&&(e.getCostoPorHora()==costoHora.getValue()||costoHora.getValue()==null)
		&&((!checkFecha.isSelected())||e.getFechaDeCompra()==fechaCompra.getValue())).forEach
		(e->
		{fila[0]=e.getId();
		fila[1]=e.getPatente();
		fila[2]=e.getMarca();
		fila[3]=e.getCostoPorHora();
		fila[4]=e.getCostoPorKM();
		fila[5]=new SimpleDateFormat("dd/MM/yyyy").format(e.getFechaDeCompra());
		modelo.addRow(fila);});
}
}


