package paneles;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.*;


public class TestPaneles extends JFrame{
	CardLayout deck;
	public TestPaneles() {
		super();
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
								TestPaneles ventana = new TestPaneles();
								ventana.deck= new CardLayout();
								ventana.setLayout(ventana.deck);
								PanelModificarCamion modif= new PanelModificarCamion();
								
								PanelCamiones panel= new PanelCamiones(modif);
								ventana.add("a",panel);
								ventana.add("b",modif);
								panel.setSize(500, 500);
								ventana.setSize(500,500);
								ventana.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				System.out.println("app creada");
			}
		});
	};
	public CardLayout getDeck() {
		return deck;
	}
}
