package App;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import paneles.PanelCamiones;
import paneles.PanelInsumo;
import paneles.PanelPlanta;

public class App {

	private JFrame frameTrabajoPractico;
	private JMenuBar menuBar;
	private JMenu menuArchivo;
	private JMenu menuEntidades;
	private JMenu menuAyuda;
	private JMenuItem menuItemAyuda;
	private JMenuItem menuItemCamion;
	private JMenuItem menuItemSalir;
	private CardLayout cl;
	private JPanel panelInsumo;
	private JPanel panelCamion;
	private JPanel panelPlanta;
	
	public  App() {
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
		
		menuPrincipal.add(camionButton);
		menuPrincipal.add(insumoButton);
		menuPrincipal.add(plantaButton);
		menuPrincipal.add(pedidoButton);
	}
	
	
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
				
				
				panelInsumo = new JPanel();
				panelInsumo.setLayout(new BoxLayout(panelInsumo, BoxLayout.PAGE_AXIS));
				panelInsumo.add(panelVolver, BorderLayout.NORTH);
				panelInsumo.add(new PanelInsumo());
				frameTrabajoPractico.getContentPane().add(panelInsumo, "card__Insumo");
	}
	
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
				//panelCamion.add(new PanelCamiones());
				frameTrabajoPractico.getContentPane().add(panelCamion, "card__Camion");
	}
	
	
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
				
				
				panelPlanta = new JPanel();
				panelPlanta.setLayout(new BoxLayout(panelPlanta, BoxLayout.PAGE_AXIS));
				panelPlanta.add(panelVolver, BorderLayout.NORTH);
				panelPlanta.add(new PanelPlanta());
				frameTrabajoPractico.getContentPane().add(panelPlanta, "card__Planta");
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
