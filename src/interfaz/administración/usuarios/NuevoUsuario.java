package interfaz.administración.usuarios;

import interfaz.Menú;
import interfaz.Ventana;
import interfaz.administración.Utilidades;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.JComboBox;

import observers.Limpiador;

import baseDeDatos.BD;

/**
 * Creación de un nuevo usuario
 * @author Razican (Iban Eguia)
 *
 */
/**
 * @author Razican (Iban Eguia)
 *
 */
public class NuevoUsuario extends JPanel {

	private static final long serialVersionUID = -4178114753536564412L;
	private JTextField dniText;
	private JTextField nombreText;
	private JTextField apellidoText;
	private JComboBox<String> comboTipo;
	private JPanel panel;
	private JLabel lblCorrecto;

	/**
	 * Creación del panel
	 */
	public NuevoUsuario()
	{
		Font fuenteTítulo = new Font("Gran_label", Font.PLAIN|Font.BOLD, 25);
		Limpiador l = new Limpiador();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {10, 400, 10, 3};
		gridBagLayout.rowHeights = new int[] {10, 200, 10, 3};
		gridBagLayout.columnWeights = new double[]{100.0, 1.0, 100.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{100.0, 1.0, 100.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel menú = new Menú(this);
		GridBagConstraints gbc_menú = new GridBagConstraints();
		gbc_menú.anchor = GridBagConstraints.NORTHEAST;
		gbc_menú.gridwidth = 3;
		gbc_menú.insets = new Insets(0, 0, 5, 0);
		gbc_menú.gridx = 0;
		gbc_menú.gridy = 0;
		add(menú, gbc_menú);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblCrearNuevoUsuario = new JLabel("Crear nuevo usuario");
		GridBagConstraints gbc_lblCrearNuevoUsuario = new GridBagConstraints();
		gbc_lblCrearNuevoUsuario.gridwidth = 3;
		gbc_lblCrearNuevoUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblCrearNuevoUsuario.gridx = 1;
		gbc_lblCrearNuevoUsuario.gridy = 0;
		lblCrearNuevoUsuario.setFont(fuenteTítulo);
		panel.add(lblCrearNuevoUsuario, gbc_lblCrearNuevoUsuario);

		JLabel lblDni = new JLabel("DNI del usuario:");
		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 1;
		gbc_lblDni.gridy = 2;
		panel.add(lblDni, gbc_lblDni);

		dniText = new JTextField();
		dniText.addFocusListener(l);
		dniText.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
				if(dniText.getText().length() > 7 || c < '0' || c > '9')
				{
					e.consume();
				}
			}
		});
		GridBagConstraints gbc_dniText = new GridBagConstraints();
		gbc_dniText.insets = new Insets(0, 0, 5, 5);
		gbc_dniText.fill = GridBagConstraints.HORIZONTAL;
		gbc_dniText.gridx = 3;
		gbc_dniText.gridy = 2;
		panel.add(dniText, gbc_dniText);
		dniText.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 3;
		panel.add(lblNombre, gbc_lblNombre);

		nombreText = new JTextField();
		nombreText.addFocusListener(l);
		GridBagConstraints gbc_nombreText = new GridBagConstraints();
		gbc_nombreText.insets = new Insets(0, 0, 5, 5);
		gbc_nombreText.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreText.gridx = 3;
		gbc_nombreText.gridy = 3;
		panel.add(nombreText, gbc_nombreText);
		nombreText.setColumns(10);

		JLabel lblApellido = new JLabel("Apellido:");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 1;
		gbc_lblApellido.gridy = 4;
		panel.add(lblApellido, gbc_lblApellido);

		apellidoText = new JTextField();
		apellidoText.addFocusListener(l);
		GridBagConstraints gbc_apellidoText = new GridBagConstraints();
		gbc_apellidoText.insets = new Insets(0, 0, 5, 5);
		gbc_apellidoText.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidoText.gridx = 3;
		gbc_apellidoText.gridy = 4;
		panel.add(apellidoText, gbc_apellidoText);
		apellidoText.setColumns(10);

		JLabel lblTipo = new JLabel("Tipo:");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.gridx = 1;
		gbc_lblTipo.gridy = 5;
		panel.add(lblTipo, gbc_lblTipo);

		String[] tipos = {"Alumno", "Profesor"};
		comboTipo = new JComboBox<>(tipos);
		GridBagConstraints gbc_comboTipo = new GridBagConstraints();
		gbc_comboTipo.insets = new Insets(0, 0, 5, 5);
		gbc_comboTipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboTipo.gridx = 3;
		gbc_comboTipo.gridy = 5;
		panel.add(comboTipo, gbc_comboTipo);

		JButton btnCrearUsuario = new JButton("Crear usuario");
		btnCrearUsuario.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean correcto = true;
				Color rojo = new Color(255, 100, 100);

				if (dniText.getText().length() < 8 || Utilidades.existeUsuario(dniText.getText()))
				{
					dniText.setBackground(rojo);
					correcto = false;
				}

				if (nombreText.getText().length() < 1)
				{
					nombreText.setBackground(rojo);
					correcto = false;
				}

				if (apellidoText.getText().length() < 1)
				{
					apellidoText.setBackground(rojo);
					correcto = false;
				}

				if (correcto)
				{
					crearUsuario();
				}
			}
		});
		GridBagConstraints gbc_btnCrearUsuario = new GridBagConstraints();
		gbc_btnCrearUsuario.gridwidth = 3;
		gbc_btnCrearUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_btnCrearUsuario.gridx = 1;
		gbc_btnCrearUsuario.gridy = 6;
		panel.add(btnCrearUsuario, gbc_btnCrearUsuario);

		lblCorrecto = new JLabel(new ImageIcon("img/tick.png"));
		lblCorrecto.setVisible(false);
		GridBagConstraints gbc_lblCorrecto_1 = new GridBagConstraints();
		gbc_lblCorrecto_1.gridwidth = 3;
		gbc_lblCorrecto_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblCorrecto_1.gridx = 1;
		gbc_lblCorrecto_1.gridy = 7;
		panel.add(lblCorrecto, gbc_lblCorrecto_1);
	}

	/**
	 * Crea el nuevo usuario
	 */
	private void crearUsuario()
	{
		BD.getInstance().actualizar("INSERT INTO usuarios (dni, nombre, apellido, tipo)" +
					"VALUES (" + dniText.getText() + "," +
							"'" + nombreText.getText() + "'," +
							"'" + apellidoText.getText() + "'," +
							comboTipo.getSelectedIndex() + ");");

		if ( ! BD.getInstance().hayError())
		{
			lblCorrecto.setVisible(true);
			(new Thread()
			{
				public void run()
				{
					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					lblCorrecto.setVisible(false);
				}
			}).start();
			dniText.setText("");
			nombreText.setText("");
			apellidoText.setText("");
			comboTipo.setSelectedIndex(0);
		}
		else
		{
			Ventana.getInstance().dispose();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + BD.getInstance().getÚltimoError(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
		}
	}
}