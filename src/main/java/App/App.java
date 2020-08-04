package App;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	
	public  App() {
		armarApp();
	}
	
	private void armarApp() {
		frameTrabajoPractico = new JFrame();
		cl = new CardLayout(0, 0);

		// Frame properties
		frameTrabajoPractico.setResizable(false);
		frameTrabajoPractico.setBackground(Color.WHITE);
		frameTrabajoPractico.setTitle("Trabajo Práctico 2019 - DIED");
		frameTrabajoPractico.setMinimumSize(new Dimension(1280, 720));
		frameTrabajoPractico.setBounds(100, 100, 450, 300);
		frameTrabajoPractico.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameTrabajoPractico.getContentPane().setLayout(cl);

		JMenuBar menuBar = new JMenuBar();
		frameTrabajoPractico.setJMenuBar(menuBar);

		menuPrincipal();

	}
	
	private void menuPrincipal() {
		
		JPanel menuPrincipal = new JPanel();
		frameTrabajoPractico.getContentPane().add(menuPrincipal, "card__MainMenu");
		
		GridLayout grid = new GridLayout(5, 1);
		menuPrincipal.setLayout(grid);
		
		JButton camionButton = new JButton();
		camionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
	}
	
	public static void main() {
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
