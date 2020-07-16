package App;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class App extends JFrame {

	JMenuBar menuBar;
	JMenu menuArchivo;
	JMenu menuEntidades;
	JMenu menuAyuda;
	JMenuItem menuItemAyuda;
	JMenuItem menuItemCamion;
	JMenuItem menuItemSalir;
	
	private App() {
		
	}
	
	private void armarApp() {
		
	}
	
	public static void main() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				  try {
			            // Set System L&F
//			          UIManager.setLookAndFeel(
//			          UIManager.getSystemLookAndFeelClassName());
//					  UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");			    }
					  UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");			    }
					  
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
		   		App app = new App();
		   		app.setTitle("Sistema de gestion logística - TP DIED 2020 ");
				// no hacer nada cuando presiona la cruz para cerrar
				app.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				app.armarApp();
				// seteo el tamaño fijo
				// app.setSize(1020, 750);
				// para que aparezca maximizado
				app.setExtendedState(app.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				app.setVisible(true);
				System.out.println("app creada");
			}
		});
	}
}
