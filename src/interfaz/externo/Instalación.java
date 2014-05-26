package interfaz.externo;


import interfaz.Menú;
import interfaz.Ventana;
import interfaz.administración.Administración;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

import baseDeDatos.BD;

import usuarios.Usuario;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Panel de instalación de la autoescuela
 * @author Razican (Iban Eguia)
 *
 */
public class Instalación extends JPanel {

	private static final long serialVersionUID = 5199728959923826918L;
	private JTextField dniText;
	private JTextField nombreText;
	private JTextField apellidoText;

	/**
	 * Creación del panel
	 */
	public Instalación()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 3};
		gridBagLayout.rowHeights = new int[] {39, 191, 0, 3};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel menú = new Menú(this);
		GridBagConstraints gbc_menú = new GridBagConstraints();
		gbc_menú.anchor = GridBagConstraints.NORTHEAST;
		gbc_menú.gridwidth = 8;
		gbc_menú.insets = new Insets(0, 0, 5, 0);
		gbc_menú.gridx = 0;
		gbc_menú.gridy = 0;
		add(menú, gbc_menú);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 6;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {25, 0, 0, 243, 16, 2};
		gbl_panel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 10};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblTítulo = new JLabel("Sistema de primer registro");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 5;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		lblTítulo.setFont(new Font("Gran_label", Font.PLAIN|Font.BOLD, 25));
		panel.add(lblTítulo, gbc_lblNewLabel_1);

		JLabel lblIntroduceTuDni = new JLabel("DNI (Solo números):");
		GridBagConstraints gbc_lblIntroduceTuDni = new GridBagConstraints();
		gbc_lblIntroduceTuDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntroduceTuDni.gridwidth = 2;
		gbc_lblIntroduceTuDni.gridx = 1;
		gbc_lblIntroduceTuDni.gridy = 2;
		panel.add(lblIntroduceTuDni, gbc_lblIntroduceTuDni);

		dniText = new JTextField();
		dniText.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent e)
			{
				dniText.setBackground(Color.WHITE);
			}
		});
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
		gbc_lblNombre.gridwidth = 2;
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 3;
		panel.add(lblNombre, gbc_lblNombre);

		nombreText = new JTextField();
		nombreText.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent e)
			{
				nombreText.setBackground(Color.WHITE);
			}
		});
		GridBagConstraints gbc_nombreText = new GridBagConstraints();
		gbc_nombreText.insets = new Insets(0, 0, 5, 5);
		gbc_nombreText.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreText.gridx = 3;
		gbc_nombreText.gridy = 3;
		panel.add(nombreText, gbc_nombreText);
		nombreText.setColumns(10);

		JLabel lblApellido = new JLabel("Apellido:");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.gridwidth = 2;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 1;
		gbc_lblApellido.gridy = 4;
		panel.add(lblApellido, gbc_lblApellido);

		apellidoText = new JTextField();
		apellidoText.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent e)
			{
				apellidoText.setBackground(Color.WHITE);
			}
		});
		GridBagConstraints gbc_apellidoText = new GridBagConstraints();
		gbc_apellidoText.insets = new Insets(0, 0, 5, 5);
		gbc_apellidoText.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidoText.gridx = 3;
		gbc_apellidoText.gridy = 4;
		panel.add(apellidoText, gbc_apellidoText);
		apellidoText.setColumns(10);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				boolean correcto = true;
				Color rojo = new Color(255, 100, 100);

				if (dniText.getText().length() < 8)
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
					registrar();
				}
			}
		});
		GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
		gbc_btnRegistrar.insets = new Insets(0, 0, 0, 5);
		gbc_btnRegistrar.gridx = 3;
		gbc_btnRegistrar.gridy = 5;
		panel.add(btnRegistrar, gbc_btnRegistrar);
	}

	/**
	 * Registra el nuevo usuario
	 */
	protected void registrar()
	{
		BD.getInstance().actualizar("INSERT INTO usuarios (dni, nombre, apellido, tipo) " +
								"VALUES (" + dniText.getText() + ", " +
										"'" + nombreText.getText() + "', " +
										"'" + apellidoText.getText() + "', " +
										Usuario.PROFESOR + ");");
		if ( ! BD.getInstance().hayError())
		{
			ResultSet usuario = BD.getInstance().consulta("SELECT id FROM usuarios WHERE dni = " + dniText.getText() + ";");

			try
			{
				while (usuario.next())
				{
					Usuario.actual = new Usuario(usuario.getInt("id"),
							Integer.parseInt(dniText.getText()),
							nombreText.getText(),
							apellidoText.getText(), new Date(), Usuario.PROFESOR);
				}
			}
			catch (NumberFormatException | SQLException e)
			{
				e.printStackTrace();
				Ventana.getInstance().dispose();
				JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
			}

			Ventana.getInstance().setContentPane(new Administración());
			Ventana.getInstance().validate();
			Ventana.getInstance().repaint();
		}
		else
		{
			Ventana.getInstance().dispose();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + BD.getInstance().getÚltimoError(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
		}
	}
}