package paneles;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dominio.Insumo;
import dominio.Insumo.UnidadDeMedida;
import dominio.InsumoGeneral;
import dominio.InsumoLiquido;
import dominio.Planta;
import dominio.Stock;
import servicios.InsumoService;
import servicios.PlantaService;

@SuppressWarnings("serial")
public class PanelInsumo extends JPanel {

	private DefaultTableModel modeloInsumo;
	private JTable tablaInsumo;
	private JButton borrar;
	private JButton crear;
	private JButton modificar;
	private InsumoService service = new InsumoService();

	
	public PanelInsumo() {
		inicializar();
	}
	
	private void inicializar() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel panelTitulo = new JPanel(new GridBagLayout());

		//Crear tabla
		tablaInsumo = new JTable(modeloInsumo);
		add(tablaInsumo);
		actualizarTabla();
		tablaInsumo.setDefaultEditor(Object.class, null);

		//Crear botones

		//Boton BORRAR
		borrar = new JButton("Borrar");
		borrar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(tablaInsumo.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(new JFrame(), "No se ha seleccionado un insumo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					int res = JOptionPane.showConfirmDialog(getParent(), "¿Desea borrar el Insumo?", "Advertencia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (res == 0) {
						service.borrarInsumo((Integer) modeloInsumo.getValueAt(tablaInsumo.getSelectedRow(), 0));
						actualizarTabla();
					}
				}
				
			}
		});
		
		
		//Boton AÑADIR
		crear = new JButton("Añadir");
		crear.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {
					crearInsumo();
			}
			
		});
	
		
		modificar = new JButton("Modificar");
		modificar.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				if(tablaInsumo.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(new JFrame(), "No se ha seleccionado un insumo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					Insumo i = service.buscarPorId(Integer.valueOf(modeloInsumo.getValueAt(tablaInsumo.getSelectedRow(), 0).toString()));
					modificarInsumo(i);
				}
			}
			
		});
		
		
		JScrollPane tableSP = new JScrollPane(tablaInsumo);
		tableSP.setPreferredSize(new Dimension(1200, 500));
		 
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.insets = new Insets(10, 10, 10, 10);
	          
	    constraints.gridx = 1;
	    constraints.gridy = 0;     
	    JLabel titulos = new JLabel("Control de Insumos");
	    titulos.setFont(new Font("Panel Insumos", Font.TYPE1_FONT, 30));
	    titulos.setSize(new Dimension(300, 400));
	    panelTitulo.add(titulos, constraints);
	    
	    // add components to the panel
	    constraints.gridx = 0;
	    constraints.gridy = 1;     
	    panelTitulo.add(borrar, constraints);
	  
	    constraints.gridx = 1;
	    panelTitulo.add(modificar, constraints);
	          
	    constraints.gridx = 2;    
	    panelTitulo.add(crear, constraints);
	          
	    JPanel panelTabla = new JPanel(new GridBagLayout());
	    GridBagConstraints constraintsTabla = new GridBagConstraints();
	    constraintsTabla.anchor = GridBagConstraints.BOTH;
	    constraintsTabla.insets = new Insets(0, 0, 0, 5);
	    constraintsTabla.gridy = 2;
	    constraintsTabla.gridx = 1;
	    constraintsTabla.gridwidth = 8;
	    constraintsTabla.anchor = GridBagConstraints.CENTER;
	    panelTabla.add(tableSP, constraintsTabla);
	    
	    add(panelTitulo);
	    add(panelTabla);
	    
	}
	
	public void crearInsumo() {
		final JDialog dialogo = new JDialog(new JFrame(), "Crear insumo", Dialog.ModalityType.DOCUMENT_MODAL); 
		dialogo.setResizable(false);
		dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panelCrear = new JPanel();
        panelCrear.setLayout(new BoxLayout(panelCrear, BoxLayout.PAGE_AXIS)); //VERRRRRRRRRRRRRRR
        JButton botonAñadir = new JButton("Guardar");
        
        //Etiquetas
        JLabel descripcionEtiqueta = new JLabel("Descripcion: ");
        JLabel unidadDeMedidaEtiqueta = new JLabel("Unidad de medida: ");
        JLabel costoEtiqueta = new JLabel("Costo: ");
        JLabel tipoEtiqueta = new JLabel("Tipo: ");
        JLabel densidadEtiqueta = new JLabel("Densidad");
    	JLabel pesoEtiqueta = new JLabel("Peso: ");
        
    	//Campos
        final JTextField textoDescripcion = new JTextField(20);
        final JComboBox<UnidadDeMedida> unidadDeMedidaComboBox = new JComboBox<UnidadDeMedida>(); 
        unidadDeMedidaComboBox.setModel(new DefaultComboBoxModel<UnidadDeMedida>(UnidadDeMedida.values()));
        final JTextField textoCosto = new JTextField(20);
        final JTextField textoTipo = new JTextField(20);
        
        final JPanel panelTipo = new JPanel();
        CardLayout tipoLayout = new CardLayout();
        panelTipo.setLayout(tipoLayout);
        
        //Insumo Liquido y General
        JPanel panelGeneral = new JPanel();
        JPanel panelLiquido = new JPanel();
        
        panelTipo.add(panelGeneral, "card__General");
        panelTipo.add(panelLiquido, "card__Liquido");
        
        panelGeneral.setLayout(new GridBagLayout());
        GridBagConstraints constraintsGeneral = new GridBagConstraints();
        constraintsGeneral.anchor = GridBagConstraints.WEST;
        constraintsGeneral.insets = new Insets(10, 10, 10, 10);
        
        constraintsGeneral.gridx = 0;
        constraintsGeneral.gridy = 0; 
        panelGeneral.add(pesoEtiqueta, constraintsGeneral);
        
        constraintsGeneral.gridx = 3;
        panelGeneral.add(textoTipo, constraintsGeneral);
        
        panelLiquido.setLayout(new GridBagLayout());
        GridBagConstraints constraintsLiquido = new GridBagConstraints();
        constraintsLiquido.anchor = GridBagConstraints.WEST;
        constraintsLiquido.insets = new Insets(10, 10, 10, 10);
        
        constraintsLiquido.gridx = 0;
        constraintsLiquido.gridy = 0; 
        panelLiquido.add(densidadEtiqueta, constraintsLiquido);
        
        constraintsLiquido.gridx = 1;
        panelLiquido.add(textoTipo, constraintsLiquido);
        
        final JRadioButton botonIGeneral = new JRadioButton("Insumo General");
        botonIGeneral.setMnemonic(KeyEvent.VK_B);
        botonIGeneral.setActionCommand("Insumo General");
        botonIGeneral.setSelected(true);
        
        final JRadioButton botonILiquido = new JRadioButton("Insumo Liquido");
        botonILiquido.setMnemonic(KeyEvent.VK_C);
        botonILiquido.setActionCommand("Insumo Liquido");
        
        ActionListener gen = new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CardLayout cardLayout = (CardLayout) panelTipo.getLayout();
				cardLayout.show(panelTipo, "card__General");
				
			}
        	
        };

        ActionListener liq = new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CardLayout cardLayout = (CardLayout) panelTipo.getLayout();
				cardLayout.show(panelTipo, "card__Liquido");
				
			}
        	
        };
        
        botonIGeneral.addActionListener(gen);
        botonILiquido.addActionListener(liq);

        ButtonGroup group = new ButtonGroup();
        group.add(botonIGeneral);
        group.add(botonILiquido);

        //Panel del mensaje
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
          
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(descripcionEtiqueta, constraints);
  
        constraints.gridx = 1;
        newPanel.add(textoDescripcion, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 2;     
        newPanel.add(unidadDeMedidaEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(unidadDeMedidaComboBox, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 3;
        newPanel.add(costoEtiqueta, constraints);
        
        constraints.gridx = 1;
        newPanel.add(textoCosto, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 4;
        
        newPanel.add(tipoEtiqueta, constraints);
        
        constraints.gridx = 1;
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(botonIGeneral);
        radioPanel.add(botonILiquido);
        newPanel.add(radioPanel, constraints);
        
        panelCrear.add(newPanel);
        panelCrear.add(panelTipo);

        //Crear Insumo
        ActionListener al =  new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if(		textoDescripcion.getText().length() == 0 ||
						textoCosto.getText().length() == 0 ||
						unidadDeMedidaComboBox.getSelectedItem().toString() == "Seleccione una Opcion" ||
						textoTipo.getText().length() == 0 ) {
					JOptionPane.showMessageDialog(new JPanel(), "Error: Uno de los campos se encuentra vacío", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					
					Double costo;
					Double tipo;
					try {
						costo = Double.parseDouble(textoCosto.getText());
						tipo = Double.parseDouble(textoTipo.getText());
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(new JFrame(), "Uno o más datos ingresados no son válidos",
								"Información", JOptionPane.INFORMATION_MESSAGE);

						return;
					}
					Insumo i;
					
					if(botonIGeneral.isSelected()) {
						

						i = new InsumoGeneral();
						((InsumoGeneral)i).setPeso(tipo);
					}
					else {
						i = new InsumoLiquido();
						((InsumoLiquido)i).setDensidad(tipo);
					}
					
					i.setDescripcion(textoDescripcion.getText());
					i.setUnidadDeMedida(UnidadDeMedida.valueOf(unidadDeMedidaComboBox.getSelectedItem().toString()));
					i.setCosto(costo);
				
					service.saveOrUpdate(i);
					actualizarTabla();
					dialogo.dispose();
				}
			}
        	
        };
        botonAñadir.addActionListener(al); 
  
        JPanel panelBoton = new JPanel();
        panelBoton.setSize(new Dimension(400, 120));
        panelBoton.setLayout(new BorderLayout(0,0));
        panelBoton.add(botonAñadir, BorderLayout.CENTER); 
  
        panelCrear.add(panelBoton);
        dialogo.add(panelCrear);  
        dialogo.setSize(400, 300); 
        dialogo.setVisible(true); 
	}
	
	public void modificarInsumo(final Insumo i) {
        
		final JDialog dialogo = new JDialog(new JFrame(), "Modificar insumo", Dialog.ModalityType.DOCUMENT_MODAL); 
		dialogo.setResizable(false);
		dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panelModificar = new JPanel();
        panelModificar.setLayout(new BoxLayout(panelModificar, BoxLayout.PAGE_AXIS));
        
        //Etiquetas
        JLabel descripcionEtiqueta = new JLabel("Descripcion: ");
        JLabel unidadDeMedidaEtiqueta = new JLabel("Unidad de medida: ");
        JLabel costoEtiqueta = new JLabel("Costo: ");
        JLabel tipoEtiqueta;
        
        //Campos
        final JTextField textoDescripcion = new JTextField(i.getDescripcion(), 30);
        final JComboBox<UnidadDeMedida> unidadDeMedidaComboBox = new JComboBox<UnidadDeMedida>(); 
        unidadDeMedidaComboBox.setModel(new DefaultComboBoxModel<UnidadDeMedida>(UnidadDeMedida.values()));
		unidadDeMedidaComboBox.setSelectedItem(i.getUnidadDeMedida());
        final JTextField textoCosto = new JTextField(i.getCosto().toString(), 30);
        final JTextField textoTipo;
        
        if(i instanceof InsumoGeneral) {
        	tipoEtiqueta = new JLabel("Peso: ");
        	textoTipo = new JTextField(((InsumoGeneral) i).getPeso().toString(), 30);
        }
        else {
        	tipoEtiqueta = new JLabel("Densidad: ");
        	textoTipo = new JTextField(((InsumoLiquido) i).getDensidad().toString(), 30);
        }
        
        JButton botonGuardar = new JButton("Guardar cambios");
         
        //Se agregan los componentes en el panel
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridBagLayout());
 
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
          
        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        newPanel.add(descripcionEtiqueta, constraints);
  
        constraints.gridx = 1;
        newPanel.add(textoDescripcion, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(unidadDeMedidaEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(unidadDeMedidaComboBox, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 2;
        newPanel.add(costoEtiqueta, constraints);
        
        constraints.gridx = 1;
        newPanel.add(textoCosto, constraints);
        	
        constraints.gridx = 0;
        constraints.gridy = 3;
        newPanel.add(tipoEtiqueta, constraints);
            
        constraints.gridx = 1;
        newPanel.add(textoTipo, constraints);
        
        panelModificar.add(newPanel);
  
        //Creamos el boton
        ActionListener al =  new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if(		textoTipo.getText().length() == 0 || 
						textoDescripcion.getText().length() == 0 ||
						textoCosto.getText().length() == 0 ) {
					JOptionPane.showMessageDialog(getParent(), "Error: Uno de los campos se encuentra vacío", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					
					Double costo;
					Double tipo;
					try {
						costo = Double.parseDouble(textoCosto.getText());
						tipo = Double.parseDouble(textoTipo.getText());
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(new JFrame(), "Uno o más datos ingresados no son válidos",
								"Información", JOptionPane.INFORMATION_MESSAGE);

						return;
					}
					if(i instanceof InsumoGeneral) {
						((InsumoGeneral)i).setPeso(tipo);
					}
					else {
						((InsumoLiquido)i).setDensidad(tipo);
					}
					
					i.setDescripcion(textoDescripcion.getText());
					i.setUnidadDeMedida(UnidadDeMedida.valueOf(unidadDeMedidaComboBox.getSelectedItem().toString()));
					i.setCosto(costo);
				
					service.saveOrUpdate(i);
					actualizarTabla();
					dialogo.dispose();
				}
			}
        	
        };
        botonGuardar.addActionListener(al); 

        //Añadimos es boton al panel
        JPanel panelBoton = new JPanel();
        panelBoton.setSize(new Dimension(400, 100));
        panelBoton.setLayout(new BorderLayout(0,0));
        panelBoton.add(botonGuardar, BorderLayout.CENTER);
        
        panelModificar.add(panelBoton);
        dialogo.add(panelModificar); 
        dialogo.setSize(420, 300); 
        dialogo.setVisible(true); 

	}
	
	public void actualizarTabla() {
		//crear modelo tabla
				String[] columnas = {"Id", "Descripcion", "Unidad de medida", "Costo", "Tipo", "Peso", "Stock Total"};
				modeloInsumo = new DefaultTableModel(columnas, 0);
				
				//buscar datos de la db
				List<Insumo> insumos = service.buscarTodos();
				
				PlantaService serviceP = new PlantaService();
				List<Planta> plantas = serviceP.buscarTodos();
				
				for(Planta p: plantas) {
					for(Stock s: p.getStock()) {
						s.getInsumo().sumCantidad(s.getCantidad());
					}
				}
			
				for(Insumo i: insumos) {
					Integer id = i.getId();
					String descripcion = i.getDescripcion();
					String unidadDeMedida = i.getUnidadDeMedida().toString();
					Double costo = i.getCosto();
					String tipo;
					Double peso = i.pesoPorUnidad();
					Integer stockTotal = i.getCantidad();
					
					if(i instanceof InsumoGeneral) {
						tipo = "General";
					}
					else {
						tipo = "Liquido";
					}
					Object[] renglon = {id, descripcion, unidadDeMedida, costo, tipo, peso, stockTotal};
					modeloInsumo.addRow(renglon);
				}

				//actualizar modelo
				tablaInsumo.setModel(modeloInsumo);

	}
	

}
