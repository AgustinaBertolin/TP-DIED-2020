package App;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import dominio.Camion;
import dominio.Envio;
import dominio.Insumo;
import dominio.InsumoGeneral;
import dominio.InsumoLiquido;
import dominio.Item;
import dominio.MapaRutas;
import dominio.Pedido;
import dominio.Planta;
import dominio.Ruta;
import dominio.Stock;
import dominio.Insumo.UnidadDeMedida;
import dominio.Pedido.Estado;
import dominio.Planta.TipoPlanta;
import paneles.PanelAnalisis;
import paneles.PanelCamiones;
import paneles.PanelModificarCamion;
import servicios.CamionService;
import servicios.IDService;
import servicios.InsumoService;
import servicios.PedidoService;
import servicios.PlantaService;
import servicios.RutaService;

public class App {

	private JFrame frameTrabajoPractico;
	private CardLayout cl;
	private JPanel panelInsumo;
	private JPanel panelCamion;
	private JPanel panelPlanta;
	private JPanel panelPedido;
	private JPanel panelAnalisis;
	
	private DefaultTableModel modeloPedidosCreados;
	private DefaultTableModel modeloPedidosProcesados;
	private JTable tablaPedidosCreados;
	private JTable tablaPedidosProcesados;
	private DefaultTableModel modeloPlanta;
	private DefaultTableModel modeloStock;
	private DefaultTableModel modeloItem;
	private JTable tablaPlanta;
	private JTable tablaStock;
	private JTable tablaItem;
	private DefaultTableModel modeloInsumo;
	private JTable tablaInsumo;
	
	private InsumoService insumoService = new InsumoService();
	public PlantaService plantaService = new PlantaService();
	public RutaService rutaService = new RutaService();
	public CamionService camionService = new CamionService();
	public PedidoService pedidoService = new PedidoService();
	
	public List<Planta> plantas;
	public List<Insumo> insumos;
	public List<Ruta> rutas;
	public List<Camion> camiones;
	public List<Pedido> pedidos;	
 	
	public int idPlanta;
	public int idCamion;
	public int idPedido;
	public int idInsumo;
	public int idRuta;
	
	public  App() {
		List<Integer> ids = (new IDService()).getIds();
		idPlanta = ids.get(0);
		idCamion = ids.get(1);
		idPedido = ids.get(2);
		idInsumo = ids.get(3);
		idRuta = ids.get(4);
		
		plantas = plantaService.buscarTodos();
		insumos = insumoService.buscarTodos();
		rutas = rutaService.buscarTodos();
		camiones = camionService.buscarTodos();
		pedidos = new ArrayList<Pedido>();
		for(Planta p: plantas) {
			pedidos.addAll(p.getPedidosRealizados());
		}
		armarApp();
	}
	
	private void armarApp() {
		frameTrabajoPractico = new JFrame();
		cl = new CardLayout(0, 0);

		// Frame properties
		frameTrabajoPractico.setResizable(false);
		frameTrabajoPractico.setBackground(Color.WHITE);
		frameTrabajoPractico.setTitle("Trabajo Práctico 2020 - DIED");
		frameTrabajoPractico.setMinimumSize(new Dimension(1280, 720));
		frameTrabajoPractico.setBounds(100, 100, 450, 300);
		frameTrabajoPractico.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameTrabajoPractico.getContentPane().setLayout(cl);

		JMenuBar menuBar = new JMenuBar();
		frameTrabajoPractico.setJMenuBar(menuBar);

		menuPrincipal();

		menuInsumo();
		
		menuCamion();
		
		menuPlanta();

		menuPedidos();
		
		menuAnalisis();

		frameTrabajoPractico.setLocationRelativeTo(null);
		frameTrabajoPractico.pack();
	}
	
