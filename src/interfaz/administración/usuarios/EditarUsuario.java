package interfaz.administración.usuarios;

import interfaz.Menú;
import interfaz.Ventana;
import interfaz.administración.Utilidades;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

import usuarios.Usuario;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JButton;

import baseDeDatos.BD;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JComboBox;

import observers.Limpiador;

/**
 * Panel para editar un usuario
 * @author Razican (Iban Eguia)
 *
 */
public class EditarUsuario extends JPanel {

	private static final long serialVersionUID = 311154288740267563L;
	private Usuario usuarioEditado;
	private JTextField dniText;
	private JTextField nombreText;
	private JTextField apellidoText;
	private JPanel panel;
	private JList<Usuario> list;
	private JButton btnSi;
	private JLabel lblestsSeguroDe;
	private JButton btnNo;
	private Vector<Usuario> usuarios;
	private JComboBox<String> comboTipo;
	private JLabel lblConfirmacin;
	private JButton btnBorrarUsuario;

	/**
	 * Crear el panel
	 */
	public EditarUsuario()
	{
		Font fuenteTítulo = new Font("Gran_label", Font.PLAIN|Font.BOLD, 25);
		Limpiador l = new Limpiador();
		usuarioEditado = null;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {10, 400, 10, 3};
		gridBagLayout.rowHeights = new int[] {10, 200, 10, 3};
		gridBagLayout.columnWeights = new double[]{100.0, 0.0, 100.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{100.0, 0.0, 100.0, Double.MIN_VALUE};
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
		gbl_panel.rowHeights = new int[]{0, 48, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblEditarUsuario = new JLabel("Editar usuario");
		GridBagConstraints gbc_lblEditarUsuario = new GridBagConstraints();
		gbc_lblEditarUsuario.gridwidth = 3;
		gbc_lblEditarUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblEditarUsuario.gridx = 1;
		gbc_lblEditarUsuario.gridy = 0;
		lblEditarUsuario.setFont(fuenteTítulo);
		panel.add(lblEditarUsuario, gbc_lblEditarUsuario);

		JLabel lblSeleccionarUsuario = new JLabel("Seleccionar usuario:");
		GridBagConstraints gbc_lblSeleccionarUsuario = new GridBagConstraints();
		gbc_lblSeleccionarUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccionarUsuario.gridx = 1;
		gbc_lblSeleccionarUsuario.gridy = 1;
		panel.add(lblSeleccionarUsuario, gbc_lblSeleccionarUsuario);

		usuarios = Usuario.generarVector();
		list = new JList<>(usuarios);
		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (usuarioEditado != list.getSelectedValue())
				{
					usuarioEditado = list.getSelectedValue();
					dniText.setBackground(Color.WHITE);
					nombreText.setBackground(Color.WHITE);
					apellidoText.setBackground(Color.WHITE);
					mostrarDatos();
				}
			}
		});
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 3;
		gbc_list.gridy = 1;

		JScrollPane scrollPane = new JScrollPane(list);
		panel.add(scrollPane, gbc_list);

		JLabel lblDniDelUsuario = new JLabel("DNI del usuario:");
		GridBagConstraints gbc_lblDniDelUsuario = new GridBagConstraints();
		gbc_lblDniDelUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblDniDelUsuario.gridx = 1;
		gbc_lblDniDelUsuario.gridy = 2;
		panel.add(lblDniDelUsuario, gbc_lblDniDelUsuario);

		dniText = new JTextField();
		dniText.addFocusListener(l);
		GridBagConstraints gbc_dniText = new GridBagConstraints();
		gbc_dniText.insets = new Insets(0, 0, 5, 5);
		gbc_dniText.fill = GridBagConstraints.HORIZONTAL;
		gbc_dniText.gridx = 3;
		gbc_dniText.gridy = 2;
		panel.add(dniText, gbc_dniText);
		dniText.setColumns(10);

		JLabel lblNombreDelUsuario = new JLabel("Nombre del usuario:");
		GridBagConstraints gbc_lblNombreDelUsuario = new GridBagConstraints();
		gbc_lblNombreDelUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreDelUsuario.gridx = 1;
		gbc_lblNombreDelUsuario.gridy = 3;
		panel.add(lblNombreDelUsuario, gbc_lblNombreDelUsuario);

