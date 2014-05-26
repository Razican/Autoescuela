package interfaz;

import interfaz.externo.Acceso;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import baseDeDatos.BD;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class Ventana extends JFrame {

	private static final long serialVersionUID = 529791212370755802L;
	private static Ventana ventana;
	private boolean pCompleta;

	/**
	 * @param pCompleta Será en pantalla completa?
	 */
	public Ventana(boolean pCompleta)
	{
		if (pCompleta)
		{
			setAlwaysOnTop(true);
			setResizable(false);
			setUndecorated(true);

			setBounds(getGraphicsConfiguration().getBounds());
			getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		}
		else
		{
			setAlwaysOnTop(false);
			setResizable(true);
			setUndecorated(false);
			setBounds(0, 0, 800, 600);
			setMinimumSize(new Dimension(800, 600));
			setLocationRelativeTo(null);
		}

		setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png"));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Autoescuela");

		this.pCompleta = pCompleta;
	}

	/**
	 * Pone el cursor de mano para todos los botones y comboBoxes.
	 * Visto en: http://www.javaneverdie.com/java/swing/how-to-set-hand-cursor-to-all-buttons/
	 * @param container Contenedor
	 */
	private void setCursorToAllButtons(Container container)
	{
		Component[] components = container.getComponents();

		for (int i = 0; i < components.length; i++)
		{
			if (components[i] instanceof Container)
			{
				setCursorToAllButtons((Container) components[i]);
			}

			if (components[i] instanceof JButton || components[i] instanceof JCheckBox || components[i] instanceof JRadioButton)
			{
				components[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}

	public void setContentPane(Container panel)
	{
		super.setContentPane(panel);
		ponerFondo((JPanel) getContentPane(), new Color(230, 230, 100));
		setCursorToAllButtons(this);
	}

	public void dispose()
	{
		super.dispose();
		BD.getInstance().cerrar();
	}

	private void ponerFondo(JPanel panel, Color c)
	{
		Component[] components = panel.getComponents();
		getContentPane().setBackground(c);

		for (int i = 0; i < components.length; i++)
		{
			if (components[i] instanceof JPanel || components[i] instanceof JRadioButton)
			{
				components[i].setBackground(c);
				if (components[i] instanceof JPanel)
				{
					ponerFondo((JPanel) components[i], c);
				}
			}
		}
	}

	/**
	 * Cambia Pantalla completa / modo ventana
	 */
	public static void cambioPCompleta()
	{
		ventana.dispose();
		Container panel = ventana.getContentPane();

		if ( ! ventana.pCompleta)
		{
			ventana = new Ventana(true);
		}
		else
		{
			ventana = new Ventana(false);
		}

		ventana.setContentPane(panel);
		ventana.setVisible(true);
	}

	/**
	 * @return ¿Está la ventana en pantalla completa?
	 */
	public boolean esPCompleta()
	{
		return pCompleta;
	}

	/**
	 * @return Instancia de la ventana
	 */
	public static Ventana getInstance()
	{
		if (ventana == null)
		{
			ventana = new Ventana(true);
		}
		return ventana;
	}

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args)
	{
		Ventana.getInstance().setContentPane(new Acceso());
		Ventana.getInstance().setVisible(true);

		try
		{
			Thread.sleep(10000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		Ventana.getInstance().dispose();
	}
}