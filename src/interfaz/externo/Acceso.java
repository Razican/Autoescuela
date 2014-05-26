package interfaz.externo;


import interfaz.Menú;
import interfaz.Ventana;
import interfaz.administración.Administración;
import interfaz.tests.Escuela;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import excepciones.UserNotSetException;

import observers.BotoneraDNI;
import usuarios.Usuario;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Panel de acceso
 * @author Razican (Iban Eguia)
 *
 */
public class Acceso extends JPanel {

	private static final long serialVersionUID = 5199728959923826918L;
	private JTextField textField;
	private ActionListener botonera;

	/**
	 * Create the panel.
	 */
	public Acceso()
	{
		Font fuenteTítulo = new Font("Gran_label", Font.PLAIN|Font.BOLD, 25);

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

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{81, 120, 42, 131, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 30, 19, 36, 25, 25, 25, 25, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblAcceso = new JLabel("Acceso");
		GridBagConstraints gbc_lblAcceso = new GridBagConstraints();
		gbc_lblAcceso.gridwidth = 3;
		gbc_lblAcceso.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcceso.gridx = 1;
		gbc_lblAcceso.gridy = 0;
		lblAcceso.setFont(fuenteTítulo);
		panel.add(lblAcceso, gbc_lblAcceso);

		JLabel lblIntroduceTuDni = new JLabel("Introduce tu DNI:");
		GridBagConstraints gbc_lblIntroduceTuDni = new GridBagConstraints();
		gbc_lblIntroduceTuDni.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblIntroduceTuDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntroduceTuDni.gridx = 1;
		gbc_lblIntroduceTuDni.gridy = 2;
		panel.add(lblIntroduceTuDni, gbc_lblIntroduceTuDni);

		textField = new JTextField();
		textField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Container acceso = acceder(textField.getText());
				if (acceso != null)
				{
					Ventana.getInstance().setContentPane(acceso);
					Ventana.getInstance().validate();
					Ventana.getInstance().repaint();
				}
				else
				{
					textField.setBackground(new Color(255, 100, 100));
				}
			}
		});
		textField.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent e)
			{
				textField.setBackground(Color.WHITE);
			}
		});
		textField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
				if(textField.getText().length() > 7 || c < '0' || c > '9')
				{
					e.consume();
				}
			}
		});
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridwidth = 2;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);

		botonera = new BotoneraDNI(textField);

		JButton button_1 = new JButton("1");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.VERTICAL;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 4;
		panel.add(button_1, gbc_button_1);
		button_1.addActionListener(botonera);

		JButton button_2 = new JButton("2");
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.fill = GridBagConstraints.BOTH;
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 2;
		gbc_button_2.gridy = 4;
		panel.add(button_2, gbc_button_2);
		button_2.addActionListener(botonera);

		JButton button_3 = new JButton("3");
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.fill = GridBagConstraints.VERTICAL;
		gbc_button_3.insets = new Insets(0, 0, 5, 5);
		gbc_button_3.gridx = 3;
		gbc_button_3.gridy = 4;
		panel.add(button_3, gbc_button_3);
		button_3.addActionListener(botonera);

		JButton button_4 = new JButton("4");
		GridBagConstraints gbc_button_4 = new GridBagConstraints();
		gbc_button_4.fill = GridBagConstraints.VERTICAL;
		gbc_button_4.insets = new Insets(0, 0, 5, 5);
		gbc_button_4.gridx = 1;
		gbc_button_4.gridy = 5;
		panel.add(button_4, gbc_button_4);
		button_4.addActionListener(botonera);

		JButton button_5 = new JButton("5");
		GridBagConstraints gbc_button_5 = new GridBagConstraints();
		gbc_button_5.fill = GridBagConstraints.BOTH;
		gbc_button_5.insets = new Insets(0, 0, 5, 5);
		gbc_button_5.gridx = 2;
		gbc_button_5.gridy = 5;
		panel.add(button_5, gbc_button_5);
		button_5.addActionListener(botonera);

		JButton button_6 = new JButton("6");
		GridBagConstraints gbc_button_6 = new GridBagConstraints();
		gbc_button_6.fill = GridBagConstraints.VERTICAL;
		gbc_button_6.insets = new Insets(0, 0, 5, 5);
		gbc_button_6.gridx = 3;
		gbc_button_6.gridy = 5;
		panel.add(button_6, gbc_button_6);
		button_6.addActionListener(botonera);

		JButton button_7 = new JButton("7");
		GridBagConstraints gbc_button_7 = new GridBagConstraints();
		gbc_button_7.fill = GridBagConstraints.VERTICAL;
		gbc_button_7.insets = new Insets(0, 0, 5, 5);
		gbc_button_7.gridx = 1;
		gbc_button_7.gridy = 6;
		panel.add(button_7, gbc_button_7);
		button_7.addActionListener(botonera);

		JButton button_8 = new JButton("8");
		GridBagConstraints gbc_button_8 = new GridBagConstraints();
		gbc_button_8.fill = GridBagConstraints.BOTH;
		gbc_button_8.insets = new Insets(0, 0, 5, 5);
		gbc_button_8.gridx = 2;
		gbc_button_8.gridy = 6;
		panel.add(button_8, gbc_button_8);
		button_8.addActionListener(botonera);

		JButton button_9 = new JButton("9");
		GridBagConstraints gbc_button_9 = new GridBagConstraints();
		gbc_button_9.fill = GridBagConstraints.VERTICAL;
		gbc_button_9.insets = new Insets(0, 0, 5, 5);
		gbc_button_9.gridx = 3;
		gbc_button_9.gridy = 6;
		panel.add(button_9, gbc_button_9);
		button_9.addActionListener(botonera);

		JButton button_0 = new JButton("0");
		GridBagConstraints gbc_button_0 = new GridBagConstraints();
		gbc_button_0.fill = GridBagConstraints.BOTH;
		gbc_button_0.insets = new Insets(0, 0, 0, 5);
		gbc_button_0.gridx = 2;
		gbc_button_0.gridy = 7;
		panel.add(button_0, gbc_button_0);
		button_0.addActionListener(botonera);

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				textField.setBackground(Color.WHITE);
				textField.setText("");
			}
		});
		GridBagConstraints gbc_btnBorrar = new GridBagConstraints();
		gbc_btnBorrar.fill = GridBagConstraints.VERTICAL;
		gbc_btnBorrar.insets = new Insets(0, 0, 0, 5);
		gbc_btnBorrar.gridx = 1;
		gbc_btnBorrar.gridy = 7;
		panel.add(btnBorrar, gbc_btnBorrar);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Container acceso = acceder(textField.getText());
				if (acceso != null)
				{
					Ventana.getInstance().setContentPane(acceso);
					Ventana.getInstance().validate();
					Ventana.getInstance().repaint();
				}
				else
				{
					textField.setBackground(new Color(255, 100, 100));
				}
			}
		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAceptar.fill = GridBagConstraints.VERTICAL;
		gbc_btnAceptar.gridx = 3;
		gbc_btnAceptar.gridy = 7;
		panel.add(btnAceptar, gbc_btnAceptar);
	}

	private Container acceder(String dni)
	{
		if (dni.length() != 8)
		{
			return null;
		}

		try
		{
			Usuario.actual = new Usuario(Integer.parseInt(dni));
		}
		catch(UserNotSetException e)
		{
			return null;
		}

		if (Usuario.actual.esProfesor())
		{
			return new Administración();
		}
		else
		{
			return new Escuela();
		}
	}
}