		nombreText = new JTextField();
		nombreText.addFocusListener(l);
		GridBagConstraints gbc_nombreText = new GridBagConstraints();
		gbc_nombreText.insets = new Insets(0, 0, 5, 5);
		gbc_nombreText.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreText.gridx = 3;
		gbc_nombreText.gridy = 3;
		panel.add(nombreText, gbc_nombreText);
		nombreText.setColumns(10);

		JLabel lblApellidoDelUsuario = new JLabel("Apellido del usuario:");
		GridBagConstraints gbc_lblApellidoDelUsuario = new GridBagConstraints();
		gbc_lblApellidoDelUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoDelUsuario.gridx = 1;
		gbc_lblApellidoDelUsuario.gridy = 4;
		panel.add(lblApellidoDelUsuario, gbc_lblApellidoDelUsuario);

		apellidoText = new JTextField();
		apellidoText.addFocusListener(l);
		GridBagConstraints gbc_apellidoText = new GridBagConstraints();
		gbc_apellidoText.insets = new Insets(0, 0, 5, 5);
		gbc_apellidoText.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidoText.gridx = 3;
		gbc_apellidoText.gridy = 4;
		panel.add(apellidoText, gbc_apellidoText);
		apellidoText.setColumns(10);

		btnBorrarUsuario = new JButton("Borrar usuario");
		btnBorrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (usuarioEditado != null)
				{
					mostrarConfirmación();
				}
			}
		});

		JLabel lblTipoDeUsuario = new JLabel("Tipo de usuario:");
		GridBagConstraints gbc_lblTipoDeUsuario = new GridBagConstraints();
		gbc_lblTipoDeUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoDeUsuario.gridx = 1;
		gbc_lblTipoDeUsuario.gridy = 5;
		panel.add(lblTipoDeUsuario, gbc_lblTipoDeUsuario);

		String[] tipos = {"Alumno", "Profesor"};
		comboTipo = new JComboBox<>(tipos);
		GridBagConstraints gbc_comboTipo = new GridBagConstraints();
		gbc_comboTipo.insets = new Insets(0, 0, 5, 5);
		gbc_comboTipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboTipo.gridx = 3;
		gbc_comboTipo.gridy = 5;
		panel.add(comboTipo, gbc_comboTipo);
		GridBagConstraints gbc_btnBorrarUsuario = new GridBagConstraints();
		gbc_btnBorrarUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_btnBorrarUsuario.gridx = 1;
		gbc_btnBorrarUsuario.gridy = 6;
		panel.add(btnBorrarUsuario, gbc_btnBorrarUsuario);

		JButton btnEditarUsuario = new JButton("Editar usuario");
		btnEditarUsuario.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (usuarioEditado != null)
				{
					boolean correcto = true;
					Color rojo = new Color(255, 100, 100);

					if (( ! dniText.getText().equals(String.valueOf(usuarioEditado.getDni()))) && (dniText.getText().length() < 8 || Utilidades.existeUsuario(dniText.getText())))
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
						BD.getInstance().actualizar("UPDATE usuarios SET dni = " + dniText.getText() + ", nombre = '" + nombreText.getText() + "', apellido = '" + apellidoText.getText() + "', tipo = " + comboTipo.getSelectedIndex() + " WHERE id = " + usuarioEditado.getId() + ";");
						usuarioEditado.setDni(Integer.parseInt(dniText.getText()));
						usuarioEditado.setNombre(nombreText.getText());
						usuarioEditado.setApellido(apellidoText.getText());
						usuarioEditado.setTipo((byte) comboTipo.getSelectedIndex());

						usuarioEditado = null;

						/*
						 * Actualizamos el modelo de la lista para que no haya problemas
						 */
						list.setListData(usuarios);

						lblConfirmacin.setVisible(true);
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
								lblConfirmacin.setVisible(false);
							}
						}).start();
					}
				}
			}
		});
		GridBagConstraints gbc_btnEditarUsuario = new GridBagConstraints();
		gbc_btnEditarUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditarUsuario.gridx = 3;
		gbc_btnEditarUsuario.gridy = 6;
		panel.add(btnEditarUsuario, gbc_btnEditarUsuario);

		lblestsSeguroDe = new JLabel("¿Estás seguro de borrar el usuario?");
		lblestsSeguroDe.setVisible(false);
		GridBagConstraints gbc_lblestsSeguroDe = new GridBagConstraints();
		gbc_lblestsSeguroDe.gridwidth = 3;
		gbc_lblestsSeguroDe.insets = new Insets(0, 0, 5, 5);
		gbc_lblestsSeguroDe.gridx = 1;
		gbc_lblestsSeguroDe.gridy = 7;
		panel.add(lblestsSeguroDe, gbc_lblestsSeguroDe);

		btnSi = new JButton("Si");
		btnSi.setVisible(false);
		btnSi.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				borrarUsuario();

				lblestsSeguroDe.setVisible(false);
				btnSi.setVisible(false);
				btnNo.setVisible(false);

				Ventana.getInstance().validate();
				Ventana.getInstance().repaint();
			}
		});
		GridBagConstraints gbc_btnSi = new GridBagConstraints();
		gbc_btnSi.anchor = GridBagConstraints.EAST;
		gbc_btnSi.insets = new Insets(0, 0, 0, 5);
		gbc_btnSi.gridx = 1;
		gbc_btnSi.gridy = 8;
		panel.add(btnSi, gbc_btnSi);

		btnNo = new JButton("No");
		btnNo.setVisible(false);
		btnNo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lblestsSeguroDe.setVisible(false);
				btnSi.setVisible(false);
				btnNo.setVisible(false);

				Ventana.getInstance().validate();
				Ventana.getInstance().repaint();
			}
		});
		GridBagConstraints gbc_btnNo = new GridBagConstraints();
		gbc_btnNo.anchor = GridBagConstraints.WEST;
		gbc_btnNo.insets = new Insets(0, 0, 0, 5);
		gbc_btnNo.gridx = 3;
		gbc_btnNo.gridy = 8;
		panel.add(btnNo, gbc_btnNo);

		lblConfirmacin = new JLabel(new ImageIcon("img/tick.png"));
		lblConfirmacin.setVisible(false);
		GridBagConstraints gbc_lblConfirmacin = new GridBagConstraints();
		gbc_lblConfirmacin.insets = new Insets(0, 0, 0, 5);
		gbc_lblConfirmacin.gridx = 1;
		gbc_lblConfirmacin.gridy = 2;
		add(lblConfirmacin, gbc_lblConfirmacin);
	}

	protected void mostrarDatos()
	{
		if (usuarioEditado != null)
		{
			dniText.setText(String.valueOf(usuarioEditado.getDni()));
			nombreText.setText(usuarioEditado.getNombre());
			apellidoText.setText(usuarioEditado.getApellido());
			comboTipo.setSelectedIndex(usuarioEditado.getTipo());

			if (usuarioEditado != Usuario.actual)
			{
				btnBorrarUsuario.setVisible(true);
				comboTipo.setEnabled(true);
			}
			else
			{
				btnBorrarUsuario.setVisible(false);
				comboTipo.setEnabled(false);
			}
		}
		else
		{
			dniText.setText("");
			nombreText.setText("");
			apellidoText.setText("");
			comboTipo.setSelectedIndex(0);
			btnBorrarUsuario.setVisible(true);
			comboTipo.setEnabled(true);
		}
	}

	private void mostrarConfirmación()
	{
		lblestsSeguroDe.setVisible(true);
		btnSi.setVisible(true);
		btnNo.setVisible(true);

		Ventana.getInstance().validate();
		Ventana.getInstance().repaint();
	}

	private void borrarUsuario()
	{
		BD.getInstance().actualizar("DELETE FROM usuarios WHERE dni=" + list.getSelectedValue().getDni());
		usuarios.remove(list.getSelectedValue());

		/*
		 * Se vuelve a cargar el vector, por lo que se indica aquí:
		 * http://docs.oracle.com/javase/7/docs/api/javax/swing/JList.html#JList(java.util.Vector)
		 * Si se cambia el vector sin crear un nuevo ListModel, el funcionamiento es impredecible.
		 * Aunque he hecho pruebas y parece funcionar como debe, esta es la manera correcta de hacerlo.
		 */
		list.setListData(usuarios);
	}
}