	private void menuPrincipal() {
		
		JPanel menuPrincipal = new JPanel();
		frameTrabajoPractico.getContentPane().add(menuPrincipal, "card__MainMenu");
		
		GridLayout grid = new GridLayout(5, 1);
		menuPrincipal.setLayout(grid);
		
		JButton camionButton = new JButton("Camiones");
		camionButton.setFont(new Font("Panel Camiones", Font.ITALIC, 30));
		camionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(frameTrabajoPractico.getContentPane(), "card__Camion");
			}
		});
		
		JButton insumoButton = new JButton("Insumos");
		insumoButton.setFont(new Font("Panel Insumos", Font.ITALIC, 30));
		insumoButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				cl.show(frameTrabajoPractico.getContentPane(), "card__Insumo");
			}
			
		});
		
		JButton plantaButton = new JButton("Plantas");
		plantaButton.setFont(new Font("Panel Plantas", Font.ITALIC, 30));
		plantaButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				cl.show(frameTrabajoPractico.getContentPane(), "card__Planta");
			}
			
		});

		JButton pedidoButton = new JButton("Pedidos");
		pedidoButton.setFont(new Font("Panel Stock", Font.ITALIC, 30));
		pedidoButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				cl.show(frameTrabajoPractico.getContentPane(), "card__Pedido");
			}
			
		});
		
		JButton analisisButton = new JButton("Analisis de Rutas");
		analisisButton.setFont(new Font("Panel Analisis", Font.ITALIC, 30));
		analisisButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				cl.show(frameTrabajoPractico.getContentPane(), "card__Analisis");
			}
			
		});
		
		menuPrincipal.add(camionButton);
		menuPrincipal.add(insumoButton);
		menuPrincipal.add(plantaButton);
		menuPrincipal.add(pedidoButton);
		menuPrincipal.add(analisisButton);
	}
	
	//menu Insumos
	private void menuInsumo() {
		//Boton volver al menu principal
				JPanel panelVolver = new JPanel(new GridBagLayout());
				GridBagConstraints volverConstraints = new GridBagConstraints();
				volverConstraints.insets = new Insets(10, 10, 10, 10);
				volverConstraints.fill = GridBagConstraints.WEST;
				volverConstraints.gridx = 1;
				volverConstraints.gridy = 0;
				JButton volver = new JButton("Volver");
				volver.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						cl.show(frameTrabajoPractico.getContentPane(), "card__MainMenu");
					}
					
				});
				panelVolver.add(volver, volverConstraints);
				
				JPanel subPanel = new JPanel();
				
				subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.PAGE_AXIS));
				JPanel panelTitulo = new JPanel(new GridBagLayout());

				//Crear tabla
				tablaInsumo = new JTable(modeloInsumo);
				subPanel.add(tablaInsumo);
				actualizarTablaInsumo();
				tablaInsumo.setDefaultEditor(Object.class, null);

				//Crear botones

				//Boton BORRAR
				JButton borrar = new JButton("Borrar");
				borrar.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(tablaInsumo.getSelectedRow() < 0) {
							JOptionPane.showMessageDialog(new JFrame(), "No se ha seleccionado un insumo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							int res = JOptionPane.showConfirmDialog(subPanel, "¿Desea borrar el Insumo?", "Advertencia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
							if (res == 0) {
								insumoService.borrarInsumo((Integer) modeloInsumo.getValueAt(tablaInsumo.getSelectedRow(), 0));
								insumos.remove(insumos.stream().filter(i -> i.getId() == (Integer) modeloInsumo.getValueAt(tablaInsumo.getSelectedRow(), 0)).collect(Collectors.toList()).get(0));
								actualizarTablaInsumo();
							}
						}
						
					}
				});
				
				
				//Boton AÑADIR
				JButton crear = new JButton("Añadir");
				crear.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent e) {
							crearInsumo();
					}
					
				});
			
				
				JButton modificar = new JButton("Modificar");
				modificar.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						if(tablaInsumo.getSelectedRow() < 0) {
							JOptionPane.showMessageDialog(new JFrame(), "No se ha seleccionado un insumo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							Insumo i = insumos.stream().filter(f -> f.getId() == (Integer) modeloInsumo.getValueAt(tablaInsumo.getSelectedRow(), 0)).collect(Collectors.toList()).get(0);
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
			    
			    subPanel.add(panelTitulo);
			    subPanel.add(panelTabla);
			    
				panelInsumo = new JPanel();
				panelInsumo.setLayout(new BoxLayout(panelInsumo, BoxLayout.PAGE_AXIS));
				panelInsumo.add(panelVolver, BorderLayout.NORTH);
				panelInsumo.add(subPanel);
				frameTrabajoPractico.getContentPane().add(panelInsumo, "card__Insumo");
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
        final JTextField textoDensidad = new JTextField(20);
        final JTextField textoPeso = new JTextField(20);
        
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
        panelGeneral.add(textoPeso, constraintsGeneral);
        
        panelLiquido.setLayout(new GridBagLayout());
        GridBagConstraints constraintsLiquido = new GridBagConstraints();
        constraintsLiquido.anchor = GridBagConstraints.WEST;
        constraintsLiquido.insets = new Insets(10, 10, 10, 10);
        
        constraintsLiquido.gridx = 0;
        constraintsLiquido.gridy = 0; 
        panelLiquido.add(densidadEtiqueta, constraintsLiquido);
        
        constraintsLiquido.gridx = 1;
        panelLiquido.add(textoDensidad, constraintsLiquido);
        
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
						botonIGeneral.isSelected() && textoPeso.getText().length() == 0 ||
						botonILiquido.isSelected() && textoDensidad.getText().length() == 0) {
					JOptionPane.showMessageDialog(new JPanel(), "Error: Uno de los campos se encuentra vacío", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					
					Double costo;
					Double peso = 0d;
					Double densidad = 0d;
					try {
						costo = Double.parseDouble(textoCosto.getText());
						if(textoPeso.getText().length() == 0) {
							densidad = Double.parseDouble(textoDensidad.getText());
						}
						else {
							peso = Double.parseDouble(textoPeso.getText());
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(new JFrame(), "Uno o más datos ingresados no son válidos",
								"Información", JOptionPane.INFORMATION_MESSAGE);

						return;
					}
					Insumo i;
					
					if(botonIGeneral.isSelected()) {
						

						i = new InsumoGeneral();
						((InsumoGeneral)i).setPeso(peso);
					}
					else {
						i = new InsumoLiquido();
						((InsumoLiquido)i).setDensidad(densidad);
					}
					
					i.setDescripcion(textoDescripcion.getText());
					i.setUnidadDeMedida(UnidadDeMedida.valueOf(unidadDeMedidaComboBox.getSelectedItem().toString()));
					i.setCosto(costo);
					idInsumo ++;
					i.setId(idInsumo);
				
					insumoService.saveOrUpdate(i, false);
					insumos.add(i);
					actualizarTablaInsumo();
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
					JOptionPane.showMessageDialog(dialogo, "Error: Uno de los campos se encuentra vacío", "Error", JOptionPane.ERROR_MESSAGE);
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
				
					insumoService.saveOrUpdate(i, true);
					actualizarTablaInsumo();
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
	
	public void actualizarTablaInsumo() {
		//crear modelo tabla
				String[] columnas = {"Id", "Descripcion", "Unidad de medida", "Costo", "Tipo", "Peso", "Stock Total"};
				modeloInsumo = new DefaultTableModel(columnas, 0);
			
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

	//menu Camiones
	private void menuCamion() {
		//Boton volver al menu principal
				JPanel panelVolverC = new JPanel(new GridBagLayout());
				GridBagConstraints volverConstraintsC = new GridBagConstraints();
				volverConstraintsC.insets = new Insets(10, 10, 10, 10);
				volverConstraintsC.fill = GridBagConstraints.WEST;
				volverConstraintsC.gridx = 1;
				volverConstraintsC.gridy = 0;
				JButton volverC = new JButton("Volver");
				volverC.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						cl.show(frameTrabajoPractico.getContentPane(), "card__MainMenu");
					}
					
				});
				panelVolverC.add(volverC, volverConstraintsC);
				
				panelCamion = new JPanel();
				panelCamion.setLayout(new BoxLayout(panelCamion, BoxLayout.PAGE_AXIS));
				panelCamion.add(panelVolverC, BorderLayout.NORTH);
				panelCamion.add(new PanelCamiones(new PanelModificarCamion(), camiones));
				frameTrabajoPractico.getContentPane().add(panelCamion, "card__Camion");
	}
	
	//menu Plantas
	private void menuPlanta() {
		//Boton volver al menu principal
				JPanel panelVolver = new JPanel(new GridBagLayout());
				GridBagConstraints volverConstraints = new GridBagConstraints();
				volverConstraints.insets = new Insets(10, 10, 10, 10);
				volverConstraints.fill = GridBagConstraints.WEST;
				volverConstraints.gridx = 1;
				volverConstraints.gridy = 0;
				JButton volver = new JButton("Volver");
				volver.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						cl.show(frameTrabajoPractico.getContentPane(), "card__MainMenu");
					}
					
				});
				panelVolver.add(volver, volverConstraints);
				
				JPanel subPanel = new JPanel();
				
				subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.PAGE_AXIS));
				JPanel panelTitulo = new JPanel(new GridBagLayout());

				//Crear tabla Plantas
				tablaPlanta = new JTable(modeloPlanta);
				actualizarTablaPlanta();
				tablaPlanta.setDefaultEditor(Object.class, null);

				//Crear tabla Stock
				tablaStock = new JTable(modeloStock);
				actualizarTablaStock(null, null);
				tablaStock.setDefaultEditor(Object.class, null);
				
				//Crear botones

				//Boton BORRAR
				JButton borrar = new JButton("Borrar");
				borrar.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(tablaPlanta.getSelectedRow() < 0) {
							JOptionPane.showMessageDialog(new JFrame(), "No se ha seleccionado una planta", "Informacion", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							int res = JOptionPane.showConfirmDialog(subPanel, "¿Desea borrar esta planta?", "Advertencia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
							if (res == 0) {
								plantaService.borrarPlanta((Integer) modeloPlanta.getValueAt(tablaPlanta.getSelectedRow(), 0));
								plantas.remove(plantas.stream().filter(f -> f.getId() == (Integer) modeloPlanta.getValueAt(tablaPlanta.getSelectedRow(), 0)).collect(Collectors.toList()).get(0));
								actualizarTablaPlanta();
							}
						}
						
					}
				});
				
				
				//Boton AÑADIR
				JButton crear = new JButton("Añadir");
				crear.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent e) {
							crearPlanta();
					}
					
				});
			
				//Boton Modificar
				JButton modificar = new JButton("Modificar");
				modificar.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						if(tablaPlanta.getSelectedRow() < 0) {
							JOptionPane.showMessageDialog(new JFrame(), "No se ha seleccionado una planta", "Informacion", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							Planta p = plantas.stream().filter(f -> f.getId() == (Integer) modeloPlanta.getValueAt(tablaPlanta.getSelectedRow(), 0)).collect(Collectors.toList()).get(0);
							modificarPlanta(p);					
						}
						
					}
					
				});
				
				//Boton agregar Stock
				JButton actualizarStock = new JButton("Agregar Insumo");
				actualizarStock.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						if(tablaPlanta.getSelectedRow() < 0) {
							JOptionPane.showMessageDialog(new JFrame(), "No se ha seleccionado una planta", "Informacion", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							Planta p = plantas.stream().filter(f -> f.getId() == (Integer) modeloPlanta.getValueAt(tablaPlanta.getSelectedRow(), 0)).collect(Collectors.toList()).get(0);
							agregarInsumo(p);					
						}
						
					}
					
				});

				//Boton agregar Ruta
				JButton agregarRuta = new JButton("Agregar Ruta");
				agregarRuta.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						
						if(plantas.size() < 2) {
								JOptionPane.showMessageDialog(new JFrame(), "No hay Plantas suficientes", "Informacion", JOptionPane.INFORMATION_MESSAGE);
							}
							else {
								agregarRuta();
							}
						}
					
				});

				//Boton realizar Pedido
				JButton realizarPedido = new JButton("Realizar Pedido");
				realizarPedido.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						if(tablaPlanta.getSelectedRow() < 0) {
							JOptionPane.showMessageDialog(new JFrame(), "No se ha seleccionado una planta", "Informacion", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							Planta p = plantas.stream().filter(f -> f.getId() == (Integer) modeloPlanta.getValueAt(tablaPlanta.getSelectedRow(), 0)).collect(Collectors.toList()).get(0);
							realizarPedido(p);					
						}
					}
					
				});
				
				//Titulo, paso a main?
			    GridBagConstraints constraintsTitulo = new GridBagConstraints();
			    constraintsTitulo.anchor = GridBagConstraints.CENTER; 
			    constraintsTitulo.insets = new Insets(10, 10, 10, 10);
			          
			    constraintsTitulo.gridx = 5;
			    constraintsTitulo.gridy = 0;     
			    JLabel titulos = new JLabel("Control de Plantas");
			    titulos.setFont(new Font("Panel Plantas", Font.TYPE1_FONT, 30));
			    titulos.setSize(new Dimension(300, 400));
			    panelTitulo.add(titulos, constraintsTitulo);
			    
			    //Panel Planta
			    
			    JPanel panelPlantas = new JPanel();
			    panelPlantas.setLayout(new BoxLayout(panelPlantas, BoxLayout.PAGE_AXIS));
			    
			    // Botones tabla Planta
			    JPanel botonesPlantas = new JPanel(new GridBagLayout());
			    GridBagConstraints constraints = new GridBagConstraints();
			    constraints.anchor = GridBagConstraints.CENTER; 
			    constraints.insets = new Insets(0, 5, 10, 10);
			    
			    constraints.gridx = 0;
			    constraints.gridy = 0;     
			    botonesPlantas.add(borrar, constraints);
			  
			    constraints.gridx = 1;
			    botonesPlantas.add(modificar, constraints);
			          
			    constraints.gridx = 2;    
			    botonesPlantas.add(crear, constraints);
			          
			    constraints.gridx = 3;
			    botonesPlantas.add(actualizarStock, constraints);
			    
			    constraints.gridx = 4;
			    botonesPlantas.add(agregarRuta, constraints);

			    constraints.gridx = 5;
			    botonesPlantas.add(realizarPedido, constraints);

			    panelPlantas.add(botonesPlantas);
			    
			    //tabla plantas
			    JScrollPane tableSP = new JScrollPane(tablaPlanta);
				tableSP.setPreferredSize(new Dimension(600, 500)); //ver
				
			    JPanel panelTablaP = new JPanel(new GridBagLayout());
			    
			    GridBagConstraints constraintsTabla = new GridBagConstraints();
			    constraintsTabla.anchor = GridBagConstraints.BOTH;
			    constraintsTabla.insets = new Insets(0, 0, 0, 5);
			    constraintsTabla.gridy = 2;
			    constraintsTabla.gridx = 1;
			    constraintsTabla.gridwidth = 8;
			    constraintsTabla.anchor = GridBagConstraints.CENTER;
			    panelTablaP.add(tableSP, constraintsTabla);
			    
			    panelPlantas.add(panelTablaP);
			    
			    //Panel Stock
			    JPanel panelStock = new JPanel();
			    panelStock.setLayout(new BoxLayout(panelStock, BoxLayout.PAGE_AXIS));
			    
			    //Botones stock
			    JLabel plantaEtiqueta = new JLabel("Planta");
			    JLabel insumoEtiqueta = new JLabel("Insumo");
			    
			    final JTextField textoPlanta = new JTextField(20);
			    final JTextField textoInsumo = new JTextField(20);
			    
			    JButton filtrar = new JButton ("Filtrar");
			    filtrar.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						
						actualizarTablaStock(textoPlanta.getText(), textoInsumo.getText());
						
					}
			    	
			    });
			    
			    JPanel botonesStock = new JPanel(new GridBagLayout());
			    
			    constraints.gridx = 0;
			    constraints.gridy = 0;     
			    botonesStock.add(plantaEtiqueta, constraints);
			  
			    constraints.gridx = 1;
			    botonesStock.add(textoPlanta, constraints);
			          
			    constraints.gridx = 2;    
			    botonesStock.add(insumoEtiqueta, constraints);
			          
			    constraints.gridx = 3;
			    botonesStock.add(textoInsumo, constraints);
			    
			    constraints.gridx = 4;
			    botonesStock.add(filtrar, constraints);
			    
			    panelStock.add(botonesStock);
			    
			    //Panel tabla stock
			    JScrollPane tablaSP = new JScrollPane(tablaStock);
				tablaSP.setPreferredSize(new Dimension(600, 500)); //ver
			    JPanel panelTablaS = new JPanel(new GridBagLayout());
			    
			    panelTablaS.add(tablaSP, constraintsTabla);
			    panelStock.add(panelTablaS);
			    
			    //Agregamos panel Titulo, panelPlanta y panel Stock a la pantalla 
			    subPanel.add(panelTitulo);
			    
			    JPanel tablas = new JPanel();
			    tablas.setLayout(new BoxLayout(tablas, BoxLayout.LINE_AXIS));
			    tablas.add(panelPlantas);
			    tablas.add(panelStock);
			    
			    subPanel.add(tablas);
				
				panelPlanta = new JPanel();
				panelPlanta.setLayout(new BoxLayout(panelPlanta, BoxLayout.PAGE_AXIS));
				panelPlanta.add(panelVolver, BorderLayout.NORTH);
				panelPlanta.add(subPanel);
				frameTrabajoPractico.getContentPane().add(panelPlanta, "card__Planta");
	}

	public void crearPlanta() {
		final JDialog dialogo = new JDialog(new JFrame(), "Crear planta", Dialog.ModalityType.DOCUMENT_MODAL); 
		dialogo.setResizable(false);
		dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panelCrear = new JPanel();
        panelCrear.setLayout(new BoxLayout(panelCrear, BoxLayout.PAGE_AXIS)); //VERRRRRRRRRRRRRRR
        JButton botonAñadir = new JButton("Guardar");
        
        //Etiquetas
        JLabel nombreEtiqueta = new JLabel("Nombre: ");
        JLabel tipoEtiqueta = new JLabel("Tipo: ");
        
    	//Campos
        final JTextField textoNombre = new JTextField(20);
        final JComboBox<TipoPlanta> tipoComboBox = new JComboBox<TipoPlanta>(); 
        tipoComboBox.setModel(new DefaultComboBoxModel<TipoPlanta>(TipoPlanta.values()));
        
        //Panel del mensaje
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
          
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(nombreEtiqueta, constraints);
  
        constraints.gridx = 1;
        newPanel.add(textoNombre, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 2;     
        newPanel.add(tipoEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(tipoComboBox, constraints);
         
        panelCrear.add(newPanel);
        
        //Crear Planta
        ActionListener al =  new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if(textoNombre.getText().length() == 0 ) {
					JOptionPane.showMessageDialog(dialogo, "Error: El nombre esta vacío", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					boolean todoOk = true;
					switch (TipoPlanta.valueOf(tipoComboBox.getSelectedItem().toString())) {
					case Acopio_Final:
						int i = 0;
						while(todoOk && i < plantas.size()) {
							if(plantas.get(i).getTipoPlanta() == TipoPlanta.Acopio_Final) todoOk = false;
							i++;
						}
						break;
					case Acopio_Puerto:
						int j = 0;
						while(todoOk && j < plantas.size()) {
							if(plantas.get(j).getTipoPlanta() == TipoPlanta.Acopio_Puerto) todoOk = false;
							j++;
						}
						break;
					case Produccion:
						break;
					default:
						break;
					
					}
					if(!todoOk) {
						JOptionPane.showMessageDialog(dialogo, "Error: Solo puede haber una planta de ese tipo", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						Planta p = new Planta();
						
						p.setNombre(textoNombre.getText());
						p.setTipoPlanta(TipoPlanta.valueOf(tipoComboBox.getSelectedItem().toString()));
						idPlanta++;
						p.setId(idPlanta);
					
						plantaService.crearPlanta(p, false);
						plantas.add(p);
						actualizarTablaPlanta();
						dialogo.dispose();
					}
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
        dialogo.setSize(300, 200); 
        dialogo.setVisible(true); 
	}
		
	public void modificarPlanta(final Planta p) {
        
		final JDialog dialogo = new JDialog(new JFrame(), "Modificar planta", Dialog.ModalityType.DOCUMENT_MODAL); 
		dialogo.setResizable(false);
		dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panelModificar = new JPanel();
        panelModificar.setLayout(new BoxLayout(panelModificar, BoxLayout.PAGE_AXIS));
        
      //Etiquetas
        JLabel nombreEtiqueta = new JLabel("Nombre: ");
        JLabel tipoEtiqueta = new JLabel("Tipo: ");
        
    	//Campos
        final JTextField textoNombre = new JTextField(p.getNombre(), 20);
        final JComboBox<TipoPlanta> tipoComboBox = new JComboBox<TipoPlanta>(); 
        tipoComboBox.setModel(new DefaultComboBoxModel<TipoPlanta>(TipoPlanta.values()));
        tipoComboBox.setSelectedItem(p.getTipoPlanta());
        
        //Boton       
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
        newPanel.add(nombreEtiqueta, constraints);
  
        constraints.gridx = 1;
        newPanel.add(textoNombre, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(tipoEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(tipoComboBox, constraints);
                  
        panelModificar.add(newPanel);
  
        //Creamos el boton
        ActionListener al =  new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if(		textoNombre.getText().length() == 0 ) {
					JOptionPane.showMessageDialog(dialogo, "Error: Ingrese un nombre", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					p.setNombre(textoNombre.getText());
					p.setTipoPlanta(TipoPlanta.valueOf(tipoComboBox.getSelectedItem().toString()));
					plantaService.crearPlanta(p, true);
					actualizarTablaPlanta();
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
	
	public void agregarInsumo(final Planta p) {
		
		final JDialog dialogo = new JDialog(new JFrame(), "Agregar Insumo", Dialog.ModalityType.DOCUMENT_MODAL); 
		dialogo.setResizable(false);
		dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panelAgregarI = new JPanel();
        panelAgregarI.setLayout(new BoxLayout(panelAgregarI, BoxLayout.PAGE_AXIS)); //VERRRRRRRRRRRRRRR
        JButton botonAñadir = new JButton("Guardar");
        
        //Etiquetas
        JLabel insumoEtiqueta = new JLabel("Insumo: ");
        JLabel cantidadEtiqueta = new JLabel("Cantidad: ");
        JLabel ptoPedidoEtiqueta = new JLabel("Punto de Pedido: ");
        
    	//Campos
        final JComboBox<String> insumoComboBox = new JComboBox<String>(); 
        final JTextField textoCantidad = new JTextField(16);
        final JTextField textoPtoPedido = new JTextField(16);
        
        //Obtener nombres Insumos
        InsumoService is = new InsumoService();
        final List<Insumo> insumos = is.buscarTodos();
        for(Insumo i: insumos) {
        	insumoComboBox.addItem(i.getDescripcion());
        }
       
        //Panel del mensaje
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
          
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(insumoEtiqueta, constraints);
  
        constraints.gridx = 1;
        newPanel.add(insumoComboBox, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 2;     
        newPanel.add(cantidadEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(textoCantidad, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;     
        newPanel.add(ptoPedidoEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(textoPtoPedido, constraints);
         
        panelAgregarI.add(newPanel);
        
        //Crear Planta
        ActionListener al =  new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if(		textoCantidad.getText().length() == 0 ||
						textoPtoPedido.getText().length() == 0) {
					JOptionPane.showMessageDialog(dialogo, "Error: Uno de los campos se encuentra vacío", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					Integer cantidad;
					Integer ptoPedido;
					try {
						cantidad = Integer.parseInt(textoCantidad.getText());
						ptoPedido = Integer.parseInt(textoPtoPedido.getText());
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(new JFrame(), "Uno o más datos ingresados no son válidos",
								"Información", JOptionPane.INFORMATION_MESSAGE);

						return;
					}
					Stock s = new Stock();
					String d = insumoComboBox.getSelectedItem().toString();
					boolean encontrado = false;
					int j = 0;
					while(!encontrado) {
						if(insumos.get(j).getDescripcion().equals(d)) {
							s.setInsumo(insumos.get(j));
							encontrado = true;
						}
						j++;
					}
					s.setCantidad(cantidad);
					s.setPuntoDeReposicion(ptoPedido);
					p.agregarStock(s);
					plantaService.crearPlanta(p, true);
					actualizarTablaStock(null, null);
					dialogo.dispose();
					
				}
			}
        	
        };
        botonAñadir.addActionListener(al); 
  
        JPanel panelBoton = new JPanel();
        panelBoton.setSize(new Dimension(400, 120));
        panelBoton.setLayout(new BorderLayout(0,0));
        panelBoton.add(botonAñadir, BorderLayout.CENTER); 
  
        panelAgregarI.add(panelBoton);
        dialogo.add(panelAgregarI);  
        dialogo.setSize(400, 300); 
        dialogo.setVisible(true); 
	}
	
	public void agregarRuta() {
		final JDialog dialogo = new JDialog(new JFrame(), "Agregar Ruta", Dialog.ModalityType.DOCUMENT_MODAL); 
		dialogo.setResizable(false);
		dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panelAgregarI = new JPanel();
        panelAgregarI.setLayout(new BoxLayout(panelAgregarI, BoxLayout.PAGE_AXIS)); //VERRRRRRRRRRRRRRR
        JButton botonAñadir = new JButton("Guardar");
        
        //Etiquetas
        JLabel plantaOEtiqueta = new JLabel("Planta de origen: ");
        JLabel plantaDEtiqueta = new JLabel("Planta destino: ");
        JLabel distanciaEtiqueta = new JLabel("Distancia (Km): ");
        JLabel duracionEtiqueta = new JLabel("Duracion (Horas): ");
        JLabel pesoEtiqueta = new JLabel("Peso maximo transportable (Kg): ");
        
    	//Campos
        final JComboBox<String> plantaOComboBox = new JComboBox<String>();
        final JComboBox<String> plantaDComboBox = new JComboBox<String>();
        final JTextField textoDistancia = new JTextField(16);
        final JTextField textoDuracion = new JTextField(16);
        final JTextField textoPeso = new JTextField(16);
        
        //Obtener nombres Plantas
        for(Planta p: plantas) {
        	switch (p.getTipoPlanta()) {
			case Acopio_Final:
				plantaDComboBox.addItem(p.getNombre());
				break;
			case Acopio_Puerto:
				plantaOComboBox.addItem(p.getNombre());
				break;
			case Produccion:
	        	plantaOComboBox.addItem(p.getNombre());
	        	plantaDComboBox.addItem(p.getNombre());
				break;
			default:
				break;
        	
        	}
        }
       
        //Panel del mensaje
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
          
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(plantaOEtiqueta, constraints);
  
        constraints.gridx = 1;
        newPanel.add(plantaOComboBox, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 2;     
        newPanel.add(plantaDEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(plantaDComboBox, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;     
        newPanel.add(distanciaEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(textoDistancia, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 4;     
        newPanel.add(duracionEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(textoDuracion, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 5;     
        newPanel.add(pesoEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(textoPeso, constraints);
        
        panelAgregarI.add(newPanel);
        
        //Crear Ruta
        ActionListener al =  new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if(		textoDistancia.getText().length() == 0 ||
						textoDuracion.getText().length() == 0 ||
						textoPeso.getText().length() == 0) {
					JOptionPane.showMessageDialog(dialogo, "Error: Uno de los campos se encuentra vacío", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					if(plantaOComboBox.getSelectedItem().toString() == plantaDComboBox.getSelectedItem().toString()) {
						JOptionPane.showMessageDialog(dialogo, "Error: No se puede crear una ruta desde una planta hacia si misma", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						Double distancia;
						Integer duracion;
						Double pesoMax;
						try {
							distancia = Double.parseDouble(textoDistancia.getText());
							pesoMax = Double.parseDouble(textoPeso.getText());
							duracion = Integer.parseInt(textoDuracion.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(new JFrame(), "Uno o más datos ingresados no son válidos",
									"Información", JOptionPane.INFORMATION_MESSAGE);

							return;
						}
						Planta po = null;
						Planta pd = null;
						int i = 0;
						while(po == null || pd == null) {
							if(plantas.get(i).getNombre() == plantaOComboBox.getSelectedItem().toString()) {
								po = plantas.get(i);
							}
							else {
								if(plantas.get(i).getNombre() == plantaDComboBox.getSelectedItem().toString()) {
									pd = plantas.get(i);
								}
							}
							i++;
						}
						
						Ruta r = new Ruta();
						r.setOrigen(po);
						r.setDestino(pd);
						r.setDistanciaKM(distancia);
						r.setDuracion(duracion);
						r.setPesoMaxPorDia(pesoMax);
						idRuta++;
						r.setId(idRuta);
						
						RutaService rs = new RutaService();
						rs.saveOrUpdate(r, false);
						rutas.add(r);
						dialogo.dispose();
					}
				}
			}
        	
        };
        botonAñadir.addActionListener(al);   
        botonAñadir.setPreferredSize(new Dimension(100, 100));
        
        JPanel panelBoton = new JPanel();
        panelBoton.setSize(new Dimension(400, 40));
        panelBoton.setLayout(new BorderLayout(0,0));
        panelBoton.add(botonAñadir, BorderLayout.CENTER); 
  
        panelAgregarI.add(panelBoton);
        dialogo.add(panelAgregarI);  
        dialogo.setSize(400, 300); 
        dialogo.setVisible(true); 
	}
	
	public void realizarPedido(final Planta p) {
		final JDialog dialogo = new JDialog(new JFrame(), "Realizar pedido", Dialog.ModalityType.DOCUMENT_MODAL); 
		dialogo.setResizable(false);
		dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panelCrear = new JPanel();
        panelCrear.setLayout(new BoxLayout(panelCrear, BoxLayout.PAGE_AXIS)); //VERRRRRRRRRRRRRRR

        final Pedido pe = new Pedido();
        JButton botonCrear = new JButton("Realizar Pedido");
        
        //Etiquetas
        JLabel plantaEtiqueta = new JLabel("Planta: ");
        JLabel fechaEtiqueta = new JLabel("Fecha Maxima de Entrega: ");
        
    	//Campos
        JLabel nombrePlanta = new JLabel(p.getNombre());

        //fecha
        Calendar calendar = Calendar.getInstance();
    	Date initDate = (Date) calendar.getTime();
    	calendar.add(Calendar.YEAR, -100);
    	Date earliestDate = (Date) calendar.getTime();
    	calendar.add(Calendar.YEAR, 200);
    	Date latestDate = (Date) calendar.getTime();
    	SpinnerModel dateModel = new SpinnerDateModel(initDate,
    	                                 earliestDate,
    	                                 latestDate,
    	                                 Calendar.YEAR);
    	
        final JSpinner fechaMax = new JSpinner(dateModel);
        fechaMax.setEditor(new JSpinner.DateEditor(fechaMax, "dd/MM/yyyy"));
        
        //Boton agragar item
        JButton botonAñadir = new JButton("Añadir item");
        botonAñadir.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				agregarItem(pe);
				
			}
        	
        });
        
        //Panel del mensaje
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
          
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(plantaEtiqueta, constraints);
  
        constraints.gridx = 1;
        newPanel.add(nombrePlanta, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 2;     
        newPanel.add(fechaEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(fechaMax, constraints);
         
        constraints.gridx = 2;
        newPanel.add(botonAñadir, constraints);
                 
        panelCrear.add(newPanel);
        
        //Tabla Items
		tablaItem = new JTable(modeloItem);
		actualizarTablaItem(pe);
		tablaItem.setDefaultEditor(Object.class, null);
		
        JScrollPane tableSP = new JScrollPane(tablaItem);
		tableSP.setPreferredSize(new Dimension(600, 500)); //ver
		
	    JPanel panelTabla = new JPanel(new GridBagLayout());
	    
	    GridBagConstraints constraintsTabla = new GridBagConstraints();
	    constraintsTabla.anchor = GridBagConstraints.BOTH;
	    constraintsTabla.insets = new Insets(0, 0, 0, 5);
	    constraintsTabla.gridy = 2;
	    constraintsTabla.gridx = 1;
	    constraintsTabla.gridwidth = 8;
	    constraintsTabla.anchor = GridBagConstraints.CENTER;
	    panelTabla.add(tableSP, constraintsTabla);
	    
	    panelCrear.add(panelTabla);
	    
        //Crear Pedido
        ActionListener al =  new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				pe.setFechaEntrega(((Date)fechaMax.getValue()).toInstant()
					      .atZone(ZoneId.systemDefault())
					      .toLocalDate());
				pe.setFechaSolicitud(LocalDate.now());
				pe.setEstado(Estado.CREADA);
				pe.setDestino(p);
				idPedido++;
				pe.setNroOrden(idPedido);
				System.out.println("Pedido: "+pe);
				p.addPedido(pe);
				pedidos.add(pe);
				actualizarTablaPedidosCreados();
				
				plantaService.crearPlanta(p, true);
				dialogo.dispose();
			}
        	
        };
        botonCrear.addActionListener(al); 
  
        JPanel panelBoton = new JPanel();
        panelBoton.setSize(new Dimension(400, 120));
        panelBoton.setLayout(new BorderLayout(0,0));
        panelBoton.add(botonCrear, BorderLayout.CENTER); 
  
        panelCrear.add(panelBoton);
        dialogo.add(panelCrear);  
        dialogo.setSize(800, 700); 
        dialogo.setVisible(true); 
	}
	
	public void agregarItem(final Pedido pe) {
		final JDialog dialogo = new JDialog(new JFrame(), "Agregar Item", Dialog.ModalityType.DOCUMENT_MODAL); 
		dialogo.setResizable(false);
		dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panelCrear = new JPanel();
        panelCrear.setLayout(new BoxLayout(panelCrear, BoxLayout.PAGE_AXIS)); //VERRRRRRRRRRRRRRR

        JButton botonCrear = new JButton("Agregar Item");
        
        //Etiquetas
        JLabel insumoEtiqueta = new JLabel("Insumo: ");
        JLabel cantidadEtiqueta = new JLabel("Cantidad: ");
        
        //Campos
        final JComboBox<String> insumoBox = new JComboBox<String>();
        final JTextField textoCantidad = new JTextField(20);
        
        InsumoService is = new InsumoService();
        final List<Insumo> insumos = is.buscarTodos();
        for(Insumo i: insumos) {
        	insumoBox.addItem(i.getDescripcion());
        }
        
        //Panel del mensaje
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
          
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(insumoEtiqueta, constraints);
  
        constraints.gridx = 1;
        newPanel.add(insumoBox, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 2;     
        newPanel.add(cantidadEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(textoCantidad, constraints);
         
        panelCrear.add(newPanel);
        
        //Crear Planta
        ActionListener al =  new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if( textoCantidad.getText().length() == 0) {
					JOptionPane.showMessageDialog(dialogo, "Error: El campo Cantidad se encuentra vacío", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					Integer cantidad;
					try {
						cantidad = Integer.parseInt(textoCantidad.getText());
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(new JFrame(), "Cantidad es un campo numerico",
								"Información", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					Insumo i = null;
					Item item = new Item();
					boolean encontrado = false;
					int j = 0;
					while(!encontrado) {
						if(insumos.get(j).getDescripcion() == insumoBox.getSelectedItem()) {
							encontrado = true;
							i = insumos.get(j);
						}
						j++;
					}
					
					item.setInsumo(i);
					item.setCantidad(cantidad);
					pe.addItem(item);
					
					actualizarTablaItem(pe);
					dialogo.dispose();
				}
			}
        	
        };
        botonCrear.addActionListener(al); 
  
        JPanel panelBoton = new JPanel();
        panelBoton.setSize(new Dimension(400, 120));
        panelBoton.setLayout(new BorderLayout(0,0));
        panelBoton.add(botonCrear, BorderLayout.CENTER); 
  
        panelCrear.add(panelBoton);
        dialogo.add(panelCrear);  
        dialogo.setSize(300, 200); 
        dialogo.setVisible(true); 
	}
			
	public void actualizarTablaPlanta() {

		//crear modelo tabla
				String[] columnas = {"Id", "Nombre", "Tipo"};
				modeloPlanta = new DefaultTableModel(columnas, 0);					
			
				for(Planta p: plantas) {
					Integer id = p.getId();
					String nombre = p.getNombre();
					String tipoPlanta = p.getTipoPlanta().toString();
					Object[] renglon = {id, nombre, tipoPlanta};
					modeloPlanta.addRow(renglon);
				}

				//actualizar modelo
				tablaPlanta.setModel(modeloPlanta);

	}

	public void actualizarTablaStock(String planta, String insumo) {

				boolean plantaB = planta == null;
				boolean insumoB = insumo == null;
				
				//crear modelo tabla
				String[] columnas = {"Planta", "Insumo", "Stock", "Punto de pedido", "Stock total"};
				modeloStock = new DefaultTableModel(columnas, 0);
				
				for(Planta p: plantas) {
					for(Stock s: p.getStock()) {
						s.getInsumo().sumCantidad(s.getCantidad());
					}
				}
			
				for(Planta p: plantas) {
					String nombre = p.getNombre();
					
					if(plantaB || nombre.contains(planta)) {
						for(Stock s: p.getStock()) {
							if(s.getCantidad() < s.getPuntoDeReposicion() && (insumoB || s.getInsumo().getDescripcion().contains(insumo))) {
								String i = s.getInsumo().getDescripcion();
								Integer cantidad = s.getCantidad();
								Integer ptoP = s.getPuntoDeReposicion();
								Integer cantTot = s.getInsumo().getCantidad();

								Object[] renglon = {nombre, i, cantidad, ptoP, cantTot};
								modeloStock.addRow(renglon);
							}
					}
					}
				}

				//actualizar modelo
				tablaStock.setModel(modeloStock);

	}
		
	public void actualizarTablaItem(Pedido pe) {
		//crear modelo tabla
		String[] columnas = {"Insumo", "Cantidad", "Precio"};
		modeloItem = new DefaultTableModel(columnas, 0);
	
		for(Item i: pe.getItems()) {
			String insumo = i.getInsumo().getDescripcion();
			Integer cantidad = i.getCantidad();
			Double precio = i.precio();

			Object[] renglon = {insumo, cantidad, precio};
			modeloItem.addRow(renglon);
		}

		//actualizar modelo
		tablaItem.setModel(modeloItem);
	}

	//menu Pedidos
	private void menuPedidos() {
		//Boton volver al menu principal
				JPanel panelVolver = new JPanel(new GridBagLayout());
				GridBagConstraints volverConstraints = new GridBagConstraints();
				volverConstraints.insets = new Insets(10, 10, 10, 10);
				volverConstraints.fill = GridBagConstraints.WEST;
				volverConstraints.gridx = 1;
				volverConstraints.gridy = 0;
				JButton volver = new JButton("Volver");
				volver.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						cl.show(frameTrabajoPractico.getContentPane(), "card__MainMenu");
					}
					
				});
				panelVolver.add(volver, volverConstraints);
				
				JPanel subPanel = new JPanel();
				
				subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.PAGE_AXIS));
				JPanel panelTitulo = new JPanel(new GridBagLayout());

				//Crear tabla Creados
				tablaPedidosCreados = new JTable(modeloPedidosCreados);
				actualizarTablaPedidosCreados();
				tablaPedidosCreados.setDefaultEditor(Object.class, null);

				//Crear tabla Procesados
				tablaPedidosProcesados = new JTable(modeloPedidosProcesados);
				actualizarTablaPedidosProcesados();
				tablaPedidosProcesados.setDefaultEditor(Object.class, null);		
						
				//Titulo, paso a main?
			    GridBagConstraints constraintsTitulo = new GridBagConstraints();
			    constraintsTitulo.anchor = GridBagConstraints.CENTER; 
			    constraintsTitulo.insets = new Insets(10, 10, 10, 10);
			          
			    constraintsTitulo.gridx = 5;
			    constraintsTitulo.gridy = 0;     
			    JLabel titulos = new JLabel("Control de Pedidos");
			    titulos.setFont(new Font("Panel Pedidos", Font.TYPE1_FONT, 30));
			    titulos.setSize(new Dimension(300, 400));
			    panelTitulo.add(titulos, constraintsTitulo);
			    
			    //Panel Creado
			    
			    JPanel panelCreados = new JPanel();
			    panelCreados.setLayout(new BoxLayout(panelCreados, BoxLayout.PAGE_AXIS));
			    
			    // Boton creados
				JButton ver = new JButton("Ver detalles");
				ver.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(tablaPedidosCreados.getSelectedRow() < 0) {
							JOptionPane.showMessageDialog(new JFrame(), "No se ha seleccionado un insumo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							Pedido p = pedidos.stream().filter(f -> f.getNroOrden() == (Integer) modeloPedidosProcesados.getValueAt(tablaPedidosProcesados.getSelectedRow(), 0)).collect(Collectors.toList()).get(0);
							verDetalles(p);
						}
						
					}
				});
			    JPanel botonesPlanta = new JPanel(new GridBagLayout());
			    GridBagConstraints constraints = new GridBagConstraints();
			    constraints.anchor = GridBagConstraints.CENTER; 
			    constraints.insets = new Insets(0, 5, 10, 10);
			    
			    constraints.gridx = 9;
			    constraints.gridy = 0;     
			    botonesPlanta.add(ver, constraints);
			    
			    //tabla pedidos
			    JScrollPane tableSP = new JScrollPane(tablaPedidosCreados);
				tableSP.setPreferredSize(new Dimension(1000, 500)); //ver
				
			    JPanel panelTablaP = new JPanel(new GridBagLayout());
			    
			    GridBagConstraints constraintsTabla = new GridBagConstraints();
			    constraintsTabla.anchor = GridBagConstraints.BOTH;
			    constraintsTabla.insets = new Insets(0, 0, 0, 5);
			    constraintsTabla.gridy = 2;
			    constraintsTabla.gridx = 1;
			    constraintsTabla.gridwidth = 8;
			    constraintsTabla.anchor = GridBagConstraints.CENTER;
			    panelTablaP.add(tableSP, constraintsTabla);
			    
			    panelCreados.add(panelTablaP);
			    panelCreados.add(botonesPlanta);
			    
			    //Panel Procesados
			    JPanel panelProcesados = new JPanel();
			    panelProcesados.setLayout(new BoxLayout(panelProcesados, BoxLayout.PAGE_AXIS));
			    
			    //Botones procesados
			    JButton entregado = new JButton ("Entregar Pedido");
			    entregado.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						
						if(tablaPedidosCreados.getSelectedRow() < 0) {
							JOptionPane.showMessageDialog(new JFrame(), "No se ha seleccionado un insumo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							Pedido p = pedidos.stream().filter(f -> f.getNroOrden() == (Integer) modeloPedidosProcesados.getValueAt(tablaPedidosProcesados.getSelectedRow(), 0)).collect(Collectors.toList()).get(0);
							p.setEstado(Estado.ENTREGADA);
							p.setFechaEntrega(LocalDate.now());
							pedidoService.saveOrUpdate(p, true);
						}
					}
			    	
			    });
			    
			    JPanel botonesProcesados = new JPanel(new GridBagLayout());
			    
			    constraints.gridx = 9;
			    constraints.gridy = 0;     
			    botonesProcesados.add(entregado, constraints);
			    
			    //Panel tabla procesados
			    JScrollPane tablaSP = new JScrollPane(tablaPedidosProcesados);
				tablaSP.setPreferredSize(new Dimension(1000, 500)); //ver
			    JPanel panelTablaS = new JPanel(new GridBagLayout());
			    
			    panelTablaS.add(tablaSP, constraintsTabla);
			    panelProcesados.add(panelTablaS);
			    panelProcesados.add(botonesProcesados);
			    
			    //Agregamos panel Titulo 
			    subPanel.add(panelTitulo);
			    
			    //Tabs
			    JTabbedPane pedidosPanel = new JTabbedPane();
			    pedidosPanel.addTab("Pedidos Creados", panelCreados);
			    pedidosPanel.addTab("Pedidos Procesados", panelProcesados);
			    
			    //Agregamos tabs
			    subPanel.add(pedidosPanel);
				
				
				panelPedido = new JPanel();
				panelPedido.setLayout(new BoxLayout(panelPedido, BoxLayout.PAGE_AXIS));
				panelPedido.add(panelVolver, BorderLayout.NORTH);
				panelPedido.add(subPanel);
				frameTrabajoPractico.getContentPane().add(panelPedido, "card__Pedido");
	}

	public void verDetalles(Pedido p) {
		final JDialog dialogo = new JDialog(new JFrame(), "Pedido", Dialog.ModalityType.DOCUMENT_MODAL); 
		dialogo.setResizable(false);
		dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panelCrear = new JPanel();
        panelCrear.setLayout(new BoxLayout(panelCrear, BoxLayout.PAGE_AXIS)); 
        JButton botonAñadir = new JButton("Guardar");
        
        //Etiquetas
        JLabel numeroOrdenEtiqueta = new JLabel("Numero de orden: ");
        JLabel plantaNombreEtiqueta = new JLabel("Planta destino: ");
        JLabel fechaEtiqueta = new JLabel("Fecha de solicitud: ");
        JLabel plantaEtiqueta = new JLabel("Planta con Stock disponible: ");
        JLabel rutaKMEtiqueta = new JLabel("Ruta mas corta(KM): ");
        JLabel rutaHEtiqueta = new JLabel("Ruta mas rapida(horas): ");
        
    	//Campos
        JLabel textoNumeroOrden = new JLabel(p.getNroOrden().toString());
        JLabel textoPlantaNombre = new JLabel(p.getDestino().getNombre());
        JLabel textoFecha = new JLabel(p.getFechaSolicitud().toString());
        
        final JComboBox<String> plantaComboBox = new JComboBox<String>();
        JComboBox<String> rutaCorta = new JComboBox<String>();
        JComboBox<String> rutaRapida = new JComboBox<String>();
        
        //Verifico que existan plantas con stock disponible
/*        for(Planta pl: plantas) {
        	List<Stock> s = pl.getStock().stream().filter(e -> p.getItems().stream().map(a -> a.getInsumo()).collect(Collectors.toList()).contains(e.getInsumo())).collect(Collectors.toList()));
        	if(s.size() == p.getItems().size()) {
        		List<Stock> sr = s.stream().filter(f -> f.getCantidad() >= p.getItems().stream().filter(a -> a.getInsumo() == f.getInsumo()).collect(Collectors.toList()).get(0).getCantidad()).collect(Collectors.toList());
        		if(sr.size() == p.getItems().size()) plantaComboBox.addItem(pl.getNombre()); 
        	}
        }
        MapaRutas mapa=new MapaRutas();
        mapa.setRutas(rutas);
        mapa.setPlantas(plantas);
        List<Planta> plantasValidas = plantas.stream().filter(e->e.getStock().stream().filter(e -> e.getInsumo()).collect(Collectors.toList()));
        List<List<Ruta>> caminosValidos=new ArrayList<List<Ruta>>();
        for(Planta pl : plantasValidas) {
        	caminosValidos.addAll(mapa.caminosValidos(p.getDestino(), pl));
        }
*/        
        if(plantaComboBox.getItemCount() == 0) {
        	JOptionPane.showMessageDialog(dialogo, "Error: No existen plantas que tengan stock disponible para realizar este pedido", "Error", JOptionPane.ERROR_MESSAGE);
        	p.setEstado(Estado.CANCELADO);
        	pedidoService.saveOrUpdate(p, true);
        	dialogo.dispose();
        }
        
        
        //Panel del mensaje
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
          
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(numeroOrdenEtiqueta, constraints);
  
        constraints.gridx = 1;
        newPanel.add(textoNumeroOrden, constraints);
          
        constraints.gridx = 0;
        constraints.gridy = 2;     
        newPanel.add(plantaNombreEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(textoPlantaNombre, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;     
        newPanel.add(fechaEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(textoFecha, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 4;     
        newPanel.add(plantaEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(plantaComboBox, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 5;     
        newPanel.add(rutaKMEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(rutaCorta, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 6;     
        newPanel.add(rutaHEtiqueta, constraints);
          
        constraints.gridx = 1;
        newPanel.add(rutaRapida, constraints);
         
        panelCrear.add(newPanel);
        
        //Procesar Pedido
        ActionListener al =  new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
					Envio e = new Envio();
					
					if(rutaCorta.getSelectedItem().toString() == "Seleccione una opcion" && rutaRapida.getSelectedItem().toString() == "Seleccione una opcion") {
						JOptionPane.showMessageDialog(dialogo, "Seleccione una ruta", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						if(rutaCorta.getSelectedItem().toString() != "Seleccione una opcion" && rutaRapida.getSelectedItem().toString() != "Seleccione una opcion") {
							JOptionPane.showMessageDialog(dialogo, "Seleccione una unica ruta", "Error", JOptionPane.ERROR_MESSAGE);
						}
						else {
							if(rutaCorta.getSelectedItem().toString() != "Seleccione una opcion") {
								//e.setRutaElegida(rutas.stream().filter(f -> f.getId()));
							}

							e.setCamionAsignado(null);
								
							p.setEnvio(e);
							
							pedidoService.saveOrUpdate(p, true);
							actualizarTablaPedidosCreados();
							actualizarTablaPedidosProcesados();
							dialogo.dispose();
						}
					}
			}
        	
        };
        botonAñadir.addActionListener(al); 
  
        JPanel panelBoton = new JPanel();
        panelBoton.setSize(new Dimension(400, 120));
        panelBoton.setLayout(new BorderLayout(0,0));
  //      panelBoton.add(botonAñadir, BorderLayout.CENTER); 
  
        panelCrear.add(panelBoton);
        dialogo.add(panelCrear);  
        dialogo.setSize(300, 200); 
        dialogo.setVisible(true); 
	}
			
	public void actualizarTablaPedidosCreados() {

		//crear modelo tabla
				String[] columnas = {"Numero de orden", "Planta destino", "Fecha de solicitud", "SubTotal"};
				modeloPedidosCreados = new DefaultTableModel(columnas, 0);					
			
				for(Pedido p: pedidos) {
					System.out.println(p.getEstado());
					if(p.getEstado() == Estado.CREADA) {
						Integer nroOrden = p.getNroOrden();
						String nombre = p.getDestino().getNombre();
						String fechaS = p.getFechaSolicitud().toString();
						Double subtot = 0d;
						for(Item i: p.getItems()) {
							subtot += i.getCantidad() * i.getInsumo().getCosto();
						}
						Object[] renglon = {nroOrden, nombre, fechaS, subtot};
						modeloPedidosCreados.addRow(renglon);

					}
				}

				//actualizar modelo
				tablaPedidosCreados.setModel(modeloPedidosCreados);

	}

	public void actualizarTablaPedidosProcesados() {
				
				//crear modelo tabla
				String[] columnas = {"Numero Orden", "Planta destino", "Fecha de solicitud", "Patente Transporte", "Ruta", "Costo de envio", "Costo Final"};
				modeloPedidosProcesados = new DefaultTableModel(columnas, 0);
				
				for(Pedido p: pedidos) {
					if(p.getEstado() == Estado.PROCESADA) {
						int nroOrden = p.getNroOrden();
						String nombre = p.getDestino().getNombre();
						String fechaS = p.getFechaSolicitud().toString();
						String patente = p.getEnvio().getCamionAsignado().getPatente();
						String ruta = "";
						for(Ruta r: p.getEnvio().getRutaElegida()) {
							ruta.concat(r.getId().toString());
							ruta.concat(" - ");
						}
						Double costoEnvio = p.getEnvio().getCosto();
						Double costoTotal = costoEnvio;
						for(Item i: p.getItems()) {
							costoTotal += i.getCantidad() * i.getInsumo().getCosto();
						}
						Object[] renglon = {nroOrden, nombre, fechaS, patente, ruta, costoEnvio, costoTotal};
						modeloPedidosProcesados.addRow(renglon);
					
					}
				}

				//actualizar modelo
				tablaPedidosProcesados.setModel(modeloPedidosProcesados);

	}
	
	//menu Analisis
	private void menuAnalisis() {
		//Boton volver al menu principal
				JPanel panelVolver = new JPanel(new GridBagLayout());
				GridBagConstraints volverConstraints = new GridBagConstraints();
				volverConstraints.insets = new Insets(10, 10, 10, 10);
				volverConstraints.fill = GridBagConstraints.WEST;
				volverConstraints.gridx = 1;
				volverConstraints.gridy = 0;
				JButton volver = new JButton("Volver");
				volver.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						cl.show(frameTrabajoPractico.getContentPane(), "card__MainMenu");
					}
					
				});
				panelVolver.add(volver, volverConstraints);
				
				
				panelAnalisis = new JPanel();
				panelAnalisis.setLayout(new BoxLayout(panelAnalisis, BoxLayout.PAGE_AXIS));
				panelAnalisis.add(panelVolver, BorderLayout.NORTH);
				panelAnalisis.add(new PanelAnalisis(plantas, rutas));
				frameTrabajoPractico.getContentPane().add(panelAnalisis, "card__Analisis");
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				  try {
					  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					  
				  }  
			    catch (UnsupportedLookAndFeelException e) {
			    	e.printStackTrace();
			       // handle exception
			    }
			    catch (ClassNotFoundException e) {
			    	e.printStackTrace();
			       // handle exception
			    }
			    catch (InstantiationException e) {
			    	e.printStackTrace();
			       // handle exception
			    }
			    catch (IllegalAccessException e) {
			    	e.printStackTrace();
			       // handle exception
			    }
				  
				  EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								App ventana = new App();
								ventana.frameTrabajoPractico.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				System.out.println("app creada");
			}
		});
	}
}
