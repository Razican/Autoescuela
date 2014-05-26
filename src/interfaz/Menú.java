package interfaz;

import interfaz.administración.Administración;
import interfaz.externo.Acceso;
import interfaz.tests.Escuela;

import java.awt.Container;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import usuarios.Usuario;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Menú superior del programa
 * @author Razican (Iban Eguia)
 *
 */
public class Menú extends JPanel {

	private static final long serialVersionUID = 3231102258037665022L;
	private JLabel lblVentana;
	private ImageIcon cambioPCompleta;

	/**
	 * Creación del menú
	 * @param panel Panel actual
	 */
	public Menú(Container panel)
	{
		if (Usuario.actual != null)
		{
			JLabel lblBienvenidoUsuario = new JLabel("Bienvenido " + Usuario.actual.getNombre());
			add(lblBienvenidoUsuario);

			if ( ! (panel instanceof Escuela))
			{
				JLabel lblTests = new JLabel(new ImageIcon("img/tests.png"));
				lblTests.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblTests.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						Ventana.getInstance().setContentPane(new Escuela());
						Ventana.getInstance().validate();
						Ventana.getInstance().repaint();
					}
				});
				add(lblTests);
			}

			if (Usuario.actual.esProfesor() && !(panel instanceof Administración))
			{
				JLabel lblAdmin = new JLabel(new ImageIcon("img/admin.png"));
				lblAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblAdmin.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						Ventana.getInstance().setContentPane(new Administración());
						Ventana.getInstance().validate();
						Ventana.getInstance().repaint();
					}
				});
				add(lblAdmin);
			}
		}
		if (Ventana.getInstance().esPCompleta())
		{
			cambioPCompleta = new ImageIcon("img/windowed.png");
		}
		else
		{
			cambioPCompleta = new ImageIcon("img/fullscreen.png");
		}

		lblVentana = new JLabel(cambioPCompleta);
		lblVentana.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblVentana.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				Ventana.cambioPCompleta();
				if (Ventana.getInstance().esPCompleta())
				{
					cambioPCompleta = new ImageIcon("img/windowed.png");
				}
				else
				{
					cambioPCompleta = new ImageIcon("img/fullscreen.png");
				}
				lblVentana.setIcon(cambioPCompleta);
			}
		});
		add(lblVentana);

		if (Usuario.actual != null)
		{
			JLabel lblCerrarSesin = new JLabel(new ImageIcon("img/quitSession.png"));
			lblCerrarSesin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblCerrarSesin.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					Usuario.actual = null;
					Ventana.getInstance().setContentPane(new Acceso());
					Ventana.getInstance().validate();
					Ventana.getInstance().repaint();
				}
			});
			add(lblCerrarSesin);
		}

		JLabel lblSalir = new JLabel(new ImageIcon("img/exitMenu.png"));
		lblSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSalir.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				Ventana.getInstance().setVisible(false);

				int confirmation = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres salir?",
																"Confirmación de salida",
																JOptionPane.YES_NO_OPTION,
																JOptionPane.QUESTION_MESSAGE,
																new ImageIcon("img/exitConfirm.png"));
				if (confirmation == JOptionPane.YES_OPTION)
				{
					Ventana.getInstance().dispose();
				}
				else
				{
					Ventana.getInstance().setVisible(true);
				}
			}
		});
		add(lblSalir);
	}
}